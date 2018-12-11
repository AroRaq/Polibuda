#pragma once

#include <vector>
#include "Creature.h"
#include "Problem.h"

class GeneticAlgorithmAgent {
public:
	GeneticAlgorithmAgent(int popSize, double crossProb, double mutProb, Problem* problem);
	~GeneticAlgorithmAgent();
	void RunGeneration();
	void GenerateRandomPopulation();
	void CrossTwoCreatures();
	void Mutate();
	double GetBestFitness();

private:
	Problem* problem;
	int generation;
	int popSize;
	double crossProb;
	double mutProb;
	std::vector<Creature*>* currentPopulation;
	std::vector<Creature*>* nextPopulation;
	Creature* bestCreature;
};