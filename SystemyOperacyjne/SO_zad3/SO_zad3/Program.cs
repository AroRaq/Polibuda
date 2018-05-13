using System;
using System.Collections.Generic;

namespace SO_zad3
{
	class Program
	{
		public static int FRAMES = 20;
		public static int PAGES = 50;
		public static int ACCESSES = 3000;
		public static int TESTS = 10;
		public static int RADIUS = 3;
		public static Random rand = new Random();


		static void Main(string[] args)
		{
			int _FIFO = 0;
			int _OPT = 0;
			int _LRU = 0;
			int _SCA = 0;
			int _RAND = 0;
			

			for (int j = 0; j < TESTS; j++)
			{
				int[] d = new int[ACCESSES];
				Queue<int> dataset = new Queue<int>();

				d[0] = rand.Next(PAGES);
				dataset.Enqueue(d[0]);
				for (int i = 1; i < ACCESSES; i++)
				{
					if (rand.NextDouble() < 0.9)
						d[i] = d[i - 1];
					else if (rand.NextDouble() < 0.9)
					{
						int temp = d[i - 1] + rand.Next(-RADIUS, RADIUS);
						temp = Math.Min(temp, FRAMES - 1);
						temp = Math.Max(temp, 0);
						d[i] = temp;
					}
					else
						d[i] = rand.Next(PAGES);
					dataset.Enqueue(d[i]);
				}
				_FIFO += FIFO.Simulate(dataset);
				_OPT += OPT.Simulate(dataset);
				_LRU += LRU.Simulate(dataset);
				_SCA += SCA.Simulate(dataset);
				_RAND += RAND.Simulate(dataset);
			}

			Console.Out.WriteLine("FIFO: {0}  ", _FIFO / TESTS, (double)(_FIFO / TESTS) / ACCESSES);
			Console.Out.WriteLine("OPT:  {0}  ", _OPT / TESTS, (double)(_OPT / TESTS) / ACCESSES);
			Console.Out.WriteLine("LRU:  {0}  ", _LRU / TESTS, (double)(_LRU / TESTS) / ACCESSES);
			Console.Out.WriteLine("SCA:  {0}  ", _SCA / TESTS, (double)(_SCA / TESTS) / ACCESSES);
			Console.Out.WriteLine("RAND: {0}  ", _RAND / TESTS, (double)(_RAND / TESTS) / ACCESSES);
		}
	}
}
