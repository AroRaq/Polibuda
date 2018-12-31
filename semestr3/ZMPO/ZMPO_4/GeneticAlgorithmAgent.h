#pragma once

#include <vector>
#include "Creature.h"
#include "Problem.h"
#include "Error.h"

template <class T>
class GeneticAlgorithmAgent {
public:
	GeneticAlgorithmAgent(int popSize, double crossProb, double mutProb, Problem<T>* problem, errorCode* errCode, T left, T right);
	~GeneticAlgorithmAgent();
	void RunGeneration();
	double GetBestFitness();

protected:
	void GenerateRandomPopulation();
	void CrossTwoCreatures();
	void MutateAll();
	Creature<T>* DrawParent();

private:
	Problem<T>* problem;
	int generation;
	int popSize;
	double crossProb;
	double mutProb;
	std::vector<Creature<T>*>* currentPopulation;
	std::vector<Creature<T>*>* nextPopulation;
	Creature<T>* bestCreature;
	T leftBound, rightBound;
};



