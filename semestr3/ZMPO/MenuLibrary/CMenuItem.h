#pragma once

#include <iostream>

#define ARROW "->"

class CMenuItem {
	friend class CMenu;
public:
	virtual int Run() = 0;
	virtual void ShowHelp() = 0;
	void SetParent(CMenuItem* parent);
	std::string GetPath();
protected:
	std::string command;
	std::string name;
	std::string help;
	CMenuItem* parent;
};