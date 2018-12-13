#pragma once

#include <vector>
#include "Creature.h"
#include "Problem.h"
#include "Error.h"

class GeneticAlgorithmAgent {
public:
	GeneticAlgorithmAgent(int popSize, double crossProb, double mutProb, Problem* problem, errorCode* errCode);
	~GeneticAlgorithmAgent();
	void RunGeneration();
	void GenerateRandomPopulation();
	void CrossTwoCreatures();
	void MutateAll();
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