using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad4
{
	class Rowny : IAllocationAlgorithm
	{
		int frameCount;
		List<Process> processes = new List<Process>();

		public void Initialize(int frameCount, ICollection<Process> _processes)
		{
			this.frameCount = frameCount;
			foreach (Process p in _processes)
				processes.Add(new Process(p));

			///ASSIGN FRAMES
			int Single = (int)Math.Floor((double)frameCount / processes.Count);
			foreach (Process p in processes)
				p.AssignFrames(Single);
			//System.Console.Out.WriteLine(Single);
		}
		
		public int Run()
		{
			int PageFaults = 0;
			foreach (Process p in processes)
			{
				PageFaults += p.Run2();
			}
			return PageFaults;
		}
	}
}
