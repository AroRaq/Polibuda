#include "CMenuCommand.h"

CMenuCommand::~CMenuCommand() {
	delete commandPtr;
}

CMenuCommand::CMenuCommand(std::string name, std::string command, std::string help, CCommand* commandPtr)
{
	this->name = name;
	this->command = command;
	this->help = help;
	this->commandPtr = commandPtr;
	root = NULL;
}

CMenuCommand::CMenuCommand(std::string serialized)
{
	std::vector<std::string> vec;
	if (Utils::SplitBy(serialized, ",,", vec, '[', ']')) {
		name = vec[0];
		command = vec[1];
		help = vec[2];
	}
	else {
		name = "error";
		command = "error";
		help = "error";
	}
}

int CMenuCommand::Run() {
	if (commandPtr == NULL)
		std::cout << EMPTY_COMMAND;
	else
		commandPtr->RunCommand();
	return 0;
}

void CMenuCommand::ShowHelp() {
	std::cout << help;
}

std::string CMenuCommand::ToString()
{
	return "['" + name + "','" + command + "','" + help + "']";
}
