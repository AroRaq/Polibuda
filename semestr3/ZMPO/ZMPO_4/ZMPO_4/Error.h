#pragma once

#include <iostream>

#define ERR_FILE_NOT_FOUND "File not found"
#define ERR_WRONG_PARAMETER "File not found"


enum errorCode {
	NO_ERROR,
	FILE_NOT_FOUND,
	WRONG_PARAMETER,
	NOT_ENOUGH_ITEMS
};

class Error {
public:
	static void DisplayError(errorCode errCode);
};
