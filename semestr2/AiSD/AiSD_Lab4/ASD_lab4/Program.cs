using System;
using System.Collections;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SortingAlgorithms.Classes;

namespace ASD_lab4
{
	class Program
	{
		enum TEST_TYPE
		{
			ORDERED,
			RANDOM,
			REVERSED
		}

		static Random rand = new Random();

		static TimeSpan Test<T>(IList<T> list, Action<IList<T>> func, TEST_TYPE type)
		{
			if (type == TEST_TYPE.RANDOM)
				list.Shuffle();
			else if (type == TEST_TYPE.REVERSED)
				for (int i = 0; i < list.Count / 2; i++)
					Sort.Swap(list, i, list.Count - i);
			for (int i = 0; i < list.Count; i++)
			{
				//Console.Out.Write(list[i] + " ");
			}
			Stopwatch watch = new Stopwatch();
			watch.Start();
			func(list);
			watch.Stop();
			return watch.Elapsed;
		}

		static void Main(string[] args)
		{
            List<int> list = new List<int>();
            for (int i = 0; i < 20000; i++)
            {
                list.Add(i);
            }
            foreach (var v in list)
            {
                //Console.Out.Write(v + " ");
            }
			TEST_TYPE type = TEST_TYPE.ORDERED;
			ArrayList arr = new ArrayList(list);
			//int[] arr = list.ToArray();
            Console.Out.WriteLine("Bubble  sort time: " + Test(list, Sort.Sort_Bubble, type));
			Console.Out.WriteLine("Bubble  sort time: " + Test<int>(arr, Sort.Sort_Bubble, type));
			Console.Out.WriteLine("Insert  sort time: " + Test(list, Sort.Sort_Insert, type));
			//Console.Out.WriteLine("Coctail sort time: " + Test(list, Sort.Sort_Coctail, type));
			//Console.Out.WriteLine("Comb    sort time: " + Test(list, Sort.Sort_Comb, type));
			//Console.Out.WriteLine("Merge   sort time: " + Test(list, Sort.Sort_Merge, type));
			//Console.Out.WriteLine("OddEven sort time: " + Test(list, Sort.Sort_OddEven, type));
			Console.Out.WriteLine("Quick   sort time: " + Test(list, Sort.Sort_QuickSort, type));
			Console.Out.WriteLine("Select  sort time: " + Test(list, Sort.Sort_Selection, type));
			Console.Out.WriteLine("Shell   sort time: " + Test(list, Sort.Sort_Shell, type));
			list.Shuffle();
			Stopwatch watch = new Stopwatch();
			watch.Start();
			list.Sort();
			watch.Stop();
			Console.Out.WriteLine("C#      sort time: " + watch.Elapsed);
			//Console.Out.WriteLine("Stooge  sort time: " + Test(list, Sort.Sort_Stooge));
			foreach (var v in list)
			{
				//Console.Out.Write(v + " ");
			}
		}
	}
}
