#pragma once

#include <vector>
#include "CTable.h"

#define QUOTE_MARK '\''

#define ERR_OUT_OF_BOUNDS "Index out of bounds\n"
#define ERR_SPECIFY_IDX "Specify what table to remove\n"
#define ERR_PARAMS "Wrong number of parameters\n"
#define ERR_WRONG_COMMAND "Command doesn't exist\n"
#define ERR_WRONG_VALUE "Wrong value\n"
#define ERR_WRONG_LEN "Length must not be negative\n"

#define ERR_EMPTY_FILE "Menu source file is empty"
#define ERR_EXPECTED_BRACKET_LEFT "Expected start of item declaration at "
#define ERR_EXPECTED_BRACKET_RIGHT "Expected end of item declaration at "
#define ERR_EXPECTED_STRING "Expected property declaration at "
#define ERR_EXPECTED_COMMA "Expected comma at "
#define ERR_EXPECTED_SEMICOLON "Expected semicolon at"
#define ERR_EXPECTED_QUOTE_LEFT "Expected quotation mark before property declaration at "
#define ERR_EXPECTED_QUOTE_RIGHT "Expected quotation mark after property declaration at "
#define ERR_DIFFERENT_COMMAND "Komenda sprawdzaj¹ca inna od obiektu wewnêtrznego na "

enum errorCode {
	NO_ERROR,
	OUT_OF_BOUNDS,
	WRONG_VALUE,

	EMPTY_FILE,
	EXPECTED_BRACKET_LEFT,
	EXPECTED_BRACKET_RIGHT,
	EXPECTED_STRING,
	EXPECTED_COMMA,
	EXPECTED_SEMICOLON,
	EXPECTED_QUOTE_LEFT,
	EXPECTED_QUOTE_RIGHT,
	DIFFERENT_COMMAND
};

class Utils {
public:
	template <typename T>
	static bool IsInBounds(int idx, std::vector<T>& vec);
	static bool IsInBounds(int idx, CTable& tab);
	static void DisplayError(errorCode errCode, int arg0 = 0);
	static std::string ReadFromQuotes(std::string& source, size_t& index, errorCode* errCode);
};

template<typename T>
inline bool Utils::IsInBounds(int idx, std::vector<T>& vec)
{
	return idx >= 0 && idx < vec.size();
}
