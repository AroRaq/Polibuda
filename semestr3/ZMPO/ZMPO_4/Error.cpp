#include "Error.h"

void Error::DisplayError(errorCode errCode)
{
	switch (errCode) {
	case NO_ERROR: break;
	case FILE_NOT_FOUND: std::cout << ERR_FILE_NOT_FOUND; break;
	case WRONG_PARAMETER: std::cout << ERR_WRONG_PARAMETER; break;
	case NOT_ENOUGH_ITEMS: std::cout << ERR_NOT_ENOUGH_ITEMS; break;
	}
}
