#pragma once

#include "Evaluation.h"

#include <string>
#include <vector>

using namespace std;

#define dWRONG_VALUE -1

#define iINIT_NUMBER_OF_BITS 0
#define dINIT_MAX_VALUE 0

#define iSEED_NO_MASK -1

#define iBIT_FALSE 0
#define iBIT_TRUE 1

#define iP3_EVALUATOR_RUN_NUMBER 0
#define dP3_EVALUATOR_MAX_VALUE 1.0
#define iP3_EVALUATOR_PRECISION 65536
#define iP3_EVALUATOR_MIN_PROBLEM_SEED 0
#define iP3_EVALUATOR_MAX_PROBLEM_SEED 199
#define sP3_EVALUATOR_PROBLEM_FOLDER string("")
#define sP3_EVALUATOR_ISING_TYPE string("pm")

#define sP3_EVALUATOR_CONFIG_NAME_LENGTH "length"
#define sP3_EVALUATOR_CONFIG_NAME_PRECISION "precision"
#define sP3_EVALUATOR_CONFIG_NAME_BITS_PER_FLOAT "bits_per_float"
#define sP3_EVALUATOR_CONFIG_NAME_PROBLEM_SEED "problem_seed"
#define sP3_EVALUATOR_CONFIG_NAME_PROBLEM_FOLDER "problem_folder"
#define sP3_EVALUATOR_CONFIG_NAME_K "k"
#define sP3_EVALUATOR_CONFIG_NAME_ISING_TYPE "ising_type"
#define sP3_EVALUATOR_CONFIG_NAME_CLAUSE_RATIO "clause_ratio"


class CEvaluator
{
public:
	CEvaluator();
	
	virtual ~CEvaluator() = default;

	bool bConfigure(int iNumberOfBits, double dMaxValue);

	virtual double dEvaluate(const int *piGenotype) = 0;
	double dEvaluate(vector<int> *pvGenotype);
	double dEvaluate(const vector<int> &vGenotype) { return dEvaluate(vGenotype.data()); }

	int iGetNumberOfBits() { return i_number_of_bits; }
	double dGetMaxValue() { return d_max_value; }

protected:
	int i_number_of_bits;
	double d_max_value;
};//class CEvaluator


class CMaskedEvaluator : public CEvaluator
{
public:
	bool bConfigure(int iNumberOfBits, double dMaxValue, int iMaskSeed);

	virtual double dEvaluate(const int *piGenotype);

protected:
	virtual double d_evaluate(const vector<int> &vMaskedGenotype) = 0;

private:
	vector<int> v_mask;
	vector<int> v_masked_genotype;
};//class CMaskedEvaluator : public CEvaluator


class CP3Evaluator : public CMaskedEvaluator
{
public:
	CP3Evaluator(const CP3Evaluator &cOther) = delete;
	CP3Evaluator(const CP3Evaluator &&cOther) = delete;

	virtual ~CP3Evaluator();

	CP3Evaluator& operator=(const CP3Evaluator &cOther) = delete;
	CP3Evaluator& operator=(const CP3Evaluator &&cOther) = delete;

protected:
	CP3Evaluator();

	virtual double d_evaluate(const vector<int> &vMaskedGenotype);

	Evaluator *pc_p3_evaluator;
	vector<bool> v_p3_genotype;
};//class CP3Evaluator : public CEvaluator


class CIsingSpinGlassEvaluator : public CP3Evaluator
{
public:
	bool bConfigure(int iNumberOfBits, int iProblemSeed, int iMaskSeed);
};//class CIsingSpinGlassEvaluator : public CP3Evaluator


class CLeadingOnesEvaluator : public CP3Evaluator
{
public:
	bool bConfigure(int iNumberOfBits, int iMaskSeed);
};//class CLeadingOnesEvaluator : public CP3Evaluator


class CMaxSatEvaluator : public CP3Evaluator
{
public:
	bool bConfigure(int iNumberOfBits, int iProblemSeed, float fClauseRatio, int iMaskSeed);
};//class CMaxSatEvaluator : public CP3Evaluator


class CNearestNeighborNKEvaluator : public CP3Evaluator
{
public:
	bool bConfigure(int iNumberOfBits, int iProblemSeed, int iK, int iMaskSeed);
};//class CNearestNeighborNKEvaluator : public CP3Evaluator


class COneMaxEvaluator : public CP3Evaluator
{
public:
	bool bConfigure(int iNumberOfBits, int iMaskSeed);
};//class COneMaxEvaluator : public CP3Evaluator


class CRastriginEvaluator : public CP3Evaluator
{
public:
	bool bConfigure(int iNumberOfBits, int iBitsPerFloat, int iMaskSeed);
};//class CRastriginEvaluator : public CP3Evaluator