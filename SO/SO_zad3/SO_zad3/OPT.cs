using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad3
{
	public static class OPT
	{
		private static int Find(int it, int[] frames)
		{
			for (int i = 0; i < frames.Count(); i++)
				if (frames[i] == it)
					return i;
			return -1;
		}

		private static int FindNext(int it, Queue<int> data)
		{
			int j = 1;
			foreach (int i in data)
			{
				j++;
				if (i == it)
				{
					return j;
				}
			}
			return int.MaxValue;
		}

		public static int Simulate(Queue<int> input)
		{
			Queue<int> dataset = new Queue<int>(input);
			int pageFaults = 0;
			int[] frames = new int[Program.FRAMES];
			int[] nextAccess = new int[Program.FRAMES];

			for (int i = 0; i < frames.Count(); i++)
			{
				frames[i] = -1;
				nextAccess[i] = int.MaxValue;
			}

			while (dataset.Any())
			{
				for (int i = 0; i < nextAccess.Count(); i++)
				{
					nextAccess[i]--;
				}
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
							nextAccess[i] = FindNext(curr, dataset);
							placed = true;
							break;
						}
					}
					if (!placed)
					{
						int longest = int.MinValue;
						int idx = 0;
						for (int i = 0; i < nextAccess.Count(); i++)
						{
							if (nextAccess[i] > longest)
							{
								longest = nextAccess[i];
								idx = i;
							}
						}
						frames[idx] = curr;
						nextAccess[idx] = FindNext(curr, dataset);
					}
				}
				else
				{
					nextAccess[f] = FindNext(curr, dataset);
				}
			}



			return pageFaults;
		}
	}
}
