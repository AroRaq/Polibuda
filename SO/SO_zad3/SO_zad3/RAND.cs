using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad3
{
	public static class RAND
	{
		private static int Find(int it, int[] frames)
		{
			for (int i = 0; i < frames.Count(); i++)
				if (frames[i] == it)
					return i;
			return -1;
		}

		public static int Simulate(Queue<int> input)
		{

			Queue<int> dataset = new Queue<int>(input);
			int pageFaults = 0;
			int[] frames = new int[Program.FRAMES];
			for (int i = 0; i < frames.Count(); i++)
				frames[i] = -1;
			
			while (dataset.Any())
			{
				int curr = dataset.Dequeue();
				int f = Find(curr, frames);
				if (f == -1)
				{
					pageFaults++;
					bool placed = false;
					for (int i = 0; i < frames.Count(); i++)
					{
						if (frames[i] == -1)
						{
							frames[i] = curr;
							placed = true;
							break;
						}
					}
					if (!placed)
					{
						frames[Program.rand.Next(Program.FRAMES)] = curr;
					}
				}
			}



			return pageFaults;
		}
	}
}
