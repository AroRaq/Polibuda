using System;
using System.IO;

namespace AiSD_Lab7
{
	class Program
	{
		static void Main(string[] args)
		{
			Graph<int> graph = ReadFromFile("graph.txt");
			System.Console.WriteLine(graph.FindCycle());
		}


		public static Graph<int> ReadFromFile(String dir)
		{
			Graph<int> g = new Graph<int>();
			StreamReader reader = new StreamReader(dir);
			int row = 0;
			while (!reader.EndOfStream)
			{
				string s = reader.ReadLine();
				for (int column = 0; column < s.Length; column++)
				{
					if (s[column] == '1')
						g.Connect(row, column);
				}
				row++;
			}
			return g;
		}
	}


}
