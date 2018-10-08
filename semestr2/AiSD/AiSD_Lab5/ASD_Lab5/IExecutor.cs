using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASD_Lab5
{
	interface IExecutor <T, R>
	{
		void Execute(T elem);
		R GetResult();
	}
}
