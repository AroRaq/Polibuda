#pragma once

#include "CMenuItem.h"
#include "CCommand.h"

#define EMPTY_COMMAND "Pusta komenda\n"

class CMenuCommand : public CMenuItem {
public:
	CMenuCommand(std::string name, std::string command, CCommand* commandPtr);
	void Run();
private:
	CCommand* commandPtr;
};
