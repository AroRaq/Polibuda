#pragma once
#include <vector>
#include "Creature.h"

class Problem {
public:
	virtual double CalculateFitness(Creature* creature) = 0;
	virtual size_t GetProblemSize() = 0;
protected:

};