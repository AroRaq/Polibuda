#include "OneMaxProblem.h"

OneMaxProblem::OneMaxProblem(int size)
{
	this->size = size;
}

double OneMaxProblem::CalculateFitness(std::vector<bool> genotype)
{
	int fitness = 0;
	for (size_t i = 0; i < genotype.size(); i++)
		if (genotype[i])
			fitness++;

	return fitness;
}

size_t OneMaxProblem::GetProblemSize()
{
	return size;
}
