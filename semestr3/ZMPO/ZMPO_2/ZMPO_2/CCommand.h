#pragma once

#include <iostream>

#define DEFAULT_COMMAND "Komenda domy�lna\n"

class CCommand {
public:
	virtual void RunCommand();
};
