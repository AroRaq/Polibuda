#include "CMenuItem.h"

void CMenuItem::SetParent(CMenuItem * parent)
{
	this->parent = parent;
}

std::string CMenuItem::GetPath()
{
	if (parent == NULL)
		return "";
	if (parent->parent == NULL)
		return parent->command;
	return parent->GetPath() + ARROW + parent->command;
}
