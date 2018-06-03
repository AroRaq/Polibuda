using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad5
{
	public class Processor
	{
		private int currentUsage = 0;
		private List<Process> processes = new List<Process>();

		public bool IsFull => currentUsage >= Results.THRESHOLD;
		public int Usage => currentUsage;

		public Processor()
		{

		}

		public void AcceptA(Process p)
		{
			if (currentUsage + p.Usage > Results.THRESHOLD)
			{
				for (int k = 0; k < Results.TRY_COUNT; k++)
				{
					Results.Requests1++;
					Processor proc = GetRandomProcessor();
					if (!proc.IsFull)
					{
						proc.AcceptA(p);
						return;
					}
				}
			}
			processes.Add(p);
			currentUsage += p.Usage;
		}

		public void AcceptB(Process p)
		{
			if (currentUsage + p.Usage > Results.THRESHOLD)
			{
				while (true)
				{
					Processor proc = GetRandomProcessor();
					if (proc.currentUsage < currentUsage)
					{
						proc.AcceptB(p);
						return;
					}
				}
			}
			processes.Add(p);
			currentUsage += p.Usage;
		}

		public void Update()
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
		}

		public void UpdateC()
		{
			for (int i = processes.Count - 1; i >= 0; i--)
			{
				processes[i].Update();
				if (processes[i].Finished)
				{
					currentUsage -= processes[i].Usage;
					processes.RemoveAt(i);
				}
				if (Results.rand.NextDouble() < 0.01 && currentUsage < Results.MIN_THRESHOLD)
				{
					Processor proc = GetRandomProcessor();
					if (proc.IsFull)
					{
						int diff = proc.currentUsage - this.currentUsage;
						proc.Share(this, diff / 2);
					}
				}
			}
		}

		private void Share(Processor processor, int share)
		{
			List<Process> list = new List<Process>();
			for (int i = processes.Count; i >= 0; i--)
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
			foreach (Process p in list)
			{
				processor.Take(p);
			}
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
			processes.Clear();
		}
	}
}
