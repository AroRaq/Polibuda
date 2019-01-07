#include "CCommand_List.h"

CCommand_List::CCommand_List(std::vector<CTable*>& tabs) : CCommand()
{
	tables = tabs;
}

void CCommand_List::RunCommand()
{
	std::cout << CONTENT;
	for (int i = 0; i < tables.size(); i++) {
		std::cout << i << ". " << tables[i]->GetName() << std::endl;
	}
}
