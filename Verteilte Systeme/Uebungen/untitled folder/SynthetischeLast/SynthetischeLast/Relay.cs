using System;
using NetMQ;
namespace SynthetischeLast
{
	public class Relay
	{
		NetMQContext context;
		NetMQSocket pushSocket;
		NetMQSocket responseSocket;


		public Relay (string pushbind, string responsebind)
		{
			context = NetMQContext.Create ();
			pushSocket = context.CreatePushSocket ();
			pushSocket.Bind (pushbind);
			responseSocket = context.CreateResponseSocket ();
			responseSocket.Bind (responsebind);
		}

		public void loop()
		{
			Console.Out.WriteLine ("Relay started");
			while (true) {
				var message = responseSocket.ReceiveMessage (false);
				responseSocket.SendMessage (message);
				Console.Out.WriteLine ("Redirect message");
				pushSocket.SendMessage (message, false);

			}
		}
	}
}

