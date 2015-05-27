using System;
using NetMQ;


namespace SynthetischeLast
{
	public class Worker
	{

		NetMQ.NetMQContext context;
		NetMQ.NetMQSocket reqSocket;
		NetMQ.NetMQSocket pullSocket;
		DateTime start;
		int id;
		static int ID = 0;
		static int count = 0;

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
				System.Threading.Thread.Sleep ((int)waitTime * 100);
				double mode = RNG.NextDouble ();
				if (mode < .33) {
					//local event.. I don't care right now
					Console.Out.WriteLine (DateTime.UtcNow + ": Local Event "+id);
				} else if (mode >= .33 && mode < .66) {
					//send to relay, it doesnt matter what client receives
					var mess = new NetMQ.NetMQMessage ();
					DateTime timestamp = DateTime.UtcNow;
					mess.Append (timestamp.ToBinary ().ToString ());
					reqSocket.SendMessage (mess);
					reqSocket.ReceiveMessage ();
					Console.Out.WriteLine (timestamp + ": Sending Event "+id);
				} else {
					//pull an event from relay, wait a maximum timeout to prevent deadlock
					//Causality is broken when event is "sent after it was received"
					Console.Out.WriteLine (DateTime.UtcNow + ": Waiting for Receive Event "+id);
					var mess = pullSocket.ReceiveMessage (new TimeSpan (10));
					if (mess != null) {
						DateTime timestampMessage = DateTime.FromBinary (long.Parse (mess.Pop ().ConvertToString ()));
						if (timestampMessage.CompareTo (DateTime.UtcNow) != -1) {
							Console.Out.WriteLine ("ERROR");
							Worker.count++;
							Console.Out.WriteLine("Runtime: "+DateTime.UtcNow.Subtract(start));
							Environment.Exit (1);
						}
					} else {
						Console.Out.WriteLine (id+ ":  Nothing to receive");
					}

				}
			}
		}
	}
}

