using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad3
{
	public static class SCA
	{
		private static int Find(int it, int[] frames)
		{
			for (int i = 0; i < frames.Count(); i++)
				if (frames[i] == it)
					return i;
			return -1;
		}

		private static int FindFree(int[] frames)
		{
			for (int i = 0; i < frames.Length; i++)
				if (frames[i] == -1)
					return i;
			return -1;
		}

		public static int Simulate(Queue<int> input)
		{
			Queue<int> dataset = new Queue<int>(input);
			int pageFaults = 0;
			int[] frames = new int[Program.FRAMES];
			bool[] secondChance = new bool[Program.FRAMES];
			Queue<int> que = new Queue<int>();

			for (int i = 0; i < frames.Count(); i++)
			{
				frames[i] = -1;
			}

			while (dataset.Any())
			{	
				int curr = dataset.Dequeue();
				int f = Find(curr, frames);
				if (f == -1)
				{
					pageFaults++;
					int free = FindFree(frames);
					if (free != -1)
					{
						frames[free] = curr;
						secondChance[free] = true;
						que.Enqueue(free);
					}
					else
					{
						int temp = que.Dequeue();
						while(secondChance[temp])
						{
							secondChance[temp] = false;
							que.Enqueue(temp);
						}
						frames[temp] = curr;
						secondChance[temp] = true;
						que.Enqueue(temp);
					}
				}
				else
				{
					secondChance[f] = true;
				}
			}



			return pageFaults;
		}
	}
}
