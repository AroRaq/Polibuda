#include "pch.h"
#include "GeneticAlgorithmAgent.h"

GeneticAlgorithmAgent::GeneticAlgorithmAgent(int popSize, double crossProb, double mutProb, Problem* problem)
{
	generation = 0;
	this->popSize = popSize;
	this->crossProb = crossProb;
	this->mutProb = mutProb;
	this->problem = problem;
	currentPopulation = new std::vector<Creature*>();
	nextPopulation = new std::vector<Creature*>();
	GenerateRandomPopulation();
	bestCreature = new Creature(*currentPopulation->at(0));
}

GeneticAlgorithmAgent::~GeneticAlgorithmAgent()
{
	for (size_t i = 0; i < currentPopulation->size(); i++)
		delete currentPopulation->at(i);
	for (size_t i = 0; i < nextPopulation->size(); i++)
		delete nextPopulation->at(i);
	delete currentPopulation;
	delete nextPopulation;
}

void GeneticAlgorithmAgent::RunGeneration()
{
	generation++;
	///Calculate fitness and set best creature
	for (size_t i = 0; i < currentPopulation->size(); i++) {
		problem->CalculateFitness(currentPopulation->at(i));
		if (currentPopulation->at(i)->GetFitness() > bestCreature->GetFitness()) {
			delete bestCreature;
			bestCreature = new Creature(*currentPopulation->at(i));
		}
	}
	///Create next generation
	while (nextPopulation->size() < popSize) {
		if (Utils::Chance(crossProb)) {
			CrossTwoCreatures();
		}
		else {
			nextPopulation->push_back(new Creature(*Utils::RandElement(currentPopulation)));
			nextPopulation->push_back(new Creature(*Utils::RandElement(currentPopulation)));
		}
	}
	Mutate();
	for (size_t i = 0; i < currentPopulation->size(); i++)
		delete currentPopulation->at(i);
	delete currentPopulation;
	currentPopulation = nextPopulation;
	nextPopulation = new std::vector<Creature*>();
}

void GeneticAlgorithmAgent::GenerateRandomPopulation()
{
	for (size_t i = 0; i < currentPopulation->size(); i++)
		delete currentPopulation->at(i);
	currentPopulation->clear();
	for (int i = 0; i < popSize; i++)
		currentPopulation->push_back(new Creature(problem->GetProblemSize()));
}

void GeneticAlgorithmAgent::CrossTwoCreatures()
{
	Creature* c1 = Utils::RandElement(currentPopulation);
	Creature* c2 = Utils::RandElement(currentPopulation);
	Creature* p1 = c1->GetFitness() > c2->GetFitness() ? c1 : c2;

	c1 = Utils::RandElement(currentPopulation);
	c2 = Utils::RandElement(currentPopulation);
	Creature* p2 = c1->GetFitness() > c2->GetFitness() ? c1 : c2;

	std::pair<Creature*, Creature*> c = p1->CrossWith(p2);

	nextPopulation->push_back(c.first);
	nextPopulation->push_back(c.second);
}

void GeneticAlgorithmAgent::Mutate()
{
	for (size_t i = 0; i < nextPopulation->size(); i++)
		nextPopulation->at(i)->Mutate(mutProb);
}

double GeneticAlgorithmAgent::GetBestFitness()
{
	return bestCreature->GetFitness();
}
