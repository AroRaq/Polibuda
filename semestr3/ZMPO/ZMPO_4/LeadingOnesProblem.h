#pragma once

#include "Problem.h"

class LeadingOnesProblem : public Problem<bool> {
public:
	LeadingOnesProblem(int size);
	double CalculateFitness(std::vector<bool> genotype) override;
	size_t GetProblemSize() override;

private:
	size_t size;
};