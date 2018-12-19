#pragma once

#include <vector>
#include "Utils.h"

template <class T>
class Creature {
public:
	Creature(size_t genotypeLength);
	void SetFitness(double fitness);
	double GetFitness() const;
	std::pair<Creature*, Creature*> CrossWith(const Creature* other) const;
	void Mutate(double probability);
	std::vector<T> GetGenotype() const;
private:
	std::vector<T> genotype;
	double fitness;
};




