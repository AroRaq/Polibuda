#pragma once

#include <vector>
#include "Creature.h"
#include "Problem.h"

class GeneticAlgorithmAgent {
public:
	GeneticAlgorithmAgent(int popSize, double crossProb, double mutProb, Problem* problem);
	void RunGeneration();
	void GenerateRandomPopulation();
	void CrossPopulation();
	void Mutate();
	double GetBestFitness();
private:
	Problem* problem;
	int generation;
	int popSize;
	double crossProb;
	double mutProb;
	std::vector<Creature*> currentPopulation;
	std::vector<Creature*> nextPopulation;
	Creature* bestCreature;
};