using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad5
{
	public class Process
	{
		private int usage;
		private int time;
		private int enterTime;
		private int progress = 0;
		private int processor;

		public Process(int processor, int usage, int time, int enterTime)
		{
			this.processor = processor;
			this.usage = usage;
			this.time = time;
			this.enterTime = enterTime;
		}

		public Process(Process other)
		{
			this.usage = other.usage;
			this.time = other.time;
			this.enterTime = other.time;
			this.processor = other.processor;
		}

		public int Usage => usage;
		public int Time => time; 
		public int Progress => progress;
		public int EnterTime => enterTime;
		public int Processor => processor;
		public bool Finished => progress >= time;


		public void Update() => progress++;
	}
}
