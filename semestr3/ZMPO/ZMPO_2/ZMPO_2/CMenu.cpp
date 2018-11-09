#include "CMenu.h"

CMenu::CMenu(std::string name, std::string command)
{
	this->name = name;
	this->command = command;
	root = NULL;
}

CMenu::CMenu(std::string& serialized, size_t& index)
{
	//Begin reading
	if (serialized[index++] != '(') {
		std::cout << "Expected ( at " << index - 1 << std::endl;
		return;
	}
	//Read Name
	if (serialized[index++] != '\'') {
		std::cout << "Expected name declaration at " << index - 1 << std::endl;
		return;
	}
	size_t len = serialized.find(index, '\'') - index;
	if (len == std::string::npos) {
		std::cout << "Expected end of name declaration after " << index << std::endl;
		return;
	}
	//check if , 
	name = serialized.substr(index + 1, len - 1);
	index += len + 1;
	if (serialized[index++] != ',') {
		std::cout << "Expected , at " << index - 1 << std::endl;
		return;
	}
	//read command
	if (serialized[index++] != '\'') {
		std::cout << "Expected command declaration at " << index - 1 << std::endl;
		return;
	}
	len = serialized.find(index, '\'') - index;
	if (len == std::string::npos) {
		std::cout << "Expected end of command declaration after " << index << std::endl;
		return;
	}
	command = serialized.substr(index + 1, len - 1);
	index += len + 1;
	//check if , 
	name = serialized.substr(index + 1, len - 1);
	index += len + 1;
	if (serialized[index++] != ',') {
		std::cout << "Expected , at " << index - 1 << std::endl;
		return;
	}
	while (serialized[index] != ')') {
		if (serialized[index] == '(') {
			Add(new CMenu(serialized, index));
		}
		else if (serialized[index] == '[') {
			Add(new CMenuCommand(serialized, index));
		}
		else {
			std::cout << "Expected new item declaration ( '(' or '[' ) or end of item ')' at " << index << std::endl;
		}
	}
}

CMenu::~CMenu() {
	for (int i = 0; i < items.size(); i++) {
		delete items[i];
	}
}

void CMenu::Add(CMenuItem* item)
{
	for (int i = 0; i < items.size(); i++) {
		if (items[i]->command == item->command) {
			std::cout << ALREADY_EXISTS << item->command << std::endl;
		}
	}
	items.push_back(item);
	item->SetRoot(this);
}

void CMenu::Add(std::string path, CMenuItem* item) {
	std::string wholePath = path;
	CMenu* target = this;
	bool finished = false;
	do {
		std::size_t found = path.find(ARROW);
		if (found == std::string::npos) {
			target = (CMenu*)(target->GetItemByCommand(path));
			if (target == NULL) {
				std::cout << NO_SUCH_PATH << wholePath << std::endl;
				return;
			}
			target->Add(item);
			finished = true;
		}
		else {
			target = (CMenu*)(target->GetItemByCommand(path.substr(0, found)));
			if (target == NULL) {
				std::cout << NO_SUCH_PATH << wholePath << std::endl;
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

int CMenu::Run() {
	std::string input;
	bool running = true;
	do {
		std::cout << std::endl << name << std::endl;
		for (int i = 0; i < items.size(); i++) {
			std::cout << i << TERMINATOR << items[i]->name << LEFT_BRACKET << items[i]->command << RIGHT_BRACKET << std::endl;
		}
		std::cout << '#';
		std::cin >> input;
		CMenuItem* item = GetItemByCommand(input);
		if (item == this) {
			std::cout << SAME_OBJECT;
		}
		else if (item != NULL) {
			if (item->Run() == 1) {
				running = false;
				return 1;
			}
		}
		else if (input == BACK) {
			running = false;
		}
		else if (input == EXIT) {
			running = false;
			return 1;
		}
		else if (input == HELP) {
			std::cin >> input;
			CMenuItem* item = GetItemByCommand(input);
			if (item != NULL)
				item->ShowHelp();
			else
				std::cout << NO_COMMAND;
		}
		else if (input == SEARCH) {
			std::cin >> input;
			Search(input);
		}
		else {
			std::cout << NO_SUCH_POSITION;
		}
	} while (running);
	return 0;
}

void CMenu::ShowHelp() {
	std::cout << NO_HELP;
}

void CMenu::Search(std::string command)
{
	if (root != NULL) {
		((CMenu*)root)->Search(command);
		return;
	}
	bool found = false;
	std::queue<CMenuItem*> que;
	que.push(this);
	while (!que.empty()) {
		if (CMenu* d = dynamic_cast<CMenu*>(que.front())) {
			for (int i = 0; i < d->items.size(); i++) {
				que.push(d->items[i]);
			}
		}
		if (que.front()->command == command) {
			std::cout << que.front()->GetPath() << std::endl;
			found = true;
		}
		que.pop();	
	}
	if (!found)
		std::cout << NO_COMMAND;
}

std::string CMenu::ToString()
{
	std::string ret = "('" + name + "','" + command + "';";
	if (items.size() > 0) {
		for (int i = 0; i < items.size() - 1; i++) {
			ret += items[i]->ToString() + ",";
		}
		ret += items[items.size() - 1]->ToString();
	}
	ret += ")";
	return ret;
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