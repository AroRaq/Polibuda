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
	parent = NULL;
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
