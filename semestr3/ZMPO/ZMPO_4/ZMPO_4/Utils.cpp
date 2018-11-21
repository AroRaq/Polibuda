#include "pch.h"
#include "Utils.h"

bool Utils::Chance(float probability)
{
	if (gen.seed == gen.default_seed)
		gen.seed = rd();
	std::uniform_real_distribution<double> dis(0.0, 1.0);
	return dis(gen) < probability;
}

int Utils::RandInt(int from, int to)
{
	if (gen.seed == gen.default_seed)
		gen.seed = rd();
	std::uniform_int_distribution<int> dis(from, to);
	return dis(gen);
}
