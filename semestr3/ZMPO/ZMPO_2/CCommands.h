#pragma once

#include "CCommand.h"
#include "CTable.h"
#include "Utils.h"
#include <vector>

#define CONTENT "Content: \n"
#define INDEX "Index"
#define NAME_ "Name"
#define LENGTH "Length"
#define NEW_NAME "New name"
#define NEW_LENGTH "New length"
#define NEW_VALUE "New value"
#define INDEX_TABLE "Index of table"
#define INDEX_ELEMENT "Index of element"

class CCommand_CTable_Base : public CCommand {
public:
	CCommand_CTable_Base(std::vector<CTable*>* tabs);
protected:
	std::vector<CTable*>* tables;
};

///CREATE BEZP
class CCommand_Create_Bezp : public CCommand_CTable_Base {
public:
	CCommand_Create_Bezp(std::vector<CTable*>* tabs);
	void RunCommand();
};


///CREATE PAR
class CCommand_Create_Par : public CCommand_CTable_Base {
public:
	CCommand_Create_Par(std::vector<CTable*>* tabs);
	void RunCommand();
};


///LIST
class CCommand_List : public CCommand_CTable_Base {
public:
	CCommand_List(std::vector<CTable*>* tabs);
	void RunCommand();
};


///DELETE
class CCommand_Delete : public CCommand_CTable_Base {
public:
	CCommand_Delete(std::vector<CTable*>* tabs);
	void RunCommand();
};


///DELETEALL
class CCommand_DeleteAll : public CCommand_CTable_Base {
public:
	CCommand_DeleteAll(std::vector<CTable*>* tabs);
	void RunCommand();
};


///CLONE
class CCommand_Clone : public CCommand_CTable_Base {
public:
	CCommand_Clone(std::vector<CTable*>* tabs);
	void RunCommand();
};


///RENAME
class CCommand_Rename : public CCommand_CTable_Base {
public:
	CCommand_Rename(std::vector<CTable*>* tabs);
	void RunCommand();
};


///RESIZE
class CCommand_Resize : public CCommand_CTable_Base {
public:
	CCommand_Resize(std::vector<CTable*>* tabs);
	void RunCommand();
};


///SETVAL
class CCommand_SetVal : public CCommand_CTable_Base {
public:
	CCommand_SetVal(std::vector<CTable*>* tabs);
	void RunCommand();
};


///DISPLAY
class CCommand_Display : public CCommand_CTable_Base {
public:
	CCommand_Display(std::vector<CTable*>* tabs);
	void RunCommand();
};