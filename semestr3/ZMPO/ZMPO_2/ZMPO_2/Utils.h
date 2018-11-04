#pragma once

#include <vector>
#include "CTable.h"

#define ERR_OUT_OF_BOUNDS "Index out of bounds\n"
#define ERR_SPECIFY_IDX "Specify what table to remove\n"
#define ERR_PARAMS "Wrong number of parameters\n"
#define ERR_WRONG_COMMAND "Command doesn't exist\n"
#define ERR_WRONG_VALUE "Wrong value\n"
#define ERR_WRONG_LEN "Length must not be negative\n"

enum errorCode {
	NO_ERROR,
	OUT_OF_BOUNDS,
	WRONG_VALUE
};

class Utils {
public:
	template <typename T>
	static bool IsInBounds(int idx, std::vector<T>& vec);
	static bool IsInBounds(int idx, CTable& tab);
	static void DisplayError(errorCode errCode);
	static bool SplitBy(std::string str, std::string splitters, std::vector<std::string>& result, char startWith, char endWith);
};

template<typename T>
inline bool Utils::IsInBounds(int idx, std::vector<T>& vec)
{
	return idx >= 0 && idx < vec.size();
}
