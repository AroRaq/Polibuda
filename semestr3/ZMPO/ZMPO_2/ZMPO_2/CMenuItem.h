#pragma once

#include <iostream>

class CMenuItem {
	friend class CMenu;
public:
	virtual int Run() = 0;
protected:
	std::string command;
	std::string name;
};