using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASD_Lab5
{
	class DisplayExec<T, R> : IExecutor<T, string>
	{
		string Result = "";
		public void Execute(T elem)
		{
			System.Console.Out.Write(elem + " ");
			Result += elem + ", ";
		}

		public string GetResult()
		{
			return Result;
		}
	}
}
