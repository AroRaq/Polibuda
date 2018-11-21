#pragma once

#include <vector>
#include "Item.h"
#include "Creature.h"

class GeneticAlgorithmAgent {
public:
	GeneticAlgorithmAgent(int popSize, int crossProb, int multProb);
	void RunGeneration();
	void GenerateRandomPopulation();
private:
	int generation;
	int popSize;
	float crossProb;
	float multProb;
	std::vector<Item> items;
	std::vector<Creature*> population;
};