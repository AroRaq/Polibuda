#pragma once

#include <vector>
#include "Utils.h"

template <class T>
class Creature {
public:
	Creature(size_t genotypeLength, double crossProbability, T left, T right);

	Creature& operator++(int);
	Creature* operator+(Creature& other);
	Creature* operator-(Creature& other);

	void SetFitness(double fitness);
	double GetFitness() const;
	std::pair<Creature*, Creature*> CrossWith(const Creature& other) const;
	void Mutate();
	std::vector<T> GetGenotype() const;
private:
	T leftBound, rightBound;
	std::vector<T> genotype;
	double fitness;
	double crossProbability;
};




