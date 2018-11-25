#include "pch.h"
#include "Creature.h"

Creature::Creature(int genotypeLength)
{
	for (int i = 0; i < genotypeLength; i++)
		genotype.push_back(Utils::Chance(0.5));
}

void Creature::SetFitness(double fitness)
{
	this->fitness = fitness;
}

double Creature::GetFitness()
{
	return fitness;
}

Creature* Creature::CrossWith(const Creature* other)
{
	int divAt = Utils::RandInt(1, genotype.size() - 1);
	Creature* child = new Creature(genotype.size());
	for (int i = 0; i < divAt; i++)
		child->genotype[i] = genotype[i];
	for (int i = divAt; i < genotype.size(); i++)
		child->genotype[i] = other->genotype[i];
	return child;
}

void Creature::Mutate(double probability)
{
	for (int i = 0; i < genotype.size(); i++)
		if (Utils::Chance(probability))
			genotype[i] = !genotype[i];
}

std::vector<bool>& Creature::GetGenotype()
{
	return genotype;
}
