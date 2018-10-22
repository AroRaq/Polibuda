#include "CTable.h"

CTable::CTable() {
	name = DEFAULT_NAME;
	std::cout << BEZP << name << std::endl;
	length = DEFAULT_LENGTH;
	table = new int[length];
	for (int i = 0; i < length; i++)
		table[i] = 0;
}

CTable::CTable(std::string tableName, int tableLen) {
	name = tableName;
	std::cout << PARAMETR << name << std::endl;
	length = tableLen;
	table = new int[length];
	for (int i = 0; i < length; i++)
		table[i] = 0;
}

CTable::CTable(const CTable& other) {
	name = other.name + COPY;
	length = other.length;
	table = new int[length];
	for (int i = 0; i < length; i++)
		table[i] = other.table[i];
	std::cout << KOPIUJ << name << std::endl;
	for (int i = 0; i < length; i++)
		table[i] = 0;
}

CTable::~CTable() {
	std::cout << USUWAM << name << std::endl;
	delete[] table;
}

void CTable::SetSize(int len, int* excCode) {
	if (len < 0) {
		if (excCode != NULL)
			*excCode = 2;
		return;
	}
	if (len == length)
		return;
	
	int* newTab = new int[len];
	for (int i = 0; i < length && i < len; i++)
		newTab[i] = table[i];
	for (int i = length; i < len; i++)
		newTab[i] = 0;
	length = len;
	delete[] table;
	table = newTab;
}

void CTable::SetName(std::string sName) {
	name = sName;
}

void CTable::SetElement(int idx, int val, int* excCode) {
	if (!IsInBounds(idx)) {
		if (excCode != NULL)
			*excCode = 1;
		return;
	}
	table[idx] = val;
}

int CTable::GetElement(int idx, int* excCode) {
	if (!IsInBounds(idx)) {
		if (excCode != NULL) 
			*excCode = 1;
		return -1;
	}
	return table[idx];
}

int CTable::GetSize() {
	return length;
}

CTable* CTable::getClone() {
	return new CTable(*this);
}

std::string CTable::toString() {
	std::ostringstream o;
	o << NAME << name << '\t' << LEN << length << '\t' << VALUES;
	for (int i = 0; i < length-1; i++)
		o << table[i] << ", ";
	if (length > 0)
		o << table[length - 1];
	o << std::endl;
	return o.str();
}

std::string CTable::GetName() {
	return name;
}

void CTable::AssignValues(const CTable& other) {
	SetSize(other.length);
	for (int i = 0; i < other.length; i++)
		table[i] = other.table[i];
}

bool CTable::IsInBounds(int idx) {
	return idx >= 0 && idx < length;
}