#pragma once

#include "CMenuItem.h"
#include "CCommand.h"
#include "Utils.h"

#define EMPTY_COMMAND "Empty command\n"

class CMenuCommand : public CMenuItem {
public:
	~CMenuCommand();
	CMenuCommand(std::string name, std::string command, std::string help, CCommand* commandPtr);
	CMenuCommand(std::string& serialized, size_t& index);
	int Run() override;
	void ShowHelp() override;
	std::string ToString() override;
private:
	CCommand* commandPtr;
};
