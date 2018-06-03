using System;
using System.Collections.Generic;
using System.Linq;

namespace SO_zad5
{
	public class Processor
	{
		private int currentUsage = 0;
		private double avgUsage = 0d;
		private List<Process> processes = new List<Process>();

		public bool IsFull => currentUsage >= Results.THRESHOLD;
		public int Usage => currentUsage;
		public int ProcessCount => processes.Count;
		public double AvgUsage => avgUsage;

		public Processor()
		{

		}

		public void AcceptA(Process p)
		{		
			for (int k = 0; k < Results.TRY_COUNT; k++)
			{
				Results.Requests[Results.currAlgorithm]++;
				Processor proc = GetRandomProcessor();
				if (!proc.IsFull)
				{
					Results.Moves[Results.currAlgorithm]++;
					proc.Take(p);
					return;
				}
			}
			Take(p);
		}

		public void AcceptB(Process p)
		{
			if (currentUsage > Results.THRESHOLD)
			{
				while (true)
				{
					Results.Requests[Results.currAlgorithm]++;
					Processor proc = GetRandomProcessor();
					if (proc.currentUsage < currentUsage)
					{
						Results.Moves[Results.currAlgorithm]++;
						proc.AcceptB(p);
						return;
					}
				}
			}
			Take(p);
		}

		public void Update(int time)
		{
			for (int i = processes.Count - 1; i >= 0; i--)
			{
				processes[i].Update();
				if (processes[i].Finished)
				{
					currentUsage -= processes[i].Usage;
					processes.RemoveAt(i);
				}
			}
			avgUsage += ((double)Usage - avgUsage) / time;
		}

		public void UpdateC(int time)
		{
			Update(time);
			if (Results.rand.NextDouble() < 0.01 && currentUsage < Results.MIN_THRESHOLD)
			{
				Results.Requests[2]++;
				Processor proc = GetRandomProcessor();
				if (proc.IsFull)
				{
					Results.Moves[2]++;
					int diff = proc.currentUsage - this.currentUsage;
					proc.Share(this, diff / 2);
				}
			}
		}

		private void Share(Processor processor, int share)
		{
			List<Process> list = new List<Process>();
			for (int i = processes.Count - 1; i >= 0; i--)
			{
				if (processes[i].Usage < share)
				{
					list.Add(processes[i]);
					this.currentUsage -= processes[i].Usage;
					share -= processes[i].Usage;
					processes.RemoveAt(i);
				}
			}
			if (!list.Any())
			{
				int i = Results.rand.Next(processes.Count);
				list.Add(processes[i]);
				this.currentUsage -= processes[i].Usage;
				share -= processes[i].Usage;
				processes.RemoveAt(i);
			}
			list.ForEach(p => processor.Take(p));
		}

		public Processor GetRandomProcessor()
		{
			Processor p = null;
			do
				p = Results.processors[Results.rand.Next(Results.processors.Count)];
			while (p == this);
			return p;
		}

		public void Take(Process p)
		{
			if (currentUsage + p.Usage > 100)
				throw new InsufficientMemoryException();
			currentUsage += p.Usage;
			processes.Add(p);
		}

		public void Reset()
		{
			currentUsage = 0;
			avgUsage = 0d;
			processes.Clear();
		}
	}
}
