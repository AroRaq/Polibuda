#pragma once

#include <iostream>

class CMenuItem {
public:
	friend class CMenu;
	virtual void Run() = 0;
protected:
	std::string command;
	std::string name;
};