#include "CCommands.h"

///CREATE BEZP
CCommand_Create_Bezp::CCommand_Create_Bezp(std::vector<CTable*>* tabs) : CCommand() {	tables = tabs; }
void CCommand_Create_Bezp::RunCommand()
{
	tables->push_back(new CTable());
}


///CREATE PAR
CCommand_Create_Par::CCommand_Create_Par(std::vector<CTable*>* tabs) : CCommand() { tables = tabs; }
void CCommand_Create_Par::RunCommand()
{
	std::vector<std::string> input = GetInput({ NAME, LENGTH });
	CTable* newTab;
	if (atoi(input[1].c_str()) <= 0) {
		Utils::DisplayError(WRONG_VALUE);
		return;
	}
	tables->push_back(new CTable(input[0], atoi(input[1].c_str())));
}


///LIST
CCommand_List::CCommand_List(std::vector<CTable*>* tabs) : CCommand() { tables = tabs; }
void CCommand_List::RunCommand()
{
	std::cout << CONTENT;
	for (int i = 0; i < tables->size(); i++) {
		std::cout << i << ". " << (*tables)[i]->GetName() << std::endl;
	}
}


///DELETE
CCommand_Delete::CCommand_Delete(std::vector<CTable*>* tabs) : CCommand() { tables = tabs; }
void CCommand_Delete::RunCommand()
{
	std::vector<std::string> input = GetInput({ INDEX });
	int idx = atoi(input[0].c_str());
	if (!Utils::IsInBounds(idx, *tables)) {
		Utils::DisplayError(OUT_OF_BOUNDS);
		return;
	}
	else {
		delete (*tables)[idx];
		tables->erase(tables->begin() + idx);
	}
}


///CLONE
CCommand_Clone::CCommand_Clone(std::vector<CTable*>* tabs) : CCommand() { tables = tabs; }
void CCommand_Clone::RunCommand()
{
	std::vector<std::string> input = GetInput({ INDEX });
	int idx = atoi(input[0].c_str());
	if (!Utils::IsInBounds(idx, *tables)) {
		Utils::DisplayError(OUT_OF_BOUNDS);
		return;
	}
	else {
		tables->push_back(new CTable(*(*tables)[idx]));
	}
}


///RENAME
CCommand_Rename::CCommand_Rename(std::vector<CTable*>* tabs) : CCommand() { tables = tabs; }
void CCommand_Rename::RunCommand()
{
	std::vector<std::string> input = GetInput({ INDEX, NEW_NAME });
	int idx = atoi(input[0].c_str());
	if (!Utils::IsInBounds(idx, *tables)) {
		Utils::DisplayError(OUT_OF_BOUNDS);
		return;
	}
	else {
		(*tables)[idx]->SetName(input[1]);
	}
}


///RESIZE
CCommand_Resize::CCommand_Resize(std::vector<CTable*>* tabs) : CCommand() { tables = tabs; }
void CCommand_Resize::RunCommand()
{
	std::vector<std::string> input = GetInput({ INDEX, NEW_LENGTH });
	int idx = atoi(input[0].c_str());
	int len = atoi(input[1].c_str());
	if (!Utils::IsInBounds(idx, *tables)) {
		Utils::DisplayError(OUT_OF_BOUNDS);
		return;
	}
	int err = 0;
	(*tables)[idx]->SetSize(len, &err);
	Utils::DisplayError(OUT_OF_BOUNDS);
}


///SETVAL
CCommand_SetVal::CCommand_SetVal(std::vector<CTable*>* tabs) : CCommand() { tables = tabs; }
void CCommand_SetVal::RunCommand()
{
	std::vector<std::string> input = GetInput({ INDEX_TABLE, INDEX_ELEMENT, NEW_VALUE });
	int idx0 = atoi(input[0].c_str());
	int idx1 = atoi(input[1].c_str());
	if (!Utils::IsInBounds(idx0, *tables)) {
		Utils::DisplayError(OUT_OF_BOUNDS);
		return;
	}
	int err = 0;
	(*tables)[idx0]->SetElement(idx1, atoi(input[2].c_str()), &err);
	Utils::DisplayError(OUT_OF_BOUNDS);
}


///DISPLAY
CCommand_Display::CCommand_Display(std::vector<CTable*>* tabs) : CCommand() { tables = tabs; }
void CCommand_Display::RunCommand()
{
	std::vector<std::string> input = GetInput({ INDEX });
	int idx = atoi(input[0].c_str());
	if (!Utils::IsInBounds(idx, *tables)) {
		Utils::DisplayError(OUT_OF_BOUNDS);
		return;
	}
	else {
		std::cout << tables->at(idx)->toString();
	}
}