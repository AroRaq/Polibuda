#pragma once

#include <vector>
#include "Utils.h"

class Creature {
public:
	Creature(int genotypeLength);
	void SetFitness(double fitness);
	double GetFitness();
	Creature* CrossWith(const Creature* other);
	void Mutate(double probability);
	std::vector<int>& GetGenotype();
private:
	std::vector<int> genotype;
	double fitness;
};