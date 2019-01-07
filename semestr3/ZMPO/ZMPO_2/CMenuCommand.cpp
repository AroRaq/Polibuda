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

CMenuCommand::CMenuCommand(std::string& serialized, size_t& index, errorCode* errCode)
{
	if (serialized[index] != LEFT_BRACKET_SQ) {
		*errCode = EXPECTED_BRACKET_LEFT;
		return;
	}

	name = Utils::ReadFromQuotes(serialized, ++index, errCode);
	if (*errCode != NO_ERROR)
		return;

	if (serialized[index] != COMMA) {
		*errCode = EXPECTED_COMMA;
		return;
	}

	command = Utils::ReadFromQuotes(serialized, ++index, errCode);
	if (*errCode != NO_ERROR)
		return;

	if (serialized[index] != COMMA) {
		*errCode = EXPECTED_COMMA;
		return;
	}

	help = Utils::ReadFromQuotes(serialized, ++index, errCode);
	if (*errCode != NO_ERROR)
		return;

	if (serialized[index] != RIGHT_BRACKET_SQ) {
		*errCode = EXPECTED_BRACKET_RIGHT;
		return;
	}
	index++;
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
	std::ostringstream o;
	o << LEFT_BRACKET_SQ <<
		QUOTE_MARK << name << QUOTE_MARK << COMMA <<
		QOUTE_MARK << command << QUOTE_MARK << COMMA <<
		QUOTE_MARK << help << QUOTE_MARK << RIGHT_BRACKET_SQ;
	return o.str();
}
