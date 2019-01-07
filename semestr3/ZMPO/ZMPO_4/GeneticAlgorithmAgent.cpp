#include "GeneticAlgorithmAgent.h"


template <class T>
GeneticAlgorithmAgent<T>::GeneticAlgorithmAgent(int popSize, double crossProb, double mutProb, Problem<T>* problem, errorCode* errCode, T left, T right)
{
	leftBound = left;
	rightBound = right;
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
	Utils::disposeVector(currentPopulation);
	Utils::disposeVector(nextPopulation);
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
	Utils::disposeVector(currentPopulation);
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
		currentPopulation->push_back(new Creature<T>(problem->GetProblemSize(), crossProb, leftBound, rightBound));
}

template <class T>
void GeneticAlgorithmAgent<T>::CrossTwoCreatures()
{
	
	nextPopulation->push_back(*DrawParent() + *DrawParent());
}

template <class T>
void GeneticAlgorithmAgent<T>::MutateAll()
{
	for (size_t i = 0; i < nextPopulation->size(); i++)
		(*(nextPopulation->at(i)))++;
}

template<class T>
Creature<T>* GeneticAlgorithmAgent<T>::DrawParent()
{
	Creature<T>* c1 = Utils::RandElement(currentPopulation);
	Creature<T>* c2 = Utils::RandElement(currentPopulation);
	return c1->GetFitness() > c2->GetFitness() ? c1 : c2;
}

template <class T>
double GeneticAlgorithmAgent<T>::GetBestFitness()
{
	return bestCreature->GetFitness();
}

template GeneticAlgorithmAgent<bool>;
template GeneticAlgorithmAgent<int>;
template GeneticAlgorithmAgent<double>;