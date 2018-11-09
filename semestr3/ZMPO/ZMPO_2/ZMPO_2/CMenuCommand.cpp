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

CMenuCommand::CMenuCommand(std::string& serialized, size_t& index)
{
	//Begin reading
	if (serialized[index++] != '(') {
		std::cout << "Expected ( at " << index - 1 << std::endl;
		return;
	}
	//Read Name
	if (serialized[index++] != '\'') {
		std::cout << "Expected name declaration at " << index - 1 << std::endl;
		return;
	}
	size_t len = serialized.find(index, '\'') - index;
	if (len == std::string::npos) {
		std::cout << "Expected end of name declaration after " << index << std::endl;
		return;
	}
	//check if , 
	name = serialized.substr(index + 1, len - 1);
	index += len + 1;
	if (serialized[index++] != ',') {
		std::cout << "Expected , at " << index - 1 << std::endl;
		return;
	}
	//read command
	if (serialized[index++] != '\'') {
		std::cout << "Expected command declaration at " << index - 1 << std::endl;
		return;
	}
	len = serialized.find(index, '\'') - index;
	if (len == std::string::npos) {
		std::cout << "Expected end of command declaration after " << index << std::endl;
		return;
	}
	command = serialized.substr(index + 1, len - 1);
	index += len + 1;
	//check if , 
	name = serialized.substr(index + 1, len - 1);
	index += len + 1;
	if (serialized[index++] != ',') {
		std::cout << "Expected , at " << index - 1 << std::endl;
		return;
	}
	//read help
	if (serialized[index++] != '\'') {
		std::cout << "Expected descriptiion declaration at " << index - 1 << std::endl;
		return;
	}
	len = serialized.find(index, '\'') - index;
	if (len == std::string::npos) {
		std::cout << "Expected end of description declaration after " << index << std::endl;
		return;
	}
	help = serialized.substr(index + 1, len - 1);
	index += len + 1;
	//check if )
	if (serialized[index++] != ']') {
		std::cout << "Expected end of CMenuCommand declaration ( ']' ) at " << index - 1 << std::endl;
		return;
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
