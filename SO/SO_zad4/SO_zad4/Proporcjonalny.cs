using System.Collections.Generic;

namespace SO_zad4
{
	class Proporcjonalny : IAllocationAlgorithm
	{
		int frameCount;
		List<Process> processes = new List<Process>();
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
				freeFrames -= i;
				p.AssignFrames(i);
			}
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
