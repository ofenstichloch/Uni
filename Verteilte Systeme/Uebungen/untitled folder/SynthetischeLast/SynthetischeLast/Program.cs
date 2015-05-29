using System;
using System.Threading;

namespace SynthetischeLast
{
	class MainClass
	{
		static long[] clocks;

		public static void Main (string[] args)
		{
			if (args.Length < 2) {
				Console.Out.WriteLine ("Usage: [local n] [relay reqBind pushBind] [worker requestAddress pullAddress]");
				Environment.Exit (1);
			}
			switch (args [0]) {
			case "local":
				int n = int.Parse (args [1]);
				clocks = new long[n];
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
				r = new Relay (args[1], args[2]);
				rT = new Thread (r.loop);
				Thread.Sleep (500);
				rT.Start ();
				break;
			case "worker":
				clocks = new long[1];
				Worker wo = new Worker (args [1], args [2]);
				Thread wt = new Thread (wo.loop);
				Thread.Sleep (500);
				wt.Start ();
				break;	
			}
			if (args [0] != "relay") {
				while (true) {
					printLamport ();
					System.Threading.Thread.Sleep (100);
				}
			} else {
				Console.In.ReadLine ();
			}
		}

		public static void tellLamport(long l, int id){
			clocks[id] = l;
		}
		public static void printLamport(){
			Console.Clear ();
			for (int i = 0; i < clocks.Length; i++) {
				Console.Out.WriteLine (i + ":  " + clocks [i]);
			}
			Console.Out.WriteLine (Worker.messagesInQueue);
		}
	}
}
