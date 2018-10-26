#include "CMenuCommand.h"

CMenuCommand::CMenuCommand(std::string name, std::string command, CCommand* commandPtr)
{
	CMenuCommand::name = name;
	CMenuCommand::command = command;
	CMenuCommand::commandPtr = commandPtr;
}

void CMenuCommand::Run() {
	if (commandPtr == NULL)
		std::cout << EMPTY_COMMAND;
	else
		commandPtr->RunCommand();
}