#include "Utils.h"

std::string Utils::stringify(int x) {
	std::ostringstream o;
	o << x;
	return o.str();
}
