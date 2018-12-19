#pragma once
#include <vector>
#include "Creature.h"

template <class T>
class Problem {
public:
	virtual double CalculateFitness(std::vector<T> genotype) = 0;
	virtual size_t GetProblemSize() = 0;
protected:

};