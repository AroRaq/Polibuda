#include "pch.h"
#include "GeneticAlgorithmAgent.h"

GeneticAlgorithmAgent::GeneticAlgorithmAgent(int popSize, double crossProb, double mutProb, Problem* problem)
{
	generation = 0;
	this->popSize = popSize;
	this->crossProb = crossProb;
	this->mutProb = mutProb;
	this->problem = problem;
	GenerateRandomPopulation();
	bestCreature = new Creature(*currentPopulation[0]);
}

void GeneticAlgorithmAgent::RunGeneration()
{
	generation++;
	for (unsigned int i = 0; i < currentPopulation.size(); i++) {
		problem->CalculateFitness(currentPopulation[i]);
		if (currentPopulation[i]->GetFitness() > bestCreature->GetFitness()) {
			delete bestCreature;
			bestCreature = new Creature(*currentPopulation[i]);
		}
	}
	while (nextPopulation.size() < popSize)
		CrossPopulation();
	Mutate();
	for (unsigned int i = 0; i < currentPopulation.size(); i++)
		delete currentPopulation[i];
	currentPopulation = nextPopulation;
	nextPopulation.clear();
}

void GeneticAlgorithmAgent::GenerateRandomPopulation()
{
	for (unsigned int i = 0; i < currentPopulation.size(); i++)
		delete currentPopulation[i];
	currentPopulation.clear();
	for (int i = 0; i < popSize; i++)
		currentPopulation.push_back(new Creature(problem->GetProblemSize()));
}

void GeneticAlgorithmAgent::CrossPopulation()
{
	Creature* c1 = Utils::RandElement(currentPopulation);
	Creature* c2;
	do {
		c2 = Utils::RandElement(currentPopulation);
	} while (c1 == c2);
	Creature* p1 = c1->GetFitness() > c2->GetFitness() ? c1 : c2;

	c1 = Utils::RandElement(currentPopulation);
	do {
		c2 = Utils::RandElement(currentPopulation);
	} while (c1 == c2);
	Creature* p2 = c1->GetFitness() > c2->GetFitness() ? c1 : c2;

	if (Utils::Chance(crossProb)) {
		if (Utils::Chance(0.5))
			nextPopulation.push_back(p1->CrossWith(p2));
		else
			nextPopulation.push_back(p2->CrossWith(p1));
	}
}

void GeneticAlgorithmAgent::Mutate()
{
	for (unsigned int i = 0; i < nextPopulation.size(); i++)
		nextPopulation[i]->Mutate(mutProb);
}

double GeneticAlgorithmAgent::GetBestFitness()
{
	return bestCreature->GetFitness();
}
