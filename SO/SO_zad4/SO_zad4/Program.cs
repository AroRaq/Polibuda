using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad4
{
	class Program
	{
		public static Random rand = new Random();

		public static int PROCESS_COUNT = 10;
		public static int REQUEST_COUNT = 1000;
		public static int FRAME_COUNT = 275;
		public static int RADIUS = 3;
		public static int TESTS = 10;
		public static int MIN_SIZE = 10;
		public static int MAX_SIZE = 100;

		static void Main(string[] args)
		{
			int _rowny = 0;
			int _propo = 0;
			int _zlicz = 0;
			int _stref = 0;

			for (int test = 0; test < TESTS; test++)
			{
				Process[] processes = new Process[PROCESS_COUNT];
				for (int i = 0; i < PROCESS_COUNT; i++)
				{
					processes[i] = new Process();
					processes[i].GenerateRequests(rand.Next(MIN_SIZE, MAX_SIZE), REQUEST_COUNT);
				}

				IAllocationAlgorithm rowny = new Rowny();
				rowny.Initialize(FRAME_COUNT, processes);
				_rowny += rowny.Run();

				IAllocationAlgorithm proporconalny = new Proporcjonalny();
				proporconalny.Initialize(FRAME_COUNT, processes);
				_propo += proporconalny.Run();

				IAllocationAlgorithm strefowy = new Strefowy();
				strefowy.Initialize(FRAME_COUNT, processes);
				_stref += strefowy.Run();

				IAllocationAlgorithm zliczanie = new Zliczanie();
				zliczanie.Initialize(FRAME_COUNT, processes);
				_zlicz += zliczanie.Run();
			}
			System.Console.Out.WriteLine("Równy:          {0}", _rowny / TESTS);
			System.Console.Out.WriteLine("Proporcjonalny: {0}", _propo / TESTS);
			System.Console.Out.WriteLine("Strefowy:       {0}", _stref / TESTS);
			System.Console.Out.WriteLine("Zliczanie:      {0}", _zlicz / TESTS);
		}
	}
}
