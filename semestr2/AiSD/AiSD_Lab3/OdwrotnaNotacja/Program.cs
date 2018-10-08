using System;
using System.Collections.Generic;
using System.IO;

namespace OdwrotnaNotacja
{
	class Program
	{
		static string[] ReadFromTxt(string dir)
		{
			List<string> ret = new List<string>();
			StreamReader reader = new StreamReader(dir);
			while (!reader.EndOfStream)
			{
				ret.Add(reader.ReadLine());
			}
			return ret.ToArray();
		}
		
		static void Main(string[] args)
		{
			//string s = "(0,5 + 3) ^ (10 - 2 * 3) = ";
			string[] dzialania = ReadFromTxt("dzialania.txt");
			foreach (string s in dzialania)
			{
				Console.Out.WriteLine(s);
				Console.Out.WriteLine(ONP.ToONP(s));
				Console.Out.WriteLine(ONP.Calculate(ONP.ToONP(s)));
			}
			while (true)
			{
				string s = Console.In.ReadLine();
				if (s.Equals(""))
					return;
				Console.Out.WriteLine(ONP.ToONP(s));
				Console.Out.WriteLine(ONP.Calculate(ONP.ToONP(s)));
			}
		}
	}
}
