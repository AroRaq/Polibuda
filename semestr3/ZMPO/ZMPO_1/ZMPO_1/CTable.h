#pragma once

#include <string>
#include <iostream>
#include "Utils.h";

static const int DEFAULT_LENGTH = 10;
static const std::string str = "test";

class CTable {
public:
	CTable();
	CTable(std::string sName, int iTableLen);
	CTable(CTable& other);
	~CTable();

	void vResize(int len, int* excCode);
	void vSetName(std::string name);
	void vSetElement(int n, int val, int* excCode);
	int iGetElement(int n, int* excCode);
	CTable* clone();
	std::string sInfo();
private:
	std::string* s_name;
	int* table;
	int* i_length;
};