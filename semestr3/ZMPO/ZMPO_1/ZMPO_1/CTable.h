#pragma once

#include <string>
#include <iostream>
#include <sstream>

#define DEFAULT_LENGTH 10
#define DEFAULT_NAME "new_CTable"
#define BEZP "bezp: "
#define PARAMETR "parametr: "
#define COPY "_copy"
#define KOPIUJ "kopiuj: "
#define USUWAM "usuwam "
#define NAME "name: "
#define LEN "len: "
#define VALUES "values: "

class CTable {
public:
	CTable();
	CTable(std::string tableName, int tableLen = DEFAULT_LENGTH);
	CTable(const CTable& other);
	~CTable();

	void SetSize(int len, int* excCode = NULL);
	void SetName(std::string name);
	void SetElement(int idx, int val, int* excCode = NULL);
	int GetElement(int idx, int* excCode = NULL);
	int GetSize();
	CTable* getClone();
	std::string toString();
	std::string GetName();
	void AssignValues(const CTable& other);
private:
	bool IsInBounds(int idx);

	std::string name;
	int* table;
	int length;
};