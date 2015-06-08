using System;
using NetMQ;


namespace SynthetischeLast
{
	public class Worker
	{

		NetMQ.NetMQContext context;
		NetMQ.NetMQSocket reqSocket;
		NetMQ.NetMQSocket pullSocket;
		public static int messagesInQueue = 0;
		DateTime start;
		int id;
		long lamport =0;
		static int ID = 0;
		// Saves the dependent events laport clocks of other workers
		long[] lastReceived;
		string options;

		public Worker (string relayRequest, string relayPull)
		{
			//connect zmq
			context = NetMQ.NetMQContext.Create();
			reqSocket = context.CreateRequestSocket ();
			pullSocket = context.CreatePullSocket ();
			reqSocket.Connect (relayRequest);
			pullSocket.Connect (relayPull);
			this.id = Worker.ID;
			Worker.ID++;
			MainClass.tellLamport (lamport, id);
			lastReceived = new long[100];
		}

		public Worker (string relayRequest, string relayPull, int id, string options){
			//connect zmq
			Console.Out.WriteLine(relayRequest+relayPull+id);
			context = NetMQ.NetMQContext.Create();
			reqSocket = context.CreateRequestSocket ();
			pullSocket = context.CreatePullSocket ();
			reqSocket.Connect (relayRequest);
			pullSocket.Connect (relayPull);
			this.id = id;
			MainClass.tellLamport (lamport, id);
			lastReceived = new long[100];
			this.options = options;
		}

		public void loop()
		{
			//generate random events in random time intevalls
			//events can be: local, sending, receiving
			Console.Out.WriteLine("Worker started "+id);
			start = DateTime.UtcNow;
			var RNG = new Random();
			while (true) {
				double waitTime = RNG.NextDouble ();
				//Let the thread sleep a random time, multiply with id to get different sleeptimers
				System.Threading.Thread.Sleep ((int)(waitTime * 10*(id*2+1)));
				double mode = RNG.NextDouble ();
				if (mode < .33) {
					//Generate local event, nothing special to do
					lamport++;
				} else if (mode >= .33 && mode < .40) {
					//Generate sending event, create message with current realtimestamp and lamport clock
					//Send message to relay, relay will distribute it to workers (can be the same one!)
					//Sending events are far less frequent to prevent queueing lots of unpulled messages
					lamport++;
					var mess = new NetMQ.NetMQMessage ();
					DateTime timestamp = DateTime.UtcNow;
					mess.Append (timestamp.ToBinary ().ToString ());
					mess.Append (lamport);
					mess.Append (id);
					reqSocket.SendMessage (mess);
					reqSocket.ReceiveMessage ();
					messagesInQueue++;
				} else {
					//Generate receiving event
					//Pull an event from relay, wait a maximum timeout to prevent deadlock
					//Causality is inconsistent when event is "sent after it was received"
					//Check realtime timestamp
					var mess = pullSocket.ReceiveMessage (new TimeSpan (500));
					if (mess != null) {
						DateTime timestampMessage = DateTime.FromBinary (long.Parse (mess.Pop ().ConvertToString ()));
						var now = DateTime.UtcNow;
						if (timestampMessage.CompareTo (now) != -1 && options != "ignoreRealtime") {
							Console.Out.WriteLine ("REALTIME ERROR");
							Console.Out.WriteLine("Runtime: "+DateTime.UtcNow.Subtract(start));
							Console.Out.WriteLine ("Message: " + timestampMessage.Millisecond + "  Now: " + now.Millisecond);
							Environment.Exit (1);
						}
						long messagelamport = mess.Pop ().ConvertToInt64 ();
						lamport = Math.Max (lamport, messagelamport) + 1;
						int messid = mess.Pop ().ConvertToInt32 ();
						lastReceived [messid] = messagelamport;
						//For local execution count the relays theoretical message delay 
						messagesInQueue--;



					} else {
						//Might happen that there is nothing to receive, nice, move on
						//	Console.Out.WriteLine (id+":  Nothing to receive");
					}
					//Give the current lamport time to MainClass to have a senseless funny output.
					MainClass.tellLamport (lamport, id);
					//Randomly check if the current lamport clock is correct 
					//using the lastReceived array as reference
					foreach (var deplamp in lastReceived) {
						if (deplamp > lamport) {
							Console.Out.WriteLine ("LAMPORT ERROR");
							Console.Out.WriteLine("Runtime: "+DateTime.UtcNow.Subtract(start));
							Console.Out.WriteLine ("Last received lamport: " + deplamp + ". Current lamport: " + lamport);
							Environment.Exit (1);
						}
					}
				}
			}
		}
	}
}

