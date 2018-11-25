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
	std::vector<bool>& GetGenotype();
private:
	std::vector<bool> genotype;
	double fitness;
};