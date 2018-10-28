#pragma once

#include "CMenuItem.h"
#include <vector>
#include <string>

#define BACK "back"
#define REMOVED "Removed "
#define NO_SUCH_POSITION "No such position\n"
#define SAME_OBJECT "Already here!\n"
#define NO_SUCH_PATH "Path doesnt't exist\n"
#define ALREADY_EXISTS "Object with the same command already exists: "

class CMenu : public CMenuItem {
public:
	~CMenu();
	CMenu(std::string name, std::string command);
	void Add(CMenuItem* item);
	void Add(std::string path, CMenuItem* item);
	void Remove(std::string name);
	void Run();
protected:
	CMenuItem* GetItemByName(std::string name);
	CMenuItem* GetItemByCommand(std::string command);
	std::vector<CMenuItem*> items;
};