#include "CTable.h"

CTable::CTable() {
	name = DEFAULT_NAME;
	std::cout << "bezp: " << name << '\n';
	length = DEFAULT_LENGTH;
	table = new int[length];
	for (int i = 0; i < length; i++)
		table[i] = 0;
}

CTable::CTable(std::string tableName, int tableLen) {
	name = tableName;
	std::cout << "parametr: " << name << '\n';
	length = tableLen;
	table = new int[length];
	for (int i = 0; i < length; i++)
		table[i] = 0;
}

CTable::CTable(const CTable& other) {
	name = other.name + "_copy";
	length = other.length;
	table = new int[length];
	for (int i = 0; i < length; i++)
		table[i] = other.table[i];
	std::cout << "kopiuj: " << name << '\n';
	for (int i = 0; i < length; i++)
		table[i] = 0;
}

CTable::~CTable() {
	std::cout << "usuwam: " << name << '\n';
	delete[] table;
}

void CTable::SetSize(int len, int* excCode) {
	if (len < 0) {
		if (excCode != NULL)
			*excCode = 1;
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

void CTable::SetElement(int n, int val, int* excCode) {
	if (n >= length || n < 0) {
		if (excCode != NULL)
			*excCode = 1;
		return;
	}
	table[n] = val;
}

int CTable::GetElement(int n, int* excCode) {
	if (n >= length || n < 0) {
		if (excCode != NULL) 
			*excCode = 1;
		return -1;
	}
	return table[n];
}

int CTable::GetSize() {
	return length;
}

CTable* CTable::getClone() {
	return new CTable(*this);
}

std::string CTable::toString() {
	std::ostringstream o;
	o << "name: " << name << "\tlen: " << length << "\tvalues: ";
	for (int i = 0; i < length-1; i++)
		o << table[i] << ", ";
	if (length > 0)
		o << table[length - 1];
	o << '\n';
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