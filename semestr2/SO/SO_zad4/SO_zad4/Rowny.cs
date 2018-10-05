using System;
using System.Collections.Generic;

namespace SO_zad4
{
	class Rowny : IAllocationAlgorithm
	{
		int frameCount;
		int freeFrames;
		List<Process> processes = new List<Process>();

		public void Initialize(int frameCount, ICollection<Process> _processes)
		{
			this.freeFrames = frameCount;
			this.frameCount = frameCount;
			foreach (Process p in _processes)
				processes.Add(new Process(p));

			///ASSIGN FRAMES
			int Single = (int)Math.Floor((double)frameCount / processes.Count);
			foreach (Process p in processes)
			{
				if (Single > freeFrames)
					Single = freeFrames;
				p.AssignFrames(Single);
				freeFrames -= Single;
			}
			//System.Console.Out.WriteLine(Single);
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
