#include "Creature.h"


template <>
Creature<bool>::Creature(size_t genotypeLength, double crossProbability, bool left, bool right)
{
	for (size_t i = 0; i < genotypeLength; i++)
		genotype.push_back(Utils::Chance(0.5));
	this->crossProbability = crossProbability;
}

template <>
Creature<int>::Creature(size_t genotypeLength, double crossProbability, int left, int right)
{
	for (size_t i = 0; i < genotypeLength; i++)
		genotype.push_back(Utils::RandInt(left, right));
	this->crossProbability = crossProbability;
	leftBound = left;
	rightBound = right;
}

template <>
Creature<double>::Creature(size_t genotypeLength, double crossProbability, double left, double right)
{
	for (size_t i = 0; i < genotypeLength; i++)
		genotype.push_back(Utils::RandDouble(left, right));
	this->crossProbability = crossProbability;
	leftBound = left;
	rightBound = right;
}

template<class T>
Creature<T>& Creature<T>::operator++(int)
{
	Mutate();
	return *this;
}

template<class T>
Creature<T>* Creature<T>::operator+(Creature& other)
{
	std::pair<Creature*, Creature*> p = CrossWith(other);
	delete p.second;
	return p.first;
}

template<>
Creature<bool>* Creature<bool>::operator-(Creature& other)
{
	Creature* child = new Creature(*this);
	for (int i = 0; i < child->genotype.size(); i++) {
		child->genotype[i] = genotype[i] ^ other.genotype[i];
	}
	return child;
}

template<>
Creature<int>* Creature<int>::operator-(Creature& other)
{
	Creature* child = new Creature(*this);
	for (int i = 0; i < child->genotype.size(); i++) {
		child->genotype[i] = std::max(0, other.genotype[i] - child->genotype[i]);
	}
	return child;
}

template<>
Creature<double>* Creature<double>::operator-(Creature& other)
{
	Creature* child = new Creature(*this);
	for (int i = 0; i < child->genotype.size(); i++) {
		child->genotype[i] = std::max(0., other.genotype[i] - child->genotype[i]);
	}
	return child;
}

template<class T>
Creature<T>* Creature<T>::operator-(Creature & other)
{
	return nullptr;
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
std::pair<Creature<T>*, Creature<T>*> Creature<T>::CrossWith(const Creature<T>& other) const
{
	int divAt = Utils::RandInt(1, (int)genotype.size() - 2);
	Creature* child1 = new Creature(*this);
	Creature* child2 = new Creature(other);
	if (divAt > (int)genotype.size() / 2) {
		for (size_t i = divAt; i < genotype.size(); i++) {
			child1->genotype[i] = other.genotype[i];
			child2->genotype[i] = genotype[i];
		}
	}
	else {
		for (size_t i = 0; i < divAt; i++) {
			child1->genotype[i] = other.genotype[i];
			child2->genotype[i] = genotype[i];
		}
	}
	return std::make_pair(child1, child2);
}

template <>
void Creature<bool>::Mutate() {
	for (size_t i = 0; i < genotype.size(); i++)
		if (Utils::Chance(crossProbability))
			genotype[i] = !genotype[i];
}

template <>
void Creature<int>::Mutate() {
	for (size_t i = 0; i < genotype.size(); i++)
		if (Utils::Chance(crossProbability))
			genotype[i] = Utils::RandInt(leftBound, rightBound);
}

template <>
void Creature<double>::Mutate() {
	for (size_t i = 0; i < genotype.size(); i++)
		if (Utils::Chance(crossProbability))
			genotype[i] = Utils::RandDouble(leftBound, rightBound);
}

template <class T>
std::vector<T> Creature<T>::GetGenotype() const
{
	return genotype;
}

template class Creature<bool>;
template class Creature<int>;
template class Creature<double>;