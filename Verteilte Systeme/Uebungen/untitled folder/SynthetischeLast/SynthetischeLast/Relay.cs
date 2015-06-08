using System;
using NetMQ;
namespace SynthetischeLast
{
	public class Relay
	{
		NetMQContext context;
		NetMQSocket pushSocket;
		NetMQSocket responseSocket;
		string option;


		public Relay (string pushbind, string responsebind, string options)
		{
			context = NetMQContext.Create ();
			pushSocket = context.CreatePushSocket ();
			pushSocket.Bind (pushbind);
			responseSocket = context.CreateResponseSocket ();
			responseSocket.Bind (responsebind);
			option = options;
		}

		public void loop()
		{
			Console.Out.WriteLine ("Relay started");
			while (true) {
				var message = responseSocket.ReceiveMessage (false);
				responseSocket.SendMessage (message);
				if (option == "delay") {
					System.Threading.Thread.Sleep (500);
				}
				Console.Out.WriteLine ("Redirect message");
				pushSocket.SendMessage (message, false);
			}
		}
	}
}

