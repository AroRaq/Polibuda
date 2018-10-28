#include "Utils.h"

bool Utils::IsInBounds(int idx, CTable & tab)
{
	return idx >= 0 && idx < tab.GetSize();
}

void Utils::DisplayError(errorCode errCode)
{
	switch (errCode) {
	case NO_ERROR : break;
	case OUT_OF_BOUNDS: std::cout << ERR_OUT_OF_BOUNDS; break;
	case WRONG_VALUE: std::cout << ERR_WRONG_VALUE; break;
	}
}
