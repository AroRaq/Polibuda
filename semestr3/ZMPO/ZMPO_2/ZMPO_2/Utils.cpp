#include "Utils.h"

bool Utils::IsInBounds(int idx, CTable & tab)
{
	return idx >= 0 && idx < tab.GetSize();
}

void Utils::DisplayError(errorCode errCode, int arg0)
{
	switch (errCode) {
	case NO_ERROR : break;
	case OUT_OF_BOUNDS: std::cout << ERR_OUT_OF_BOUNDS; break;
	case WRONG_VALUE: std::cout << ERR_WRONG_VALUE; break;

	case EMPTY_FILE: std::cout << ERR_EMPTY_FILE << std::endl; break;
	case EXPECTED_BRACKET_LEFT: std::cout << ERR_EXPECTED_BRACKET_LEFT << arg0 << std::endl; break;
	case EXPECTED_BRACKET_RIGHT: std::cout << ERR_EXPECTED_BRACKET_RIGHT << arg0 << std::endl; break;
	case EXPECTED_STRING: std::cout << ERR_EXPECTED_STRING << arg0 << std::endl; break;
	case EXPECTED_COMMA: std::cout << ERR_EXPECTED_COMMA << arg0 << std::endl; break;
	case EXPECTED_SEMICOLON: std::cout << ERR_EXPECTED_SEMICOLON << arg0 << std::endl; break;
	case EXPECTED_QUOTE_LEFT: std::cout << ERR_EXPECTED_QUOTE_LEFT << arg0 << std::endl; break;
	case EXPECTED_QUOTE_RIGHT: std::cout << ERR_EXPECTED_QUOTE_RIGHT << arg0 << std::endl; break;
	case DIFFERENT_COMMAND: std::cout << ERR_DIFFERENT_COMMAND << arg0 << std::endl; break;
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
