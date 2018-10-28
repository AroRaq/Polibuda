#pragma once

#include <iostream>
#include <vector>
#include <string>

#define DEFAULT_COMMAND "Komenda domy�lna\n"

class CCommand {
public:
	virtual void RunCommand();
	std::vector<std::string> GetInput(std::vector<std::string> argNames);
};
