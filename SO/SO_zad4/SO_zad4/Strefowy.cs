using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad4
{
	class Strefowy : IAllocationAlgorithm
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
				p.AssignFrames(p.WorkingSet(0, 10));
		}
		public int Run()
		{
			int PageFaults = 0;
			foreach (Process p in processes)
			{
				PageFaults += p.Run();
			}
			return PageFaults;
		}
	}
}

