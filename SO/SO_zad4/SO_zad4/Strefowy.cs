using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad4
{
	class Strefowy : IAllocationAlgorithm
	{
		int frameCount;
		List<Process> processes = new List<Process>();
		HashSet<int> workingSet = new HashSet<int>();
		int strefa = 1000;
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
							p.AssignFrames(p.WorkingSet());
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

