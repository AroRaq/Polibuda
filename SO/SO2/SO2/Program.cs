using System;
using System.Collections.Generic;

namespace SO2
{
	class Program
	{
		public static int DISK_SIZE = 200;
		public static int REQUEST_AMOUNT = 100;
		public static int MAX_ENTERTIME = 1000;
		public static int MAX_DEADLINE = 100;
		public static int TESTS = 100;
		public static double PRIORITY_CHANCE = 0.1;

		public static Random rand = new Random();

		static void Main(string[] args)
		{
			double fcfsTimeEDF = 0;
			double fcfsTimeFD = 0;
			double sstfTimeEDF = 0;
			double sstfTimeFD = 0;
			double scanTimeEDF = 0;
			double scanTimeFD = 0;
			double cscanTimeEDF = 0;
			double cscanTimeFD = 0;

			for (int t = 0; t < TESTS; t++)
			{
				List<Request> dataset = new List<Request>();
				for (int i = 0; i < REQUEST_AMOUNT; i++)
				{
					if (rand.NextDouble() < PRIORITY_CHANCE)
						dataset.Add(new Request(rand.Next(0, MAX_ENTERTIME), rand.Next(1, DISK_SIZE), rand.Next(1, MAX_DEADLINE)));
					else
						dataset.Add(new Request(rand.Next(0, MAX_ENTERTIME), rand.Next(1, DISK_SIZE)));
				}

				dataset.Sort((r1, r2) => r1.EnterTime.CompareTo(r2.EnterTime));

				fcfsTimeFD += FCFS.SimulateFD(dataset.AsReadOnly());
				sstfTimeFD += SSTF.SimulateFD(dataset.AsReadOnly());
				scanTimeFD += SCAN.SimulateFD(dataset.AsReadOnly());
				cscanTimeFD += C_SCAN.SimulateFD(dataset.AsReadOnly());

				fcfsTimeEDF += FCFS.SimulateEDF(dataset.AsReadOnly());
				sstfTimeEDF += SSTF.SimulateEDF(dataset.AsReadOnly());
				scanTimeEDF += SCAN.SimulateEDF(dataset.AsReadOnly());
				cscanTimeEDF += C_SCAN.SimulateEDF(dataset.AsReadOnly());
			}

			Console.Out.WriteLine("EDF: ");
			Console.Out.WriteLine("FCFS   delay: " + fcfsTimeEDF / TESTS);
			Console.Out.WriteLine("SSTF   delay: " + sstfTimeEDF / TESTS);
			Console.Out.WriteLine("SCAN   delay: " + scanTimeEDF / TESTS);
			Console.Out.WriteLine("C-SCAN delay: " + cscanTimeEDF / TESTS);


			Console.Out.WriteLine();
			Console.Out.WriteLine("FD: ");
			Console.Out.WriteLine("FCFS   delay: " + fcfsTimeFD / TESTS);
			Console.Out.WriteLine("SSTF   delay: " + sstfTimeFD / TESTS);
			Console.Out.WriteLine("SCAN   delay: " + scanTimeFD / TESTS);
			Console.Out.WriteLine("C-SCAN delay: " + cscanTimeFD / TESTS);
		}
	}
}
