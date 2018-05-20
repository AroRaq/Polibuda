using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad4
{
	class Zliczanie : IAllocationAlgorithm
	{
		int pageCount;
		Process[] processes;

		public void Initialize(int pageCount, Process[] processes)
		{
			this.pageCount = pageCount;
			this.processes = processes;
			for (int i = 0; i < processes.Length; i++)
				processes[i] = new Process(processes[i]);

			///ASSIGN FRAMES
			foreach (Process p in processes)
				p.AssignFrames(p.WorkingSet(0, 100));
		}
		public int Run()
		{
			int PageFaults = 0;
			bool finished = false;
			IEnumerator<bool>[] enumers = new IEnumerator<bool>[processes.Length];
			for (int i = 0; i < enumers.Length; i++)
				enumers[i] = processes[i].Enumerator1();
			while (!finished)
			{
				finished = true;
				for (int i = 0; i < processes.Length; i++) 
					if (!processes[i].IsFinished)
					{
						finished = false;
						enumers[i].MoveNext();
					}
			}
			foreach (Process p in processes)
				PageFaults += p.PageFaults;
			return PageFaults;
		}
	}
}
