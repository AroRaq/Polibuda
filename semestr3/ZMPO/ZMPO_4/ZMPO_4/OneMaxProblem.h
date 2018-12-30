#pragma once

#include "Problem.h"

class OneMaxProblem : public Problem<bool> {
public:
	OneMaxProblem(int size);
	double CalculateFitness(std::vector<bool> genotype) override;
	size_t GetProblemSize() override;

private:
	size_t size;
};