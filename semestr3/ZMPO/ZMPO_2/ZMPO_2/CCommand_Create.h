#pragma once

#include "CCommand.h"
#include "CTable.h"
#include <vector>

#define CONTENT "Content: \n"

class CCommand_Create : public CCommand {
public:
	CCommand_Create(std::vector<CTable*>& tabs);
	void RunCommand();
private:
	std::vector<CTable*> tables;
};
