#pragma once

#include <vector>
#include "Creature.h"
#include "Problem.h"
#include "Error.h"

template <class T>
class GeneticAlgorithmAgent {
public:
	GeneticAlgorithmAgent(int popSize, double crossProb, double mutProb, Problem<T>* problem, errorCode* errCode);
	~GeneticAlgorithmAgent();
	void RunGeneration();
	void GenerateRandomPopulation();
	void CrossTwoCreatures();
	void MutateAll();
	double GetBestFitness();

private:
	Problem<T>* problem;
	int generation;
	int popSize;
	double crossProb;
	double mutProb;
	std::vector<Creature<T>*>* currentPopulation;
	std::vector<Creature<T>*>* nextPopulation;
	Creature<T>* bestCreature;
};




template <class T>
GeneticAlgorithmAgent<T>::GeneticAlgorithmAgent(int popSize, double crossProb, double mutProb, Problem<T>* problem, errorCode* errCode)
{
	if (popSize <= 0 || crossProb < 0 || crossProb > 1 || mutProb < 0 || mutProb > 1) {
		*errCode = WRONG_PARAMETER;
		return;
	}
	generation = 0;
	this->popSize = popSize;
	this->crossProb = crossProb;
	this->mutProb = mutProb;
	this->problem = problem;
	currentPopulation = new std::vector<Creature<T>*>();
	nextPopulation = new std::vector<Creature<T>*>();
	GenerateRandomPopulation();
	bestCreature = new Creature<T>(*currentPopulation->at(0));
}

template <class T>
GeneticAlgorithmAgent<T>::~GeneticAlgorithmAgent()
{
	for (size_t i = 0; i < currentPopulation->size(); i++)
		delete currentPopulation->at(i);
	for (size_t i = 0; i < nextPopulation->size(); i++)
		delete nextPopulation->at(i);
	delete currentPopulation;
	delete nextPopulation;
	delete bestCreature;
}

template <class T>
void GeneticAlgorithmAgent<T>::RunGeneration()
{
	generation++;
	///Calculate fitness and set best creature
	for (size_t i = 0; i < currentPopulation->size(); i++) {
		currentPopulation->at(i)->SetFitness(problem->CalculateFitness(currentPopulation->at(i)->GetGenotype()));
		if (currentPopulation->at(i)->GetFitness() > bestCreature->GetFitness()) {
			delete bestCreature;
			bestCreature = new Creature<T>(*currentPopulation->at(i));
		}
	}
	///Create next generation
	while (nextPopulation->size() < popSize) {
		if (Utils::Chance(crossProb)) {
			CrossTwoCreatures();
		}
		else {
			nextPopulation->push_back(new Creature<T>(*Utils::RandElement(currentPopulation)));
			nextPopulation->push_back(new Creature<T>(*Utils::RandElement(currentPopulation)));
		}
	}
	MutateAll();
	for (size_t i = 0; i < currentPopulation->size(); i++)
		delete currentPopulation->at(i);
	delete currentPopulation;
	currentPopulation = nextPopulation;
	nextPopulation = new std::vector<Creature<T>*>();
}

template <class T>
void GeneticAlgorithmAgent<T>::GenerateRandomPopulation()
{
	for (size_t i = 0; i < currentPopulation->size(); i++)
		delete currentPopulation->at(i);
	currentPopulation->clear();
	for (int i = 0; i < popSize; i++)
		currentPopulation->push_back(new Creature<T>(problem->GetProblemSize()));
}

template <class T>
void GeneticAlgorithmAgent<T>::CrossTwoCreatures()
{
	Creature<T>* c1 = Utils::RandElement(currentPopulation);
	Creature<T>* c2 = Utils::RandElement(currentPopulation);
	Creature<T>* p1 = c1->GetFitness() > c2->GetFitness() ? c1 : c2;

	c1 = Utils::RandElement(currentPopulation);
	c2 = Utils::RandElement(currentPopulation);
	Creature<T>* p2 = c1->GetFitness() > c2->GetFitness() ? c1 : c2;

	std::pair<Creature<T>*, Creature<T>*> c = p1->CrossWith(p2);

	nextPopulation->push_back(c.first);
	nextPopulation->push_back(c.second);
}

template <class T>
void GeneticAlgorithmAgent<T>::MutateAll()
{
	for (size_t i = 0; i < nextPopulation->size(); i++)
		nextPopulation->at(i)->Mutate(mutProb);
}

template <class T>
double GeneticAlgorithmAgent<T>::GetBestFitness()
{
	return bestCreature->GetFitness();
}
