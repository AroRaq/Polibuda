#pragma once

class Value {
public:
	Value();
	Value(const Value& other);
	~Value();
	bool Equals(Value* other);
	void SetValue(void* value, bool owner);
private:
	void DisposeValue();
	void* value;
	bool owner;
};