#include "CMenu.h"

CMenu::CMenu(std::string name, std::string command)
{
	this->name = name;
	this->command = command;
	parent = NULL;
}
/*
CMenu::CMenu(std::string& serialized, size_t& index, errorCode* errCode)
{
	if (serialized[index] != LEFT_BRACKET) {
		*errCode = EXPECTED_BRACKET_LEFT;
		return;
	}

	name = Utils::ReadFromQuotes(serialized, ++index, errCode);
	if (*errCode != NO_ERROR)
		return;

	if (serialized[index] != COMMA) {
		*errCode = EXPECTED_COMMA;	
		return;
	}

	command = Utils::ReadFromQuotes(serialized, ++index, errCode);
	if (*errCode != NO_ERROR)
		return;

	if (serialized[index] != SEMICOLON) {
		*errCode = EXPECTED_SEMICOLON;
		return;
	}
	index++;
	while (serialized[index] != RIGHT_BRACKET) {
		if (serialized[index] == LEFT_BRACKET) {
			Add(new CMenu(serialized, index, errCode));
		}
		else if (serialized[index] == LEFT_CRACKET_SQ) {
			Add(new CMenuCommand(serialized, index, errCode));
		}
		else if (serialized[index] == COMMA)
			index++;
		else {
			*errCode = EXPECTED_BRACKET_RIGHT;
		}
		if (*errCode != NO_ERROR)
			return;
	}
	index++;
}

*/
CMenu::CMenu(std::string& serialized, size_t& index, errorCode* errCode)
{
	if (serialized[index] != LEFT_BRACKET) {
		*errCode = EXPECTED_BRACKET_LEFT;
		return;
	}

	name = Utils::ReadFromQuotes(serialized, ++index, errCode);
	if (*errCode != NO_ERROR)
		return;

	if (serialized[index] != COMMA) {
		*errCode = EXPECTED_COMMA;
		return;
	}

	command = Utils::ReadFromQuotes(serialized, ++index, errCode);
	if (*errCode != NO_ERROR)
		return;

	if (serialized[index] != SEMICOLON) {
		*errCode = EXPECTED_SEMICOLON;
		return;
	}
	index++;
	while (serialized[index] != RIGHT_BRACKET) {
		if (serialized[index] == LEFT_BRACKET) {
			CMenuItem* child = new CMenu(serialized, index, errCode);
			Add(child);
			if (serialized[index] != COMMA) {
				*errCode = EXPECTED_COMMA;
				return;
			}
			if (Utils::ReadFromQuotes(serialized, ++index, errCode) != child->command) {
				*errCode = DIFFERENT_COMMAND;
				return;
			}
		}
		else if (serialized[index] == LEFT_CRACKET_SQ) {
			CMenuItem* child = new CMenuCommand(serialized, index, errCode);
			Add(child);
			if (serialized[index] != COMMA) {
				*errCode = EXPECTED_COMMA;
				return;
			}
			if (Utils::ReadFromQuotes(serialized, ++index, errCode) != child->command) {
				*errCode = DIFFERENT_COMMAND;
				return;
			}
		}
		else if (serialized[index] == COMMA)
			index++;
		else {
			*errCode = EXPECTED_BRACKET_RIGHT;
		}
		if (*errCode != NO_ERROR)
			return;
	}
	index++;
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
	item->SetParent(this);
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
			std::cout << i << TERMINATOR << items[i]->name << SPACE_LEFT_BRACKET << items[i]->command << RIGHT_BRACKET << std::endl;
		}
		std::cout << HASH;
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
	if (parent != NULL) {
		((CMenu*)parent)->Search(command);
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
	std::ostringstream o;
	o << LEFT_BRACKET << 
		QUOTE_MARK << name << QUOTE_MARK << COMMA <<
		QUOTE_MARK << command << QUOTE_MARK << SEMICOLON;
	if (items.size() > 0) {
		for (int i = 0; i < items.size() - 1; i++) {
			o << items[i]->ToString() << COMMA;
		}
		o << items[items.size() - 1]->ToString();
	}
	o << RIGHT_BRACKET;
	return o.str();
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