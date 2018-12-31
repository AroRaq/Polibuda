#include "LeadingOnesProblem.h"

LeadingOnesProblem::LeadingOnesProblem(int size)
{
	this->size = size;
}

double LeadingOnesProblem::CalculateFitness(std::vector<bool> genotype)
{
	int i = 0;
	for (i = 0; i < genotype.size() && genotype[i]; i++);
	return i;
}

size_t LeadingOnesProblem::GetProblemSize()
{
	return size;
}
