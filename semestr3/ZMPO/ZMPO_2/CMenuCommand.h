#pragma once

#include "CMenuItem.h"
#include "CCommand.h"
#include "Utils.h"

#define EMPTY_COMMAND "Empty command\n"

#define COMMA ','
#define LEFT_BRACKET_SQ '['
#define RIGHT_BRACKET_SQ ']'
#define QOUTE_MARK '\''

class CMenuCommand : public CMenuItem {
public:
	~CMenuCommand();
	CMenuCommand(std::string name, std::string command, std::string help, CCommand* commandPtr);
	CMenuCommand(std::string& serialized, size_t& index, errorCode* errCode);
	int Run() override;
	void ShowHelp() override;
	std::string ToString() override;
private:
	CCommand* commandPtr;
};
