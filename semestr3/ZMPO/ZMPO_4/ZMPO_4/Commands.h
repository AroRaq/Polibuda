#pragma once

#include "CMenuCommand.h"
#include "KnapsackProblem.h"
#include "OneMaxProblem.h"
#include "LeadingOnesProblem.h"
#include "GeneticAlgorithmAgent.h"
#include "Utils.h"
#include <ctime>


#define FILE_NAME "p08"
#define CONSOLE_OUTPUT "Generation %d best: %d\n"
#define RUN_TIME_SECONDS 10

class CommandRunKnapBool : public CCommand {
public:
	void RunCommand() override;
};

class CommandRunKnapInt : public CCommand {
public:
	void RunCommand() override;
};

class CommandRunKnapDouble : public CCommand {
public:
	void RunCommand() override;
};

class CommandRunOneMax : public CCommand {
public:
	void RunCommand() override;
};

class CommandRunLeadingOnes : public CCommand {
public:
	void RunCommand() override;
};






class AgentRunner {
public:
	template <class T>
	static void RunAgent(GeneticAlgorithmAgent<T>* agent, int seconds);
};

template<class T>
inline void AgentRunner::RunAgent(GeneticAlgorithmAgent<T>* agent, int seconds)
{
	long lastBest = -1;
	std::clock_t start = std::clock();
	for (int i = 0; (std::clock() - start) / (double)CLOCKS_PER_SEC < seconds; i++) {
		agent->RunGeneration();
		long best = (long)agent->GetBestFitness();
		if (lastBest != best) {
			std::printf(CONSOLE_OUTPUT, i, best);
			lastBest = best;
		}
	}
}
