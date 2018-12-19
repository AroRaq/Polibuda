#pragma once

#include <random>
#include <iostream>

class Utils {
public:
	static bool Chance(double probability);
	static int RandInt(int from, int to);
	template <typename T>
	static T RandElement(const std::vector<T>* vec);
	template <typename T>
	static void disposeVector(std::vector<T>* vec);

private:
	static std::random_device rd;
	static std::mt19937 gen;
};

template<typename T>
inline T Utils::RandElement(const std::vector<T>* vec)
{
	return vec->at(RandInt(0, (int)vec->size() - 1));
}

template<typename T>
inline void Utils::disposeVector(std::vector<T>* vec)
{
	for (size_t i = 0; i < vec->size(); i++)
		delete vec->at(i);
	delete vec;
}
