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
				System.Threading.Thread.Sleep ((int)(waitTime * 10*(id+1)));
				double mode = RNG.NextDouble ();
				if (mode < .33) {
					//local event.. I don't care right now
					lamport++;
				} else if (mode >= .33 && mode < .40) {
					//send to relay, it doesnt matter what client receives
					lamport++;
					var mess = new NetMQ.NetMQMessage ();
					DateTime timestamp = DateTime.UtcNow;
					mess.Append (timestamp.ToBinary ().ToString ());
					mess.Append (lamport);
					reqSocket.SendMessage (mess);
					reqSocket.ReceiveMessage ();
					messagesInQueue++;
				} else {
					//pull an event from relay, wait a maximum timeout to prevent deadlock
					//Causality is broken when event is "sent after it was received"
					var mess = pullSocket.ReceiveMessage (new TimeSpan (500));
					if (mess != null) {
						DateTime timestampMessage = DateTime.FromBinary (long.Parse (mess.Pop ().ConvertToString ()));
						if (timestampMessage.CompareTo (DateTime.UtcNow) != -1) {
							Console.Out.WriteLine ("ERROR");
							Console.Out.WriteLine("Runtime: "+DateTime.UtcNow.Subtract(start));
							Environment.Exit (1);
						}
						long messagelamport = mess.Pop ().ConvertToInt64 ();
						lamport = Math.Max (lamport, messagelamport) + 1;
						messagesInQueue--;



					} else {
					//	Console.Out.WriteLine (id+":  Nothing to receive");
					}
					MainClass.tellLamport (lamport, id);
				}
			}
		}
	}
}

