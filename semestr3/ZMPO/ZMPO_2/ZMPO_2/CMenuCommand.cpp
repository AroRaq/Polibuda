#include "CMenuCommand.h"

void CMenuCommand::Run() {
	if (command == NULL)
		std::cout << EMPTY_COMMAND;
	else
		command->RunCommand();
}