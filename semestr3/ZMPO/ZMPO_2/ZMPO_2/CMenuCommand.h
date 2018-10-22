#pragma once

#include "CMenuItem.h"
#include "CCommand.h"

#define EMPTY_COMMAND "Pusta komenda\n"

class CMenuCommand : public CMenuItem {
public:
	void Run();
private:
	CCommand* command;
};
