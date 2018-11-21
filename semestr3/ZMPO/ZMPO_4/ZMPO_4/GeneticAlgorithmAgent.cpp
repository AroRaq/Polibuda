#include "pch.h"
#include "GeneticAlgorithmAgent.h"

GeneticAlgorithmAgent::GeneticAlgorithmAgent(int popSize, int crossProb, int multProb)
{
	generation = 0;
	this->popSize = popSize;
	this->crossProb = crossProb;
	this->multProb = multProb;
	GenerateRandomPopulation();
}

void GeneticAlgorithmAgent::RunGeneration()
{
	while (generation < 50) {
		generation++;
	}
}

void GeneticAlgorithmAgent::GenerateRandomPopulation()
{
	for (int i = 0; i < population.size; i++)
		delete population[i];
	population.clear;
	for (int i = 0; i < popSize; i++)
		population.push_back(new Creature(items.size));
}
