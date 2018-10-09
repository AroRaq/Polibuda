#include "CTable.h"

CTable::CTable() {
	s_name = new std::string("nazwa");
	std::cout << "bezp: " << *s_name;
	*i_length = DEFAULT_LENGTH;
	table = new int[*i_length];
}

CTable::CTable(std::string sName, int iTableLen) {
	*s_name = sName;
	std::cout << "parametr: " << *s_name;
	*i_length = iTableLen;
	table = new int[*i_length];
}

CTable::CTable(CTable& other) {
	*s_name = *(other.s_name) + "_copy";
	*i_length = *(other.i_length);
	table = new int[*i_length];
	for (int i = 0; i < *i_length; i++)
		table[i] = other.table[i];
	std::cout << "kopiuj: " << *s_name;
}

CTable::~CTable() {
	std::cout << "usuwam: " << *s_name;
	delete[] table;
	delete i_length;
	delete s_name;
}

void CTable::vResize(int len, int* excCode) {
	if (len < 0) {
		*excCode = 1;
		return;
	}
	if (len == *i_length)
		return;
	if (len < *i_length) {
		*i_length = len;
		int* newTab = new int[len];
		for (int i = 0; i < len; i++)
			newTab[i] = table[i];
		delete[] table;
		table = newTab;
	}
	else {
		*i_length = len;
		int* newTab = new int[len];
		for (int i = 0; i < *i_length; i++)
			newTab[i] = table[i];
		delete[] table;
		table = newTab;
	}
}

void CTable::vSetName(std::string sName) {
	*s_name = sName;
}

void CTable::vSetElement(int n, int val, int* excCode) {
	if (n >= *i_length || n < 0) {
		*excCode = 1;
		return;
	}
	table[n] = val;
}

int CTable::iGetElement(int n, int* excCode) {
	if (n >= *i_length || n < 0) {
		*excCode = 1;
		return -1;
	}
	return table[n];
}

CTable* CTable::clone() {
	CTable* cl = new CTable(*this);
	return cl;
}

std::string CTable::sInfo() {
	std::ostringstream o;
	o << *s_name << "len: " << *i_length << "values: ";
	for (int i = 0; i < *i_length; i++)
		o << table[i] << ", ";
	return o.str();
}