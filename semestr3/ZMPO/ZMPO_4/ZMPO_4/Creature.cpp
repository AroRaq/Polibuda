#include "pch.h"
#include "Creature.h"

Creature::Creature(size_t genotypeLength)
{
	for (int i = 0; i < genotypeLength; i++)
		genotype.push_back(Utils::Chance(0.5));
}

void Creature::SetFitness(double fitness)
{
	this->fitness = fitness;
}

double Creature::GetFitness() const
{
	return fitness;
}

std::pair<Creature*, Creature*> Creature::CrossWith(const Creature* other) const
{
	int divAt = Utils::RandInt(1, (int)genotype.size() - 2);
	Creature* child1 = new Creature(*this);
	Creature* child2 = new Creature(*other);
	if (divAt > genotype.size() / 2) {
		for (size_t i = divAt; i < genotype.size(); i++) {
			child1->genotype[i] = other->genotype[i];
			child2->genotype[i] = genotype[i];
		}
	}
	else {
		for (size_t i = 0; i < divAt; i++) {
			child1->genotype[i] = other->genotype[i];
			child2->genotype[i] = genotype[i];
		}
	}
	return std::make_pair(child1, child2);
}

void Creature::Mutate(double probability)
{
	for (size_t i = 0; i < genotype.size(); i++)
		if (Utils::Chance(probability))
			genotype[i] = !genotype[i];
}

std::vector<int> Creature::GetGenotype() const
{
	return genotype;
}
