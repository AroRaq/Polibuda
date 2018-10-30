#include "CMenuCommand.h"

CMenuCommand::~CMenuCommand() {
	delete commandPtr;
}

CMenuCommand::CMenuCommand(std::string name, std::string command, CCommand* commandPtr)
{
	this->name = name;
	CMenuCommand::command = command;
	CMenuCommand::commandPtr = commandPtr;
}

int CMenuCommand::Run() {
	if (commandPtr == NULL)
		std::cout << EMPTY_COMMAND;
	else
		commandPtr->RunCommand();
	return 0;
}