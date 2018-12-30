#pragma once

#include <vector>


class Utils1 {
public:
	template <typename T>
	static bool IsInBounds(int idx, std::vector<T>& vec);
};

template<typename T>
inline bool Utils1::IsInBounds(int idx, std::vector<T>& vec)
{
	return idx >= 0 && idx < vec.size();
}
