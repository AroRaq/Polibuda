#include "CCommand.h"

void CCommand::RunCommand() {
	std::cout << DEFAULT_COMMAND;
}

std::vector<std::string> CCommand::GetInput(std::vector<std::string> argNames)
{
	std::vector<std::string> ret;
	std::cin.ignore();
	for (int i = 0; i < argNames.size(); i++) {
		std::string input;
		do {
			std::cout << argNames[i] << ": ";
			std::getline(std::cin, input);
		} while (input.empty());
		ret.push_back(input);
	}
	return ret;
}
