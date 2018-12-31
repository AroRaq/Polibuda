#include "Utils.h"

std::random_device Utils::rd;
std::mt19937 Utils::gen(rd());

bool Utils::Chance(double probability)
{
	std::uniform_real_distribution<double> dis(0.0, 1.0);
	return dis(gen) < probability;
}

int Utils::RandInt(int from, int to)
{
	std::uniform_int_distribution<> dis(from, to);
	return dis(gen);
}

double Utils::RandDouble(double from, double to)
{
	std::uniform_real_distribution<> dis(from, to);
	return dis(gen);
}
