using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SortingAlgorithms.Classes
{
	static class Sort
	{
        static Random rand = new Random();

		//n^2
		public static void Sort_Selection<T> (this IList<T> list) where T : IComparable { Sort_Selection(list, 0, list.Count); }
		public static void Sort_Bubble<T>(this IList<T> list) where T : IComparable { Sort_Bubble(list, 0, list.Count); }
		public static void Sort_Insert<T>(this IList<T> list) where T : IComparable { Sort_Insert(list, 0, list.Count); }
		public static void Sort_Coctail<T>(this IList<T> list) where T : IComparable { Sort_Coctail(list, 0, list.Count); }
		public static void Sort_Merge<T>(this IList<T> list) where T : IComparable { Sort_Merge(list, 0, list.Count); }
		public static void Sort_OddEven<T>(this IList<T> list) where T : IComparable { Sort_OddEven(list, 0, list.Count); }
		public static void Sort_Comb<T>(this IList<T> list) where T : IComparable { Sort_Comb(list, 0, list.Count); }
		public static void Sort_Shell<T>(this IList<T> list) where T : IComparable { Sort_Shell(list, 0, list.Count); }
		public static void Sort_Stooge<T>(this IList<T> list) where T : IComparable { Sort_Stooge(list, 0, list.Count); }
		public static void Sort_QuickSort<T>(this IList<T> list) where T : IComparable { QuickSort(list, 0, list.Count); }
		public static void Sort_RadixMSD<T>(this IList<T> list) where T : IRadixSortable{
			int maxDigits = 0;
			foreach (T i in list)
				if (i.GetRBitCount() > maxDigits)
					maxDigits = i.GetRBitCount();
			Sort_RadixMSD(list, 0, list.Count, list[0].MaxDigitCount(), maxDigits);
		}
		public static void Sort_RadixLSD<T>(this IList<T> list) where T : IRadixSortable {
            int maxDigits = 0;
            foreach (T i in list)
                if (i.GetRBitCount() > maxDigits)
                    maxDigits = i.GetRBitCount();
            Sort_RadixMSD(list, 0, list.Count, list[0].MaxDigitCount(), maxDigits);
        }
	    //public static void Sort_Counting(this IList<Item> list) { Sort_Counting(list, 0, list.Count);  }


		//n logn

        public static void Swap<T>(this IList<T> list, int i, int j)
        {
            T temp = list[i];
            list[i] = list[j];
            list[j] = temp;
        }

		//Fisher-Yates shuffle
		public static void Shuffle<T>(this IList<T> list)
		{
			for (var i = 0; i < list.Count; i++)
				list.Swap(i, rand.Next(i, list.Count));
		}

		//Selection Sort
		private static void Sort_Selection<T> (this IList<T> list, int START, int STOP) where T : IComparable
		{
			for (int i = START; i < STOP; i++)
			{
				int index = i;
				for (int j = i + 1; j < STOP; j++)
				{
					if (list[index].CompareTo(list[j]) > 0)
						index = j;
                }
				if (i != index)
					list.Swap(i, index);
			}
		}

		//Bubble Sort
		private static void Sort_Bubble<T>(this IList<T> list, int START, int STOP) where T : IComparable
		{
			for (int i = START; i < STOP - 1; i++)
			{
				for (int j = START; j < STOP - 1 - i; j++)
				{
					if (list[j].CompareTo(list[j + 1]) > 0)
					{
						list.Swap(j, j + 1);
					}
				}
			}
		}//Bubble Sort
		public static void Sort_Bubble2(int[] arr)
		{
			for (int i = 0; i < arr.Length - 1; i++)
			{
				for (int j = 0; j < arr.Length - 1 - i; j++)
				{
					if (arr[j].CompareTo(arr[j + 1]) > 0)
					{
						arr.Swap(j, j + 1);
					}
				}
			}
		}

		//Insert Sort
		private static void Sort_Insert<T>(this IList<T> list, int START, int STOP) where T : IComparable
		{
			for (int i = START + 1; i < STOP; i++)
			{
				int j = START;
				while (i != j && list[i].CompareTo(list[j]) > 0)
				{
					j++;
				}
				if (j != i)
				{
					for (int k = i; k > j; k--)
					{
						list.Swap(k, k - 1);
					}
				}
			}
		}

		//Coctail Shaker Sort
		private static void Sort_Coctail<T>(this IList<T> list, int START, int STOP) where T : IComparable
		{
			if (START == STOP - 1 || START == STOP)
				return;
			bool swapped = false;
			for (int i = START + 1; i < STOP; i++)
			{
				if (list[i - 1].CompareTo(list[i]) > 0)
				{
					list.Swap(i - 1, i);
					swapped = true;
				}
			}
			for (int i = STOP - 2; i > START; i--)
			{
				if (list[i].CompareTo(list[i - 1]) < 0)
				{
					list.Swap(i, i - 1);
					swapped = true;
				}
			}
			if (swapped)
				Sort_Coctail(list, START + 1, STOP - 1);
		}

		//Merge Sort
		private static void Sort_Merge<T>(this IList<T> list, int START, int STOP) where T : IComparable
		{
			if (STOP - START < 2)
				return;
			Sort_Merge(list, START, START + (STOP - START) / 2);
			Sort_Merge(list, START + (STOP - START) / 2, STOP);
			list.Merge(START, STOP);
		}
		private static void Merge<T>(this IList<T> list, int START, int STOP) where T : IComparable
		{
			for (int i = START + (STOP - START) / 2; i < STOP; i++)
			{
				for (int j = 0; (i - j) != START && list[i - j].CompareTo(list[i - j - 1]) < 0; j++)
				{
					list.Swap(i - j, i - j - 1);
				}
			}
		}

		private static void Sort_OddEven<T>(this IList<T> list, int START, int STOP) where T : IComparable
		{
			bool sorted;
			do
			{
				sorted = true;
				for (int i = 0; i < STOP - 1; i += 2)
				{
					if (list[i].CompareTo(list[i + 1]) > 0)
					{
						sorted = false;
						list.Swap(i, i + 1);
					}
				}
				for (int i = 1; i < STOP - 1; i += 2)
				{
					if (list[i].CompareTo(list[i + 1]) > 0)
					{
						sorted = false;
						list.Swap(i, i + 1);
					}
				}
			} while (!sorted);
		}


		private static void Sort_Comb<T>(this IList<T> list, int START, int STOP) where T : IComparable
		{
			int gap = STOP - START;
			float k = 1.3f;
			bool sorted;
			do
			{
				gap = (int)Math.Floor(gap / k);
				if (gap > 1)
					sorted = false;
				else
				{
					gap = 1;
					sorted = true;
				}
				for (int i = START; i + gap < STOP; i++)
				{
					if (list[i].CompareTo(list[i + gap]) > 0)
					{
						list.Swap(i, i + gap);
						sorted = false;
					}
				}
			} while (!sorted);
		}


		private static void Sort_Shell<T>(this IList<T> list, int START, int STOP) where T : IComparable
		{
			int[] gaps = { 65, 33, 17, 9, 5, 3, 1 };
			/*
			List<int> gaps = new List<int>();
			for (int n=10; n>=1; n--)
				gaps[10-n].A
			*/

			//int[] gaps = { 83, 47, 23, 11, 3, 1 };
			
			foreach (int gap in gaps)
			{
				for (int i = START + gap; i<STOP; i++)
				{
					T temp = list[i];
					int j;
					for (j = i; j >= gap && list[j - gap].CompareTo(temp) > 0; j -= gap)
					{
						list[j] = list[j - gap];
					}
					list[j] = temp;
				}
			}
		}

		private static void Sort_Stooge<T>(this IList<T> list, int START, int STOP) where T : IComparable
		{
			if (list[START].CompareTo(list[STOP-1]) > 0)
			{
				list.Swap(START, STOP - 1);
			}
			if (STOP - START > 2)
			{
				int t = (STOP - START) / 3;
				Sort_Stooge(list, START, STOP - t);
				Sort_Stooge(list, START + t, STOP);
				Sort_Stooge(list, START, STOP - t);
			}
		}

		private static void QuickSort<T>(this IList<T> list, int START, int STOP) where T : IComparable
		{
			int i = START, j = STOP;
			var pivot = list[(START + STOP) / 2];

			/* partition */
			while (i < j)
			{
				while (list[i].CompareTo(pivot) < 0)
				{
					i++;
				}
				while (list[j - 1].CompareTo(pivot) > 0)
				{
					j--;
				}
				if (i < j)
				{
					list.Swap(i, j-1);
					i++;
					j--;
				}
			};

			/* recursion */
			if (START < j)
				QuickSort(list, START, j);
			if (i < STOP)
				QuickSort(list, i, STOP);
		}

		private static void Sort_RadixMSD<T>(this IList<T> list, int START, int STOP, int RBitCount, int RBitIdx) where T : IRadixSortable
		{
			if (RBitIdx >= RBitCount || STOP - START < 2)
				return;
			int j = START;
			for (int i = START; i < STOP; i++)
			{
				if (!list[i].GetRBit(RBitIdx))
				{
					for (int t = i; t > j; t--)
						list.Swap(t, t - 1);
					j++;
				}
			}
			RBitIdx++;
			Sort_RadixMSD(list, START, j, RBitCount, RBitIdx);
			Sort_RadixMSD(list, j, STOP, RBitCount, RBitIdx);
		}

		private static void Sort_RadixLSD<T>(this IList<T> list, int START, int STOP, int BitCount, int BitIdx, int StopBitIdx) where T : IRadixSortable
		{
			int j = START;
			for (int i = START; i < STOP; i++)
			{
				if (!list[i].GetRBit(BitIdx))
				{
					for (int t = i; t>j; t--)
						list.Swap(t, t-1);
					j++;
				}
			}
			if (BitIdx-1 >= StopBitIdx)
				Sort_RadixLSD(list, START, STOP, BitCount, BitIdx-1, StopBitIdx);
		}
        /*
		private static void Sort_Counting(this IList<Item> list, int START, int STOP)
		{
			int MaxIdx = START;
			for (int i = START+1; i < STOP; i++)
			{
				if (list[i] > list[MaxIdx])
					MaxIdx = i;
				System.Threading.Thread.Sleep(1);
			}

			int[] tab = new int[list[MaxIdx].Value+1];
			tab[0] = -1;
			for (int i = START; i < STOP; i++)
			{
				tab[list[i].Value]++;
			}

			for (int i = 1; i < list[MaxIdx].Value + 1; i++)
			{
				tab[i] += tab[i - 1];
			}

			int[] tab2 = new int[list[MaxIdx].Value + 1];				//Needed to create second array for visualization purposes
			tab.CopyTo(tab2, 0);

			for (int i = START; i < STOP; i++)
			{
				while (i <= tab[list[i].Value-1] || i > tab[list[i].Value])
				{
					System.Threading.Thread.Sleep(1);
					tab2[list[i].Value]--;
					list.Swap(i, tab2[list[i].Value]+1);
				}
			}
		}
        */
	}
}
