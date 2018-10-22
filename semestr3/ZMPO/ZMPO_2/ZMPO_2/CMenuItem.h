#pragma once

#include <iostream>

class CMenuItem {
public:
	virtual void Run();
protected:
	std::string command;
	std::string name;
};