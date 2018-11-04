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

bool Utils::SplitBy(std::string str, std::string splitters, std::vector<std::string>& result, char startWith, char endWith)
{
	if (str[0] != startWith) {
		std::cout << "Error: Expected " << startWith << std::endl;
		return false;
	}
	if (str[str.length()-1] != endWith) {
		std::cout << "Error: Expected " << endWith << std::endl;
		return false;
	}
	str = str.substr(1, str.length() - 2);
	for (int i = 0; i < splitters.length(); i++) {
		std::size_t s = str.find(splitters[i]);
		if (s == std::string::npos) {
			std::cout << "Error: expected " << splitters[i];
			return false;
		}
		result.push_back(str.substr(0, s));
		str = str.substr(s + 1);
	}
	result.push_back(str);
}
