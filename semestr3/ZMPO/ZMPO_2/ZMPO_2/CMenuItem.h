#pragma once

#include <iostream>

#define ARROW "->"

class CMenuItem {
	friend class CMenu;
public:
	virtual int Run() = 0;
	virtual void ShowHelp() = 0;
	void SetRoot(CMenuItem* root);
	std::string GetPath();
	virtual std::string ToString() = 0;
protected:
	std::string command;
	std::string name;
	std::string help;
	CMenuItem* root;
};