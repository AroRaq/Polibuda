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

	case EXPECTED_BRACKET_LEFT: std::cout << ERR_EXPECTED_BRACKET_LEFT; break;
	case EXPECTED_BRACKET_RIGHT: std::cout << ERR_EXPECTED_BRACKET_RIGHT; break;
	case EXPECTED_STRING: std::cout << ERR_EXPECTED_STRING; break;
	case EXPECTED_COMMA: std::cout << ERR_EXPECTED_COMMA; break;
	case EXPECTED_SEMICOLON: std::cout << ERR_EXPECTED_SEMICOLON; break;
	case EXPECTED_QUOTE_LEFT: std::cout << ERR_EXPECTED_QUOTE_LEFT; break;
	case EXPECTED_QUOTE_RIGHT: std::cout << ERR_EXPECTED_QUOTE_RIGHT; break;
	}
}

std::string Utils::ReadFromQuotes(std::string& source, size_t& index, errorCode* errCode)
{
	if (source[index] != QUOTE_MARK) {
		*errCode = EXPECTED_QUOTE_LEFT;
		return std::string();
	}
	size_t len = source.find(QUOTE_MARK, ++index) - index;
	if (len == std::string::npos) {
		*errCode = EXPECTED_QUOTE_RIGHT;
		return std::string();
	}
	std::string ret = source.substr(index, len);
	index += len + 1;
	return ret;
}
