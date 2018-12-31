#pragma once

#include "CCommand.h"
#include "CTable.h"
#include <vector>

#define CONTENT "Content: \n"

class CCommand_List : public CCommand {
public:
	CCommand_List(std::vector<CTable*>& tabs);
	void RunCommand();
private:
	std::vector<CTable*> tables;
};
