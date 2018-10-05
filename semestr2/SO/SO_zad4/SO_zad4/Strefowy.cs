using System.Collections.Generic;

namespace SO_zad4
{
	class Strefowy : IAllocationAlgorithm
	{
		int frameCount;
		List<Process> processes = new List<Process>();
		int strefa = 75;
		int freeFrames;

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
			for (int time = 0; !finished; time++)
			{
				finished = true;
				foreach (Process p in processes)
				{
					if (!p.IsFinished)
					{
						finished = false;
						p.MoveNext();
						if (p.Time == strefa)
						{
							int newFrames = p.WorkingSet - p.AssignedFrames;
							if (newFrames > freeFrames)
								newFrames = freeFrames;
							p.AddFrames(newFrames);
							freeFrames -= newFrames;
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

