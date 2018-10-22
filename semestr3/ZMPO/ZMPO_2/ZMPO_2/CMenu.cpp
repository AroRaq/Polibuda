#include "CMenu.h"

void CMenu::Run() {
	std::string input;
	bool running = true;
	do {
		std::cout << name;
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


CMenuItem* CMenu::GetItemFromCommand(std::string command) {
	for (int i = 0; i < items.size(); i++) 
		if (command == items[i]->command)
			return items[i];
	return NULL;
}