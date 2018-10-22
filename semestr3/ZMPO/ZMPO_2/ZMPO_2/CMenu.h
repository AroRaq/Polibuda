#pragma once

#include "CMenuItem.h"
#include <vector>
#include <string>

#define NO_SUCH_POSITION "nie ma takiej pozycji"
#define BACK "back"

class CMenu : public CMenuItem {
public:
	void Run();
protected:
	CMenuItem* GetItemFromCommand(std::string command);
	std::vector<CMenuItem*> items;
};