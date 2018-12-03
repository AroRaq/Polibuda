#include "pch.h"
#include "Value.h"

Value::Value()
{
	value = nullptr;
	owner = false;
}

Value::Value(const Value& other)
{
	value = other.value;
	owner = false;
}

Value::~Value()
{
	DisposeValue();
}

bool Value::Equals(Value* other)
{
	if (other == nullptr)
		return false;
	return value == other->value;
}

void Value::SetValue(void* value, bool owner)
{
	DisposeValue();
	this->value = value;
	this->owner = owner;
}

void Value::DisposeValue() {
	if (owner)
		delete value;
}
