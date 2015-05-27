using System;
using System.Threading;

namespace SynthetischeLast
{
	class MainClass
	{

		public static void Main (string[] args)
		{
			if (args.Length < 2) {
				Console.Out.WriteLine ("Usage: [local n] [relay reqBind pushBind] [worker requestAddress pullAddress]");
				Environment.Exit (1);
			}
			switch (args [0]) {
			case "local":
				int n = int.Parse (args [1]);
			
				Relay r = new Relay ("tcp://localhost:33333", "tcp://localhost:33334");
				Thread rT = new Thread (r.loop);
				rT.Start ();
				Console.Out.WriteLine ("Creating " + n + " worker");
				for(int i = 0; i<n;i++){
					Worker w = new Worker ("tcp://localhost:33334", "tcp://localhost:33333");
					Thread wT = new Thread (w.loop);
					wT.Start ();
				}
				break;
			case "relay":
				break;
			case "worker":
				break;	
			}
			Console.ReadLine ();
		}
	}
}
