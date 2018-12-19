// ZMPO_4.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include "KnapsackProblem.h"
#include "GeneticAlgorithmAgent.h"
#include "Error.h"

#define FILE_NAME "p08"
#define CONSOLE_OUTPUT "Generation %d best: %d\n"
#define GENERATION_NUMBER 10000

int main()
{
	errorCode errCode = NO_ERROR;
	KnapsackProblem knapProblem;
	knapProblem.ReadFromFile(FILE_NAME, &errCode);
	GeneticAlgorithmAgent<bool> agent(10, 0.25, 0.1, &knapProblem, &errCode);
	if (errCode != NO_ERROR) {
		Error::DisplayError(errCode); 
		return 0;
	}
	long lastBest = -1;
	for (int i = 0; i < GENERATION_NUMBER; i++) { 
		agent.RunGeneration();
		long best = (long)agent.GetBestFitness();
		if (lastBest != best) {
			std::printf(CONSOLE_OUTPUT, i, best);
			lastBest = best;
		}
	}
}
