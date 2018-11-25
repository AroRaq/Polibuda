#include "pch.h"
#include "KnapsackProblem.h"

double KnapsackProblem::CalculateFitness(Creature* creature)
{
	int currValue = 0;
	int currWeight = 0;
	std::vector<bool> genotype = creature->GetGenotype();
	if (genotype.size() != items.size())
		throw std::invalid_argument("B³¹d implementacji: d³ugoœci genotypów nie s¹ takie same");
	for (int i = 0; i < items.size(); i++) {
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

int KnapsackProblem::GetProblemSize()
{
	return items.size();
}

void KnapsackProblem::Initiate(int capacity, int itemCount) 
{
	this->capacity = capacity;
	for (int i = 0; i < itemCount; i++)
		items.push_back(new Item(Utils::RandInt(1, 100), Utils::RandInt(0, 100)));
}

void KnapsackProblem::SetProblem() {
	capacity = 165;
	for (int i = 0; i < items.size(); i++)
		delete items[i];
	items.clear();
	items.push_back(new Item(23, 92));
	items.push_back(new Item(31, 57));
	items.push_back(new Item(29, 49));
	items.push_back(new Item(44, 68));
	items.push_back(new Item(53, 60));

	items.push_back(new Item(38, 43));
	items.push_back(new Item(63, 67));
	items.push_back(new Item(85, 84));
	items.push_back(new Item(89, 87));
	items.push_back(new Item(82, 72));
}