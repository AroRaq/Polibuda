using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad4
{
	public class Process
	{
		public int[] Requests;
		int?[] Frames;
		int[] Recent;
		bool isFinished = false;
		int pageFaults = 0;

		private int size;
		public int Size => size;
		public bool IsFinished => isFinished;
		public int PageFaults => pageFaults;

		public Process()
		{

		}

		public Process(Process other)
		{
			Requests = other.Requests;
			Frames = other.Frames;
			size = other.size;
		}

		public void GenerateRequests(int MaxPage, int Count)
		{
			size = MaxPage;
			Requests = new int[Count];
			Requests[0] = Program.rand.Next(MaxPage);
			for (int i = 1; i < Count; i++)
			{
				if (Program.rand.NextDouble() <= 0.9)
					Requests[i] = Requests[i - 1];
				else if (Program.rand.NextDouble() <= 0.9)
					Requests[i] = Math.Min(Math.Max(0, Requests[i - 1] + Program.rand.Next(-Program.RADIUS, Program.RADIUS)), MaxPage - 1);
				else
					Requests[i] = Program.rand.Next(MaxPage);
			}
		}

		public void AssignFrames(int Count)
		{
			Frames = new int?[Count];
			for (int i = 0; i < Count; i++)
				Frames[i] = null;
		}

		public int Run()
		{
			//foreach (int i in Requests)
			//	System.Console.Out.Write(i + " ");
			IEnumerator<bool> enumer = Enumerator1();
			while (enumer.MoveNext()) ;
			return pageFaults;
		}

		public IEnumerator<bool> Enumerator1()
		{
			Recent = new int[Frames.Length];
			for (int time = 0; time < Requests.Length; time++)
			{
				int? Page = FindRequest(Requests[time]);
				if (Page != null)
				{
					Recent[(int)Page] = time;
					yield return false;
				}
				else
				{
					pageFaults++;
					Page = FindFreePage();
					if (Page != null)
					{
						Frames[(int)Page] = Requests[time];
						Recent[(int)Page] = time;
					}
					else
					{
						Page = FindLongest();
						Frames[(int)Page] = Requests[time];
						Recent[(int)Page] = time;
					}
					yield return true;
				}
			}
			isFinished = true;
			System.Console.Out.WriteLine("TEST" + this.GetHashCode());
			yield break;
			//return pageFaults;
		}

		public void AddFrame()
		{
			int?[] temp = new int?[Frames.Length + 1];
			for (int i = 0; i < Frames.Length; i++)
				temp[i] = Frames[i];
			Frames = temp;
		}

		public void RemovePage()
		{
			int idx = FindLongest();
			int?[] temp = new int?[Frames.Length - 1];
			for (int i = 0; i < idx; i++)
				temp[i] = Frames[i];
			for (int i = idx + 1; i < Frames.Length; i++)
				temp[i - 1] = Frames[i];
			Frames = temp;
		}

		public int WorkingSet(int start, int delta)
		{
			if (start + delta > Requests.Length)
				throw new ArgumentOutOfRangeException();
			HashSet<int> set = new HashSet<int>();
			for (int i = 0; i < delta; i++)
				set.Add(Requests[start + i]);
			return set.Count();
		}

		private int? FindRequest(int Reqest)
		{
			for (int i = 0; i < Frames.Length; i++)
				if (Frames[i] == Reqest)
					return i;
			return null;
		}

		private int? FindFreePage()
		{
			for (int i = 0; i < Frames.Length; i++)
				if (Frames[i] == null)
					return i;
			return null;
		}

		private int FindLongest()
		{
			int Longest = int.MaxValue;
			int LongestIdx = 0;
			for (int i = 0; i < Recent.Length; i++)
			{
				if (Recent[i] < Longest)
				{
					Longest = Recent[i];
					LongestIdx = i;
				}
			}
			return LongestIdx;
		}
	}
}
