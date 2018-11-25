#include "pch.h"
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
	std::uniform_int_distribution<int> dis(from, to);
	int ret = dis(gen);
	//std::cout << ret << std::endl;
	return ret;
}
