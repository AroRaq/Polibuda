#include "Commands.h"

void CommandRunKnapBool::RunCommand()
{
	errorCode errCode = NO_ERROR;
	KnapsackProblem<bool> knapProblem;
	knapProblem.ReadFromFile(FILE_NAME, &errCode);
	GeneticAlgorithmAgent<bool> agent(POP_SIZE, CROSS_PROB, MUT_PROB, &knapProblem, &errCode, 0, 1);
	std::vector<std::string> vec = GetInput({ IN_TIME });
	AgentRunner::RunAgent<bool>(&agent, std::atoi(vec[0].c_str()));
}

void CommandRunKnapInt::RunCommand()
{
	errorCode errCode = NO_ERROR;
	KnapsackProblem<int> knapProblem;
	knapProblem.ReadFromFile(FILE_NAME, &errCode);
	GeneticAlgorithmAgent<int> agent(POP_SIZE, CROSS_PROB, MUT_PROB, &knapProblem, &errCode, 0, 1);
	std::vector<std::string> vec = GetInput({ IN_TIME });
	AgentRunner::RunAgent<int>(&agent, std::atoi(vec[0].c_str()));
}

void CommandRunKnapDouble::RunCommand()
{
	errorCode errCode = NO_ERROR;
	KnapsackProblem<double> knapProblem;
	knapProblem.ReadFromFile(FILE_NAME, &errCode);
	GeneticAlgorithmAgent<double> agent(POP_SIZE, CROSS_PROB, MUT_PROB, &knapProblem, &errCode, 0., 1.);
	std::vector<std::string> vec = GetInput({ IN_TIME });
	AgentRunner::RunAgent<double>(&agent, std::atoi(vec[0].c_str()));
}

void CommandRunOneMax::RunCommand()
{
	std::vector<std::string> vec = GetInput({ IN_PROBLEM_SIZE, IN_TIME });
	errorCode errCode = NO_ERROR;
	OneMaxProblem problem(std::atoi(vec[0].c_str()));
	GeneticAlgorithmAgent<bool> agent(POP_SIZE, CROSS_PROB, MUT_PROB, &problem, &errCode, 0, 1);
	AgentRunner::RunAgent<bool>(&agent, std::atoi(vec[1].c_str()));
}


void CommandRunLeadingOnes::RunCommand()
{
	std::vector<std::string> vec = GetInput({ IN_PROBLEM_SIZE, IN_TIME });
	errorCode errCode = NO_ERROR;
	LeadingOnesProblem problem(std::atoi(vec[0].c_str()));
	GeneticAlgorithmAgent<bool> agent(POP_SIZE, CROSS_PROB, MUT_PROB, &problem, &errCode, 0, 1);
	AgentRunner::RunAgent<bool>(&agent, std::atoi(vec[1].c_str()));
}

