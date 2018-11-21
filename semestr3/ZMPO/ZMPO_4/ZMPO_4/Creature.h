#pragma once

#include <vector>
#include "Utils.h"

class Creature {
public:
	Creature(int genotypeLength);
	void SetFitness(int fitness);
	int GetFitness();
	Creature* CrossWith(const Creature* other);
	void Mutate(float probability);
private:
	std::vector<bool> genotype;
	int fitness;
};