#pragma once

#include <iostream>

#define ERR_FILE_NOT_FOUND "File not found\n"
#define ERR_WRONG_PARAMETER "Wrong parameters\n"
#define ERR_NOT_ENOUGH_ITEMS "Not enough items for the problem\n"


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
