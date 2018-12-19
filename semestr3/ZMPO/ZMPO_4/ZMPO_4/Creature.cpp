#include "Creature.h"


template <class T>
Creature<T>::Creature(size_t genotypeLength)
{
	for (int i = 0; i < genotypeLength; i++)
		genotype.push_back(Utils::Chance(0.5));
}

template <class T>
void Creature<T>::SetFitness(double fitness)
{
	this->fitness = fitness;
}

template <class T>
double Creature<T>::GetFitness() const
{
	return fitness;
}

template <class T>
std::pair<Creature<T>*, Creature<T>*> Creature<T>::CrossWith(const Creature<T>* other) const
{
	int divAt = Utils::RandInt(1, (int)genotype.size() - 2);
	Creature* child1 = new Creature(*this);
	Creature* child2 = new Creature(*other);
	if (divAt > genotype.size() / 2) {
		for (size_t i = divAt; i < genotype.size(); i++) {
			child1->genotype[i] = other->genotype[i];
			child2->genotype[i] = genotype[i];
		}
	}
	else {
		for (size_t i = 0; i < divAt; i++) {
			child1->genotype[i] = other->genotype[i];
			child2->genotype[i] = genotype[i];
		}
	}
	return std::make_pair(child1, child2);
}

template <>
void Creature<bool>::Mutate(double probability) {
	for (size_t i = 0; i < genotype.size(); i++)
		if (Utils::Chance(probability))
			genotype[i] = !genotype[i];
}

template <>
void Creature<int>::Mutate(double probability) {
	for (size_t i = 0; i < genotype.size(); i++)
		if (Utils::Chance(probability))
			genotype[i] = !genotype[i];
}

template <>
void Creature<double>::Mutate(double probability) {
	for (size_t i = 0; i < genotype.size(); i++)
		if (Utils::Chance(probability))
			genotype[i] = !genotype[i];
}

template <class T>
void Creature<T>::Mutate(double probability)
{
	for (size_t i = 0; i < genotype.size(); i++)
		if (Utils::Chance(probability))
			genotype[i] = !genotype[i];
}

template <class T>
std::vector<T> Creature<T>::GetGenotype() const
{
	return genotype;
}

template class Creature<bool>;
template class Creature<int>;
template class Creature<double>;