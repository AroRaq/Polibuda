#pragma once

#include "CMenuItem.h"
#include <vector>
#include <string>

#define BACK "back"
#define EXIT "exit"
#define REMOVED "Removed "
#define NO_SUCH_POSITION "No such position\n"
#define SAME_OBJECT "Already here!\n"
#define NO_SUCH_PATH "Path doesnt't exist\n"
#define ALREADY_EXISTS "Object with the same command already exists: "

#define ARROW "->"
#define LEFT_BRACKET " ("
#define RIGHT_BRACKET ")"
#define TERMINATOR ". "

class CMenu : public CMenuItem {
public:
	CMenu(std::string name, std::string command);
	~CMenu();
	void Add(CMenuItem* item);
	void Add(std::string path, CMenuItem* item);
	void Remove(std::string name);
	int Run();
protected:
	CMenuItem* GetItemByName(std::string name);
	CMenuItem* GetItemByCommand(std::string command);
	std::vector<CMenuItem*> items;
};