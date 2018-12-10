#pragma once

#include <vector>
#include "Utils.h"

class Creature {
public:
	Creature(size_t genotypeLength);
	void SetFitness(double fitness);
	double GetFitness() const;
	std::pair<Creature*, Creature*> CrossWith(const Creature* other) const;
	void Mutate(double probability);
	std::vector<int> GetGenotype() const;
private:
	std::vector<int> genotype;
	double fitness;
};