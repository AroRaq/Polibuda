#pragma once

#include <iostream>

class CMenuItem {
public:
	virtual void Run() = 0;
protected:
	std::string command;
	std::string name;
};