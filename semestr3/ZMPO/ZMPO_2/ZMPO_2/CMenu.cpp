#include "CMenu.h"

CMenu::~CMenu() {
	for (int i = 0; i < items.size(); i++) {
		delete items[i];
	}
}

CMenu::CMenu(std::string name, std::string command)
{
	CMenu::name = name;
	CMenu::command = command;
}

void CMenu::Add(CMenuItem* item)
{
	for (int i = 0; i < items.size(); i++) {
		if (items[i]->command == item->command) {
			std::cout << ALREADY_EXISTS << item->command << std::endl;
		}
	}
	items.push_back(item);
}

void CMenu::Add(std::string path, CMenuItem* item) {
	CMenu* target = this;
	bool finished = false;
	do {
		std::size_t found = path.find("->");
		if (found == std::string::npos) {
			target = (CMenu*)(target->GetItemByCommand(path));
			if (target == NULL) {
				std::cout << NO_SUCH_PATH;
				return;
			}
			target->Add(item);
			finished = true;
		}
		else {
			target = (CMenu*)(target->GetItemByCommand(path.substr(0, found)));
			if (target == NULL) {
				std::cout << NO_SUCH_PATH;
				return;
			}
			path = path.substr(found + 2);
		}
	} while (!finished);
}

void CMenu::Remove(std::string name) {
	for (int i = 0; i < items.size(); i++) {
		if (items[i]->name == name) {
			items.erase(items.begin() + i);
			std::cout << REMOVED << name << std::endl;
			return;
		}
	}
	std::cout << NO_SUCH_POSITION;
}

void CMenu::Run() {
	std::string input;
	bool running = true;
	do {
		std::cout << std::endl << name << std::endl;
		for (int i = 0; i < items.size(); i++) {
			std::cout << i << ". " << items[i]->name << " (" << items[i]->command << ")" << std::endl;
		}
		std::cout << '#';
		std::cin >> input;
		CMenuItem* item = GetItemByCommand(input);
		if (item == this) {
			std::cout << SAME_OBJECT;
		}
		else if (item != NULL) {
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

CMenuItem* CMenu::GetItemByName(std::string name)
{
	for (int i = 0; i < items.size(); i++)
		if (name == items[i]->name)
			return items[i];
	return NULL;
}

CMenuItem* CMenu::GetItemByCommand(std::string command) {
	for (int i = 0; i < items.size(); i++) 
		if (command == items[i]->command)
			return items[i];
	return NULL;
}