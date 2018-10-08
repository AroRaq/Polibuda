using System.Collections.Generic;

namespace SO_zad4
{
	class Zliczanie : IAllocationAlgorithm
	{
		int frameCount;
		List<Process> processes = new List<Process>();
		int freeFrames;

		double maxFaults = 0.2;
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
						timeSince[i]++;
						pageFault[i] += processes[i].MoveNext() ? 1 : 0;
						double perc = (double)pageFault[i] / timeSince[i];
						//System.Console.Out.WriteLine(perc);
						if (timeSince[i] > 50 && perc > maxFaults && freeFrames > 0)
						{
							processes[i].AddFrames(1);
							timeSince[i] = 0;
							pageFault[i] = 0;
							freeFrames--;
						}
						if (timeSince[i] > 50 && perc < minFaults && processes[i].AssignedFrames > 1)
						{

							//System.Console.Out.WriteLine("zabieram");
							processes[i].RemoveFrames(1);
							freeFrames++;
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
