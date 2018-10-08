using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SortingAlgorithms.Classes
{
	interface IRadixSortable
	{
		int GetRBitCount();
		int MaxDigitCount();
		bool GetRBit(int idx);
	}
}
