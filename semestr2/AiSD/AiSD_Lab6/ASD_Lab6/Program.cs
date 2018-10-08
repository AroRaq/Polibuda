using System;
using System.Collections.Generic;
using System.IO;

namespace ASD_Lab6
{
	class Program
	{
		static void Main(string[] args)
		{
			LexTree tree = new LexTree();
			ReadFromTxt(".../.../words.txt", tree);
			string temp ="";
			do
			{
				temp = Console.ReadLine();
				string[] s1 = temp.Split(' ');
				if (s1[0] == "add")
					tree.Add(s1[1]);
				else if (s1[0] == "remove")
					tree.Remove(s1[1]);
				else if (s1[0] == "prefix")
					System.Console.Out.WriteLine(tree.MostOften(int.Parse(s1[1])));
				else if (s1[0] == "prefix2")
					System.Console.Out.WriteLine(tree.MostOften2(int.Parse(s1[1])));
				else if (s1[0] == "help")
					System.Console.Out.WriteLine("add, remove, prefix, prefix2, find, list, wordcount, nodecount");
				else if (s1[0] == "find")
					System.Console.Out.WriteLine(tree.Contains(s1[1]));
				else if (s1[0] == "list")
				{
					List<string> list = tree.ReturnAllPrefix(s1[1]);
					if (list != null)
						foreach (string el in list)
							System.Console.Out.WriteLineAsync(el);
				}
				else if (s1[0] == "wordcount")
					System.Console.Out.WriteLine(tree.Count);
				else if (s1[0] == "nodecount")
					System.Console.Out.WriteLine(tree.NodeCount());
				else if (s1[0] == "count")
					System.Console.Out.WriteLine(tree.PrefixCount(s1[1]));

			}
			while (temp.Length != 0);
		}

		static void ReadFromTxt(string dir, LexTree tree)
		{
			StreamReader reader = new StreamReader(dir);
			while (!reader.EndOfStream)
			{
				tree.Add(reader.ReadLine());
			}
		}
	}
}
