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
		public static int REQUEST_COUNT = 2000;
		public static int FRAME_COUNT = 30;
		public static int RADIUS = 2;
		public static int TESTS = 100;
		public static int MIN_SIZE = 2;
		public static int MAX_SIZE = 8;

		static void Main(string[] args)
		{
			int _rowny = 0;
			int _propo = 0;
			int _zlicz = 0;
			int _stref = 0;

			for (int test = 0; test < TESTS; test++)
			{
				List<Process> processes = new List<Process>();
				for (int i = 0; i < PROCESS_COUNT; i++)
				{
					Process p = new Process();
					p.GenerateRequests(rand.Next(MIN_SIZE, MAX_SIZE), REQUEST_COUNT);
					processes.Add(p);
				}

				IAllocationAlgorithm rowny = new Rowny();
				rowny.Initialize(FRAME_COUNT, processes.AsReadOnly());
				_rowny += rowny.Run();

				IAllocationAlgorithm proporcjonalny = new Proporcjonalny();
				proporcjonalny.Initialize(FRAME_COUNT, processes.AsReadOnly());
				_propo += proporcjonalny.Run();

				IAllocationAlgorithm strefowy = new Strefowy();
				strefowy.Initialize(FRAME_COUNT, processes.AsReadOnly());
				_stref += strefowy.Run();

				IAllocationAlgorithm zliczanie = new Zliczanie();
				zliczanie.Initialize(FRAME_COUNT, processes.AsReadOnly());
				_zlicz += zliczanie.Run();
			}
			System.Console.Out.WriteLine("Równy:          {0}", _rowny / TESTS);
			System.Console.Out.WriteLine("Proporcjonalny: {0}", _propo / TESTS);
			System.Console.Out.WriteLine("Strefowy:       {0}", _stref / TESTS);
			System.Console.Out.WriteLine("Zliczanie:      {0}", _zlicz / TESTS);
		}
	}
}
