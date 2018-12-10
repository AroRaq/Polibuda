#include "pch.h"
#include "KnapsackProblem.h"

double KnapsackProblem::CalculateFitness(Creature* creature)
{
	int currValue = 0;
	int currWeight = 0;
	std::vector<int> genotype = creature->GetGenotype();
	if (genotype.size() != items.size())
		throw std::invalid_argument(IMPLEMENTATION_PROBLEM);
	for (size_t i = 0; i < items.size(); i++) {
		if (genotype[i]) {
			currValue += items[i]->GetValue();
			currWeight += items[i]->GetWeight();
			if (currWeight > capacity) {
				creature->SetFitness(0);
				return 0;
			}
		}
	}
	creature->SetFitness(currValue);
	return currValue;
}

size_t KnapsackProblem::GetProblemSize()
{
	return items.size();
}

void KnapsackProblem::Initiate(int capacity, int itemCount) 
{
	this->capacity = capacity;
	for (int i = 0; i < itemCount; i++)
		items.push_back(new Item(Utils::RandInt(1, 100), Utils::RandInt(0, 100)));
}

void KnapsackProblem::ReadFromFile(std::string path) {
	std::ifstream fcapacity(path + CAPACITY_EXTENSION);
	std::ifstream weights(path + WEIGHTS_EXTENSION);
	std::ifstream values(path + VALUES_EXTENSION);
	fcapacity >> capacity;
	fcapacity.close();

	int weight;
	int value;
	while (weights >> weight) {
		values >> value;
		items.push_back(new Item(weight, value));
	}
	weights.close();
	values.close();
}