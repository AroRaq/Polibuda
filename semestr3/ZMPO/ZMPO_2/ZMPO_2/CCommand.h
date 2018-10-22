#pragma once

#include <iostream>

#define DEFAULT_COMMAND "Komenda domyœlna\n"

class CCommand {
public:
	virtual void RunCommand();
};
