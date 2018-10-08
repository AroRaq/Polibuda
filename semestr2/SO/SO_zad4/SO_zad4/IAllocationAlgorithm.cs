using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SO_zad4
{
	interface IAllocationAlgorithm
	{
		void Initialize(int pageCount, ICollection<Process> processes);
		int Run();
	}
}
