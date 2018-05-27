using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad4
{
	class Zliczanie : IAllocationAlgorithm
	{
		int frameCount;
		List<Process> processes = new List<Process>();
		int freeFrames;

		double maxFaults = 0.1;
		double minFaults = 0.01;

		public void Initialize(int frameCount, ICollection<Process> _processes)
		{
			freeFrames = frameCount;
			this.frameCount = frameCount;
			foreach (Process p in _processes)
				processes.Add(new Process(p));

			///ASSIGN FRAMES
			int count = 0;
			foreach (Process p in processes)
				count += p.Size;
			foreach (Process p in processes)
			{
				int i = p.Size * frameCount / count;
				i = i == 0 ? 1 : i;
				p.AssignFrames(i);
				freeFrames -= i; //System.Console.Out.WriteLine(freeFrames);
			}
		}

		public int Run()
		{
			int PageFaults = 0;
			bool finished = false;
			int time = 0;
			int[] timeSince = new int[processes.Count];
			int[] pageFault = new int[processes.Count];
			while (!finished)
			{
				time++;
				finished = true;
				for (int i = 0; i < processes.Count; i++)
				{
					if (!processes[i].IsFinished)
					{
						finished = false;
						processes[i].MoveNext();
						double perc = (double)pageFault[i] / timeSince[i];
						if (processes[i].Time > 50 && perc > maxFaults && freeFrames > 0)
						{
							processes[i].AddFrame();
							processes[i].WorkingSet();
							timeSince[i] = 0;
							pageFault[i] = 0;
							freeFrames--;
						}
						if (processes[i].Time > 100 && perc < minFaults && processes[i].AssignedFrames > 1)
						{
							processes[i].RemoveFrame();
							freeFrames++;
							processes[i].WorkingSet();
							timeSince[i] = 0;
							pageFault[i] = 0;
						}
						
					}
				}
			}
			
			foreach (Process p in processes)
				PageFaults += p.PageFaults;
			return PageFaults;
		}
	}
}
