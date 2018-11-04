#include "CMenuItem.h"

void CMenuItem::SetRoot(CMenuItem * root)
{
	this->root = root;
}

std::string CMenuItem::GetPath()
{
	if (root == NULL)
		return "";
	if (root->root == NULL)
		return root->command;
	return root->GetPath() + ARROW + root->command;
}
