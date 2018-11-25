#include "Optimizer.h"

#include <cfloat>
#include <iostream>
#include <windows.h>

using namespace std;

COptimizer::COptimizer(CEvaluator &cEvaluator)
	: c_evaluator(cEvaluator)
{
	random_device c_seed_generator;
	c_rand_engine.seed(c_seed_generator());

	d_current_best_fitness = 0;
}//COptimizer::COptimizer(CEvaluator &cEvaluator)

void COptimizer::vInitialize()
{
	d_current_best_fitness = -DBL_MAX;
	v_current_best.clear();
}//void COptimizer::vInitialize()

void COptimizer::vRunIteration()
{
	vector<int> v_candidate;
	v_fill_randomly(v_candidate);

	double d_candidate_fitness = c_evaluator.dEvaluate(v_candidate);

	if (d_candidate_fitness > d_current_best_fitness)
	{
		v_current_best = v_candidate;
		d_current_best_fitness = d_candidate_fitness;

		cout << d_current_best_fitness << endl;
	}//if (d_candidate_fitness > d_current_best_fitness)
}//void COptimizer::vRunIteration()

void COptimizer::v_fill_randomly(vector<int> &vSolution)
{
	uniform_int_distribution<int> c_uniform_int_distribution(iBIT_FALSE, iBIT_TRUE);
	vSolution.resize((size_t)c_evaluator.iGetNumberOfBits());

	for (size_t i = 0; i < vSolution.size(); i++)
	{
		vSolution.at(i) = c_uniform_int_distribution(c_rand_engine);
	}//for (size_t i = 0; i < vSolution.size(); i++)
}//void COptimizer::v_fill_randomly(const vector<int> &vSolution)
