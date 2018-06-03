using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace SO_zad5
{
	public class Results
	{
		public static int THRESHOLD = 75;
		public static int TRY_COUNT = 5;
		public static int MIN_THRESHOLD = 20;
		public static int PROCESSOR_COUNT = 15;

		public static Random rand = new Random();
		public int time = 0;
		public static List<Processor> processors = new List<Processor>();
		private List<Process> processes = new List<Process>();

		public static int Requests1 = 0;

		public Results()
		{
			for (int i = 0; i < PROCESSOR_COUNT; i++)
			{
				processors.Add(new Processor());
			}
			GenerateProcesses();
		}

		private void GenerateProcesses()
		{
			processes = new List<Process>();
			for (int i = 0; i < 10000; i += rand.Next(10))
			{
				processes.Add(new Process(rand.Next(PROCESSOR_COUNT), rand.Next(10) + 1, rand.Next(400) + 100, i));
				if (rand.NextDouble() < 0.01)
				{
					for (int j = 0; j < rand.Next(5) + 2; j++)
					{
						i++;
						processes.Add(new Process(rand.Next(PROCESSOR_COUNT), rand.Next(10) + 1, rand.Next(400) + 100, i));
					}
				}
			}
		}

		public void ResetProcessors() => processors.ForEach(p => p.Reset());






		public void Run1()
		{
			ResetProcessors();
			Queue<Process> que = new Queue<Process>();
			foreach (Process p in processes)
				que.Enqueue(new Process(p));

			for (time = 0; que.Any(); time++)
			{
				while (que.Any() && que.Peek().EnterTime == time)
				{
					Process p = que.Dequeue();
					processors[p.Processor].AcceptA(p);
				}
				foreach (Processor p in processors)
				{
					p.Update();
				}
				Thread.Sleep(10);
			}
			System.Console.Out.Write("KONIEC");

		}
	}
}
