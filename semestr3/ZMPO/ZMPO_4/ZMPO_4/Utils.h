#pragma once

#include <random>
#include <iostream>

class Utils {
public:
	static bool Chance(float probability);
	static int RandInt(int from, int to);
private:
	static std::random_device rd;
	static std::mt19937 gen;
};