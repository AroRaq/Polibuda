#include "Evaluator.h"

#include "Configuration.h"

#include <algorithm>
#include <iostream>
#include <random>
#include <stdexcept>


CEvaluator::CEvaluator()
{
	bConfigure(iINIT_NUMBER_OF_BITS, dINIT_MAX_VALUE);
}//CEvaluator::CEvaluator()

bool CEvaluator::bConfigure(int iNumberOfBits, double dMaxValue)
{
	bool b_success = true;

	i_number_of_bits = iNumberOfBits;
	
	if (i_number_of_bits <= 0)
	{
		b_success = false;
	}//if (i_number_of_bits <= 0)

	d_max_value = dMaxValue;

	if (d_max_value <= 0)
	{
		b_success = false;
	}//if (d_max_value <= 0)

	return b_success;
}//bool CEvaluator::bConfigure(int iNumberOfBits, double dMaxValue)

double CEvaluator::dEvaluate(vector<int> *pvGenotype)
{
	double d_value;

	if (pvGenotype != nullptr)
	{
		d_value = dEvaluate(pvGenotype->data());
	}//if (pvGenotype != nullptr)
	else
	{
		d_value = dWRONG_VALUE;
	}//else if (pvGenotype != nullptr)

	return d_value;
}//double CEvaluator::dEvaluate(vector<int> *pvGenotype)


bool CMaskedEvaluator::bConfigure(int iNumberOfBits, double dMaxValue, int iMaskSeed)
{
	bool b_success = CEvaluator::bConfigure(iNumberOfBits, dMaxValue);

	if (b_success == true)
	{
		v_masked_genotype.resize((size_t)iNumberOfBits);
		v_mask.resize(v_masked_genotype.size());

		iota(v_mask.begin(), v_mask.end(), 0);

		if (iMaskSeed != iSEED_NO_MASK)
		{
			mt19937 c_random(iMaskSeed);
			shuffle(v_mask.begin(), v_mask.end(), c_random);
		}//if (iMaskSeed != iSEED_NO_MASK)
	}//if (b_success == true)

	return b_success;
}//bool CMaskedEvaluator::bConfigure(int iNumberOfBits, double dMaxValue, int iMaskSeed)

double CMaskedEvaluator::dEvaluate(const int *piGenotype)
{
	double d_value;

	if (i_number_of_bits == (int)v_masked_genotype.size() && v_masked_genotype.size() == v_mask.size())
	{
		for (size_t i = 0; i < v_masked_genotype.size(); i++)
		{
			v_masked_genotype.at(v_mask.at(i)) = *(piGenotype + i);
		}//for (size_t i = 0; i < v_masked_genotype.size(); i++)

		d_value = d_evaluate(v_masked_genotype);
	}//if (i_number_of_bits == (int)v_masked_genotype.size() && v_masked_genotype.size() == v_mask.size())
	else
	{
		d_value = dWRONG_VALUE;
	}//else if (i_number_of_bits == (int)v_masked_genotype.size() && v_masked_genotype.size() == v_mask.size())

	return d_value;
}//double CMaskedEvaluator::dEvaluate(const int *piGenotype)


CP3Evaluator::CP3Evaluator()
{
	pc_p3_evaluator = nullptr;
}//CP3Evaluator::CP3Evaluator()

CP3Evaluator::~CP3Evaluator()
{
	delete pc_p3_evaluator;
}//CP3Evaluator::~CP3Evaluator()

double CP3Evaluator::d_evaluate(const vector<int> &vMaskedGenotype)
{
	double d_value;

	if (pc_p3_evaluator != nullptr && (int)v_p3_genotype.size() == i_number_of_bits && v_p3_genotype.size() == vMaskedGenotype.size())
	{
		for (size_t i = 0; i < v_p3_genotype.size(); i++)
		{
			v_p3_genotype.at(i) = vMaskedGenotype.at(i) == iBIT_TRUE;
		}//for (size_t i = 0; i < v_p3_genotype.size(); i++)

		d_value = (double)pc_p3_evaluator->evaluate(v_p3_genotype);
	}//if (piGenotype != nullptr && pc_p3_evaluator != nullptr && v_p3_genotype.size() == (size_t)i_number_of_bits)
	else
	{
		d_value = dWRONG_VALUE;
	}//else if (piGenotype != nullptr && pc_p3_evaluator != nullptr && v_p3_genotype.size() == (size_t)i_number_of_bits)

	return d_value;
}//double CP3Evaluator::d_evaluate(const vector<int> &vMaskedGenotype)


bool CIsingSpinGlassEvaluator::bConfigure(int iNumberOfBits, int iProblemSeed, int iMaskSeed)
{
	delete pc_p3_evaluator;

	bool b_success = CMaskedEvaluator::bConfigure(iNumberOfBits, dP3_EVALUATOR_MAX_VALUE, iMaskSeed);

	if (b_success == true && iProblemSeed < iP3_EVALUATOR_MIN_PROBLEM_SEED)
	{
		b_success = false;
	}//if (b_success == true && iProblemSeed < iP3_EVALUATOR_MIN_PROBLEM_SEED)

	if (b_success == true && iProblemSeed > iP3_EVALUATOR_MAX_PROBLEM_SEED)
	{
		b_success = false;
	}//if (b_success == true && iProblemSeed > iP3_EVALUATOR_MAX_PROBLEM_SEED)

	if (b_success == true)
	{
		Configuration c_config;

		c_config.set(sP3_EVALUATOR_CONFIG_NAME_LENGTH, iNumberOfBits);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_PRECISION, iP3_EVALUATOR_PRECISION);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_PROBLEM_SEED, iProblemSeed);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_PROBLEM_FOLDER, sP3_EVALUATOR_PROBLEM_FOLDER);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_ISING_TYPE, sP3_EVALUATOR_ISING_TYPE);

		try 
		{
			pc_p3_evaluator = new IsingSpinGlass(c_config, iP3_EVALUATOR_RUN_NUMBER);
		}//try
		catch (invalid_argument &c_exception)
		{
			cout << c_exception.what() << endl;
			b_success = false;
		}//catch (invalid_argument &c_exception)
		
		v_p3_genotype.resize((size_t)iNumberOfBits);
	}//if (b_success == true)

	return b_success;
}//bool CIsingSpinGlassEvaluator::bConfigure(int iNumberOfBits, int iProblemSeed, int iMaskSeed)


bool CLeadingOnesEvaluator::bConfigure(int iNumberOfBits, int iMaskSeed)
{
	delete pc_p3_evaluator;

	bool b_success = CMaskedEvaluator::bConfigure(iNumberOfBits, dP3_EVALUATOR_MAX_VALUE, iMaskSeed);

	if (b_success == true)
	{
		Configuration c_config;

		c_config.set(sP3_EVALUATOR_CONFIG_NAME_LENGTH, iNumberOfBits);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_PRECISION, iP3_EVALUATOR_PRECISION);

		pc_p3_evaluator = new LeadingOnes(c_config, iP3_EVALUATOR_RUN_NUMBER);
		v_p3_genotype.resize((size_t)iNumberOfBits);
	}//if (b_success == true)

	return b_success;
}//bool CLeadingOnesEvaluator::bConfigure(int iNumberOfBits, int iMaskSeed)

bool CMaxSatEvaluator::bConfigure(int iNumberOfBits, int iProblemSeed, float fClauseRatio, int iMaskSeed)
{
	delete pc_p3_evaluator;

	bool b_success = CMaskedEvaluator::bConfigure(iNumberOfBits, dP3_EVALUATOR_MAX_VALUE, iMaskSeed);

	if (b_success == true && iProblemSeed < iP3_EVALUATOR_MIN_PROBLEM_SEED)
	{
		b_success = false;
	}//if (b_success == true && iProblemSeed < iP3_EVALUATOR_MIN_PROBLEM_SEED)

	if (b_success == true && iProblemSeed > iP3_EVALUATOR_MAX_PROBLEM_SEED)
	{
		b_success = false;
	}//if (b_success == true && iProblemSeed > iP3_EVALUATOR_MAX_PROBLEM_SEED)

	if (b_success == true && fClauseRatio <= 0)
	{
		b_success = false;
	}//if (b_success == true && fClauseRatio <= 0)

	if (b_success == true)
	{
		Configuration c_config;

		c_config.set(sP3_EVALUATOR_CONFIG_NAME_LENGTH, iNumberOfBits);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_PRECISION, iP3_EVALUATOR_PRECISION);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_PROBLEM_SEED, iProblemSeed);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_CLAUSE_RATIO, fClauseRatio);

		pc_p3_evaluator = new MAXSAT(c_config, iP3_EVALUATOR_RUN_NUMBER);
		v_p3_genotype.resize((size_t)iNumberOfBits);
	}//if (b_success == true)

	return b_success;
}//bool CMaxSatEvaluator::bConfigure(int iNumberOfBits, int iProblemSeed, float fClauseRatio, int iMaskSeed)

bool CNearestNeighborNKEvaluator::bConfigure(int iNumberOfBits, int iProblemSeed, int iK, int iMaskSeed)
{
	delete pc_p3_evaluator;

	bool b_success = CMaskedEvaluator::bConfigure(iNumberOfBits, dP3_EVALUATOR_MAX_VALUE, iMaskSeed);

	if (b_success == true && iProblemSeed < iP3_EVALUATOR_MIN_PROBLEM_SEED)
	{
		b_success = false;
	}//if (b_success == true && iProblemSeed < iP3_EVALUATOR_MIN_PROBLEM_SEED)

	if (b_success == true && iProblemSeed > iP3_EVALUATOR_MAX_PROBLEM_SEED)
	{
		b_success = false;
	}//if (b_success == true && iProblemSeed > iP3_EVALUATOR_MAX_PROBLEM_SEED)

	if (b_success == true && iK <= 0)
	{
		b_success = false;
	}//if (b_success == true && iK <= 0)

	if (b_success == true && iNumberOfBits % iK != 0)
	{
		b_success = false;
	}//if (b_success == true && iK <= 0)

	if (b_success == true)
	{
		Configuration c_config;

		c_config.set(sP3_EVALUATOR_CONFIG_NAME_LENGTH, iNumberOfBits);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_PRECISION, iP3_EVALUATOR_PRECISION);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_PROBLEM_SEED, iProblemSeed);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_K, iK);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_PROBLEM_FOLDER, sP3_EVALUATOR_PROBLEM_FOLDER);

		pc_p3_evaluator = new NearestNeighborNK(c_config, iP3_EVALUATOR_RUN_NUMBER);
		v_p3_genotype.resize((size_t)iNumberOfBits);
	}//if (b_success == true)

	return b_success;
}//bool CNearestNeighborNKEvaluator::bConfigure(int iNumberOfBits, int iProblemSeed, int iK, int iMaskSeed)


bool COneMaxEvaluator::bConfigure(int iNumberOfBits, int iMaskSeed)
{
	delete pc_p3_evaluator;

	bool b_success = CMaskedEvaluator::bConfigure(iNumberOfBits, dP3_EVALUATOR_MAX_VALUE, iMaskSeed);

	if (b_success == true)
	{
		Configuration c_config;

		c_config.set(sP3_EVALUATOR_CONFIG_NAME_LENGTH, iNumberOfBits);

		pc_p3_evaluator = new OneMax(c_config, iP3_EVALUATOR_RUN_NUMBER);
		v_p3_genotype.resize((size_t)iNumberOfBits);
	}//if (b_success == true)

	return b_success;
}//bool COneMaxEvaluator::bConfigure(int iNumberOfBits, int iMaskSeed)


bool CRastriginEvaluator::bConfigure(int iNumberOfBits, int iBitsPerFloat, int iMaskSeed)
{
	delete pc_p3_evaluator;

	bool b_success = CMaskedEvaluator::bConfigure(iNumberOfBits, dP3_EVALUATOR_MAX_VALUE, iMaskSeed);

	if (b_success == true && iBitsPerFloat <= 0)
	{
		b_success = false;
	}//if (b_success == true && iBitsPerFloat <= 0)

	if (b_success == true && iNumberOfBits % iBitsPerFloat != 0)
	{
		b_success = false;
	}//if (b_success == true && iNumberOfBits % iBitsPerFloat != 0)

	if (b_success == true)
	{
		Configuration c_config;

		c_config.set(sP3_EVALUATOR_CONFIG_NAME_LENGTH, iNumberOfBits);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_PRECISION, iP3_EVALUATOR_PRECISION);
		c_config.set(sP3_EVALUATOR_CONFIG_NAME_BITS_PER_FLOAT, iBitsPerFloat);

		pc_p3_evaluator = new Rastrigin(c_config, iP3_EVALUATOR_RUN_NUMBER);
		v_p3_genotype.resize((size_t)iNumberOfBits);
	}//if (b_success == true)

	return b_success;
}//bool CRastriginEvaluator::bConfigure(int iNumberOfBits, int iBitsPerFloat, int iMaskSeed)