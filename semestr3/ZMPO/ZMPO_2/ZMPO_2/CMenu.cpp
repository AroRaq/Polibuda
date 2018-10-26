#include "CMenu.h"

CMenu::CMenu()
{
}

CMenu::CMenu(std::string name, std::string command)
{
	CMenu::name = name;
	CMenu::command = command;
}

void CMenu::Add(CMenuItem* item)
{
	items.push_back(item);
}

void CMenu::Run() {
	std::string input;
	bool running = true;
	do {
		std::cout << name << std::endl;
		for (int i = 0; i < items.size(); i++) {
			std::cout << i << ". " << items[i]->name << " (" << items[i]->command << ")\n";
		}
		std::cin >> input;
		CMenuItem* item = GetItemFromCommand(input);
		if (item != NULL) {
			item->Run();
		}
		else if (input == BACK) {
			running = false;
		}
		else {
			std::cout << NO_SUCH_POSITION;
		}
	} while (running);

}

CMenuItem * CMenu::Get(std::string name)
{
	for (int i = 0; i < items.size(); i++)
		if (command == items[i]->command)
			return items[i];
	return NULL;
}

CMenuItem* CMenu::GetItemFromCommand(std::string command) {
	for (int i = 0; i < items.size(); i++) 
		if (command == items[i]->command)
			return items[i];
	return NULL;
}