#pragma once

#include "CMenuItem.h"
#include <vector>
#include <string>

#define NO_SUCH_POSITION "Nie ma takiej pozycji\n"
#define BACK "back"

class CMenu : public CMenuItem {
public:
	CMenu();
	CMenu(std::string name, std::string command);
	void Add(CMenuItem* item);
	void Run();
	CMenuItem* Get(std::string name);
protected:
	CMenuItem* GetItemFromCommand(std::string command);
	std::vector<CMenuItem*> items;
};