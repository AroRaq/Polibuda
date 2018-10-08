using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace SO_zad5
{
	public class Results
	{
		public static int THRESHOLD = 50;
		public static int TRY_COUNT = 5;
		public static int MIN_THRESHOLD = 30;
		public static int PROCESSOR_COUNT = 25;

		public static int MIN_USAGE = 5;
		public static int MAX_USAGE = 50;
		public static int MIN_LENGTH = 30;
		public static int MAX_LENGTH = 200;

		private int delay = 0;

		public static Random rand = new Random();
		public int time = 0;
		public static List<Processor> processors = new List<Processor>();
		private List<Process> processes = new List<Process>();

		public static int[] Requests = new int[3];
		public static int[] Moves = new int[3];
		public double[] AvgLoad = new double[3];
		public double[] AvgDeviation = new double[3];
		public static int currAlgorithm = 0;

		public Results()
		{
			for (int i = 0; i < PROCESSOR_COUNT; i++)
				processors.Add(new Processor());
			GenerateProcesses();
		}

		private void GenerateProcesses()
		{
			processes = new List<Process>();
			for (int i = 0; i < processors.Count; i++)
				processes.Add(new Process(i, rand.Next(5, 10), 12000, 1));
			for (int i = 2; i < 10000; i += rand.Next(10))
			{
				processes.Add(new Process(rand.Next(PROCESSOR_COUNT), rand.Next(MIN_USAGE, MAX_USAGE), rand.Next(MIN_LENGTH, MAX_LENGTH), i));
				if (rand.NextDouble() < 0.05)
				{
					for (int j = 0; j < rand.Next(2, 10); j++)
					{
						i++;
						int proc = rand.Next(PROCESSOR_COUNT);
						processes.Add(new Process(proc, rand.Next(MIN_USAGE, MAX_USAGE), rand.Next(MIN_LENGTH, MAX_LENGTH), i));
					}
				}
			}
		}

		public void ResetProcessors() => processors.ForEach(p => p.Reset());






		public void Run1()
		{
			currAlgorithm = 0;
			ResetProcessors();
			Queue<Process> que = new Queue<Process>();
			processes.ForEach(p => que.Enqueue(new Process(p)));

			for (time = 1; que.Any(); time++)
			{
				while (que.Any() && que.Peek().EnterTime <= time)
				{
					Process p = que.Dequeue();
					processors[p.Processor].AcceptA(p);
				}

				processors.ForEach(p => p.Update(time));
				Thread.Sleep(delay);
			}
			foreach (Processor p in processors)
				AvgLoad[0] += p.AvgUsage;
			AvgLoad[0] /= processors.Count;

			foreach (Processor p in processors)
				AvgDeviation[0] += Math.Abs(p.AvgUsage - AvgLoad[0]);
			AvgDeviation[0] /= processors.Count;
		}

		public void Run2()
		{
			currAlgorithm = 1;
			ResetProcessors();
			Queue<Process> que = new Queue<Process>();
			processes.ForEach(p => que.Enqueue(new Process(p)));

			for (time = 1; que.Any(); time++)
			{
				while (que.Any() && que.Peek().EnterTime <= time)
				{
					Process p = que.Dequeue();
					processors[p.Processor].AcceptB(p);
				}
				processors.ForEach(p => p.Update(time));
				Thread.Sleep(delay);
			}

			foreach (Processor p in processors)
				AvgLoad[1] += p.AvgUsage;
			AvgLoad[1] /= processors.Count;

			foreach (Processor p in processors)
				AvgDeviation[1] += Math.Abs(p.AvgUsage - AvgLoad[1]);
			AvgDeviation[1] /= processors.Count;
		}

		public void Run3()
		{
			currAlgorithm = 2;
			ResetProcessors();
			Queue<Process> que = new Queue<Process>();
			processes.ForEach(p => que.Enqueue(new Process(p)));
			// || !processors.TrueForAll(p => p.Usage == 0)
			for (time = 1; que.Any(); time++)
			{
				while (que.Any() && que.Peek().EnterTime <= time)
				{
					Process p = que.Dequeue();
					processors[p.Processor].AcceptB(p);
				}
				processors.ForEach(p => p.UpdateC(time));
				Thread.Sleep(delay);
			}

			foreach (Processor p in processors)
				AvgLoad[2] += p.AvgUsage;
			AvgLoad[2] /= processors.Count;

			foreach (Processor p in processors)
				AvgDeviation[2] += Math.Abs(p.AvgUsage - AvgLoad[2]);
			AvgDeviation[2] /= processors.Count;
		}
	}
}
