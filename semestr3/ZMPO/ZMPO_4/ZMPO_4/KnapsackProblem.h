#pragma once
#include "Problem.h"
#include <fstream>
#include <iostream>
#include <string>

class KnapsackProblem : public Problem {
	class Item {
	public:
		Item(int weight, int value) : weight(weight), value(value) {}
		int GetValue() { return value; }
		int GetWeight() { return weight; }
	private:
		int weight;
		int value;
	};


public:
	double CalculateFitness(Creature* creature) override;
	int GetProblemSize() override;
	void Initiate(int capacity, int itemCount);
	void SetProblem();
	void ReadFromFile(std::string path);
private:
	int capacity;
	std::vector<Item*> items;
};