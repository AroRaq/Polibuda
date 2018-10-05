using System;
using System.Collections.Generic;
using System.Linq;

namespace SO_zad4
{
	public class Process
	{
		public Queue<int> requests = new Queue<int>();
		int?[] frames;
		int[] recent;
		bool isFinished = false;
		int pageFaults = 0;

		private bool started = false;
		private IEnumerator<bool> enumer;
		private HashSet<int> workingSet = new HashSet<int>();
		private int timeSinceReset = 0;
		private int current;
		private int size;

		public int Size => size;
		public bool IsFinished => isFinished;
		public int PageFaults => pageFaults;
		public int AssignedFrames => frames.Length;
		public int Current => current;
		public int Time => timeSinceReset;
		public int WorkingSet 
		{
			get
			{
				int ret = workingSet.Count;
				timeSinceReset = 0;
				workingSet.Clear();
				return ret;
			}
		}

		public Process()
		{

		}

		public Process(Process other)
		{
			requests = new Queue<int>(other.requests);
			size = other.size;
		}

		public void GenerateRequests(int MaxPage, int Count)
		{
			size = MaxPage;
			requests.Enqueue(Program.rand.Next(1, MaxPage));
			for (int i = 1; i < Count; i++)
			{
				if (Program.rand.NextDouble() <= 0.9)
					requests.Enqueue(requests.Peek());
				else if (Program.rand.NextDouble() <= 0.9)
					requests.Enqueue(Math.Min(Math.Max(1, requests.Peek() + Program.rand.Next(-Program.RADIUS, Program.RADIUS)), MaxPage - 1));
				else
					requests.Enqueue(Program.rand.Next(1, MaxPage));
			}
		}

		public void AssignFrames(int count)
		{
			if (frames == null)
			{
				frames = new int?[count];
				recent = new int[count];
			}
			else if (frames.Length < count)
			{
				int?[] temp = new int?[count];
				int[] rece = new int[count];
				for (int i = 0; i < frames.Length; i++)
				{
					temp[i] = frames[i];
					rece[i] = recent[i];
				}
				frames = temp;
				recent = rece;
			}
			else if (frames.Length > count)
			{
				int?[] temp = new int?[count];
				int[] rece = new int[count];
				for (int i = 0; i < count; i++)
				{
					temp[i] = frames[i];
					rece[i] = recent[i];
				}
				frames = temp;
				recent = rece;
			}
		}

		public int Run()
		{
			IEnumerator<bool> enumer = Enumerator1();
			while (enumer.MoveNext()) ;
			return pageFaults;
		}

		public bool MoveNext()
		{
			if (!started)
			{
				started = true;
				enumer = Enumerator1();
			}
			if (isFinished)
				return false;
			enumer.MoveNext();
			return enumer.Current;
		}

		private IEnumerator<bool> Enumerator1()
		{
			recent = new int[frames.Length];
			for (int time = 0; requests.Any(); time++)
			{
				timeSinceReset++;
				int page = requests.Dequeue();
				workingSet.Add(page);
				current = page;
				int? frame = FindPage(page);
				if (frame != null)
				{
					recent[(int)frame] = time;
					yield return false;
				}
				else
				{
					pageFaults++;
					frame = FindFreeFrame();
					if (frame != null)
					{
						frames[(int)frame] = page;
						recent[(int)frame] = time;
					}
					else
					{
						frame = FindLongest();
						frames[(int)frame] = page;
						recent[(int)frame] = time;
					}
					yield return true;
				}
			}
			isFinished = true;
			yield return false;
			yield break;
		}

		public void AddFrames(int count) => AssignFrames(AssignedFrames + count);
		public void RemoveFrames(int count) => AssignFrames(AssignedFrames - count);

		private int? FindPage(int reqest)
		{
			for (int i = 0; i < frames.Length; i++)
				if (frames[i] == reqest)
					return i;
			return null;
		}

		private int? FindFreeFrame()
		{
			for (int i = 0; i < frames.Length; i++)
				if (frames[i] == null)
					return i;
			return null;
		}

		private int FindLongest()
		{
			int Longest = int.MaxValue;
			int LongestIdx = 0;
			for (int i = 0; i < recent.Length; i++)
			{
				if (recent[i] < Longest)
				{
					Longest = recent[i];
					LongestIdx = i;
				}
			}
			return LongestIdx;
		}
	}
}
