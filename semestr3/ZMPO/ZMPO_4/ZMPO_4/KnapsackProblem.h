#pragma once
#include "Problem.h"
#include <fstream>
#include <iostream>
#include <string>
#include "Error.h"

#define CAPACITY_EXTENSION "_c.txt"
#define WEIGHTS_EXTENSION "_w.txt"
#define VALUES_EXTENSION "_p.txt"
#define IMPLEMENTATION_PROBLEM "B³¹d implementacji: d³ugoœci genotypów nie s¹ takie same"

template <class T>
class KnapsackProblem : public Problem<T> {

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
	~KnapsackProblem();
	double CalculateFitness(std::vector<T> genotype) override;
	size_t GetProblemSize() override;
	void Initiate(int capacity, int itemCount, errorCode* errCode);
	void ReadFromFile(std::string path, errorCode* errCode);
private:
	int capacity;
	std::vector<Item*> items;
};