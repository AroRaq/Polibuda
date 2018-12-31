#include "CCommand_Create.h"

CCommand_Create::CCommand_Create(std::vector<CTable*>& tabs) : CCommand()
{
	tables = tabs;
}

void CCommand_Create::RunCommand()
{
	std::string newName;
	std::string newLen;
	std::cout << "Name: " << std::endl;

	CTable* newTab;
	if (name == DEFAULT_NAME)
		newTab = new CTable();
	else
		if (len < 0) {
			std::cout << ERR_WRONG_VALUE;
			return;
		}
		else
			newTab = new CTable(name, len);
	ctVector.push_back(newTab);
}
