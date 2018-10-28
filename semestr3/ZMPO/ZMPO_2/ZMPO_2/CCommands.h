#pragma once

#include "CCommand.h"
#include "CTable.h"
#include "Utils.h"
#include <vector>

#define CONTENT "Content: \n"
#define INDEX "Index"
#define NAME "Name"
#define LENGTH "Length"
#define NEW_NAME "New name"
#define NEW_LENGTH "New length"
#define NEW_VALUE "New value"
#define INDEX_TABLE "Index of table"
#define INDEX_ELEMENT "Index of element"

///CREATE BEZP
class CCommand_Create_Bezp : public CCommand {
public:
	CCommand_Create_Bezp(std::vector<CTable*>* tabs);
	void RunCommand();
private:
	std::vector<CTable*>* tables;
};


///CREATE PAR
class CCommand_Create_Par : public CCommand {
public:
	CCommand_Create_Par(std::vector<CTable*>* tabs);
	void RunCommand();
private:
	std::vector<CTable*>* tables;
};


///LIST
class CCommand_List : public CCommand {
public:
	CCommand_List(std::vector<CTable*>* tabs);
	void RunCommand();
private:
	std::vector<CTable*>* tables;
};


///DELETE
class CCommand_Delete : public CCommand {
public:
	CCommand_Delete(std::vector<CTable*>* tabs);
	void RunCommand();
private:
	std::vector<CTable*>* tables;
};


///CLONE
class CCommand_Clone : public CCommand {
public:
	CCommand_Clone(std::vector<CTable*>* tabs);
	void RunCommand();
private:
	std::vector<CTable*>* tables;
};


///RENAME
class CCommand_Rename : public CCommand {
public:
	CCommand_Rename(std::vector<CTable*>* tabs);
	void RunCommand();
private:
	std::vector<CTable*>* tables;
};


///RESIZE
class CCommand_Resize : public CCommand {
public:
	CCommand_Resize(std::vector<CTable*>* tabs);
	void RunCommand();
private:
	std::vector<CTable*>* tables;
};


///SETVAL
class CCommand_SetVal : public CCommand {
public:
	CCommand_SetVal(std::vector<CTable*>* tabs);
	void RunCommand();
private:
	std::vector<CTable*>* tables;
};


///DISPLAY
class CCommand_Display : public CCommand {
public:
	CCommand_Display(std::vector<CTable*>* tabs);
	void RunCommand();
private:
	std::vector<CTable*>* tables;
};