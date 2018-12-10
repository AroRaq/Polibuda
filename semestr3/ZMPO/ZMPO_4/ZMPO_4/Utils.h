#pragma once

#include <random>
#include <iostream>

class Utils {
public:
	static bool Chance(double probability);
	static int RandInt(int from, int to);
	template <typename T>
	static T RandElement(const std::vector<T>* vec);

private:
	static std::random_device rd;
	static std::mt19937 gen;
};

template<typename T>
inline T Utils::RandElement(const std::vector<T>* vec)
{
	return vec->at(RandInt(0, (int)vec->size() - 1));
}
