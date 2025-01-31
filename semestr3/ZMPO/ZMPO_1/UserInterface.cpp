#include "UserInterface.hpp"


UserInterface::UserInterface() {
	ctVector = std::vector<CTable*>();
}

UserInterface::~UserInterface() {
	for (int i = 0; i < ctVector.size(); i++) {
		delete ctVector[i];
	}
}

void UserInterface::Run() {
	excCode = 0;
	running = true;
	while (running) {
		std::cout << "#";
		std::string input;
		std::getline(std::cin, input);
		std::vector<std::string> args = ParseInput(input);
		if (args.size() == 0) {}
		else if (args[0] == LIST) {
			ListContent();
		}
		else if (args[0] == CREATE) {
			if (args.size() == 1)
				CreateCTable();
			else if (args.size() == 2)
				CreateCTable(args[1]);
			else
				CreateCTable(args[1], atoi(args[2].c_str()));
		}
		else if (args[0] == DELETE) {
			if (args.size() == 1)
				std::cout << ERR_SPECIFY_IDX;
			else
				DeleteCTable(atoi(args[1].c_str()));
		}
		else if (args[0] == CLONE) {
			if (args.size() == 1)
				std::cout << ERR_SPECIFY_IDX;
			else
				CloneCTable(atoi(args[1].c_str()));
		}
		else if (args[0] == RESIZE) {
			if (args.size() < 3)
				std::cout << ERR_PARAMS;
			else {
				ResizeCTable(atoi(args[1].c_str()), atoi(args[2].c_str()));
			}
		}
		else if (args[0] == RENAME) {
			if (args.size() < 3)
				std::cout << ERR_PARAMS;
			else
				RenameCTable(atoi(args[1].c_str()), args[2]);
		}
		else if (args[0] == SIZE) {
			if (args.size() < 2)
				std::cout << ERR_PARAMS;
			else
				ShowCTableSize(atoi(args[1].c_str()));;
		}
		else if (args[0] == DELETEALL) {
			for (int i = 0; i < ctVector.size(); i++)
				delete ctVector[i];
			ctVector.clear();
		}
		else if (args[0] == SETVAL) {
			if (args.size() < 4)
				std::cout << ERR_PARAMS;
			else
				SetValueCTable(atoi(args[1].c_str()), atoi(args[2].c_str()), atoi(args[3].c_str()));
		}
		else if (args[0] == DISPLAY) {
			if (args.size() < 2)
				std::cout << ERR_PARAMS;
			else
				DisplayCTable(atoi(args[1].c_str()));
		}
		else if (args[0] == "sum") {
			if (args.size() < 2)
				std::cout << ERR_PARAMS;
			else {
				int idx = atoi(args[1].c_str());
				if (idx < 0 || idx >= ctVector.size()) 
					std::cout << ERR_OUT_OF_BOUNDS;
				else {
					Sum(*ctVector[idx]);
					Sum(ctVector[idx]);
				}
			}
		}
		else if (args[0] == HELP) {
			std::cout << HELP_MSG;
		}
		else if (args[0] == QUIT) {
			running = false;
		}
		else {
			std::cout << ERR_WRONG_COMMAND;
		}
	}
}

bool UserInterface::IsInBounds(int pos) {
	return pos >= 0 && pos < ctVector.size();
}

std::vector<std::string> UserInterface::ParseInput(std::string input)
{
	std::vector<std::string> args = std::vector<std::string>();
	std::string temp;
	for (int i = 0; i < input.length(); i++) {
		if (input[i] == ' ') {
			if (!temp.empty()) args.push_back(temp);
			temp = "";
		}
		else
			temp += input[i];
	}
	if (!temp.empty())
		args.push_back(temp);

	return args;
}

void UserInterface::ListContent()
{
	std::cout << CONTENT;
	for (int i = 0; i < ctVector.size(); i++) {
		std::cout << i << ". " << ctVector[i]->GetName() << std::endl;
	}
}

void UserInterface::CreateCTable(std::string name, int len)
{
	CTable* newTab;
	if (name == DEFAULT_NAME)
		newTab = new CTable();
	else
		if (len < 0) {
			std::cout << ERR_WRONG_VALUE;
			return;
		}
		else
			newTab = new CTable(name, len);
	ctVector.push_back(newTab);
}

void UserInterface::DeleteCTable(int ID)
{
	if (!IsInBounds(ID))
		std::cout << ERR_OUT_OF_BOUNDS;
	else {
		delete ctVector[ID];
		ctVector.erase(ctVector.begin() + ID);
	}
}

void UserInterface::CloneCTable(int ID)
{
	if (!IsInBounds(ID))
		std::cout << ERR_OUT_OF_BOUNDS;
	else {
		ctVector.push_back(new CTable(*ctVector[ID]));
	}
}

void UserInterface::RenameCTable(int ID, std::string newName)
{
	if (!IsInBounds(ID))
		std::cout << ERR_OUT_OF_BOUNDS;
	else {
		ctVector[ID]->SetName(newName);
	}

}

void UserInterface::ShowCTableSize(int ID)
{
	if (!IsInBounds(ID))
		std::cout << ERR_OUT_OF_BOUNDS;
	else {
		std::cout << ctVector[ID]->GetSize() << std::endl;
	}

}

void UserInterface::SetValueCTable(int ID, int idx, int newVal)
{
	if (!IsInBounds(ID))
		std::cout << ERR_OUT_OF_BOUNDS;
	else {
		ctVector[ID]->SetElement(idx, newVal, &excCode);
		if (excCode == 1) {
			std::cout << ERR_OUT_OF_BOUNDS;
			excCode = 0;
		}
	}
}

void UserInterface::DisplayCTable(int ID)
{
	if (!IsInBounds(ID))
		std::cout << ERR_OUT_OF_BOUNDS;
	else {
		std::cout << ctVector[ID]->toString();
	}

}

void UserInterface::ResizeCTable(int ID, int newSize)
{
	if (!IsInBounds(ID))
		std::cout << ERR_OUT_OF_BOUNDS;
	else {
		ctVector[ID]->SetSize(newSize, &excCode);
		if (excCode == 2) {
			std::cout << ERR_WRONG_LEN;
			excCode = 0;
		}
	}

}


void UserInterface::Sum(CTable tab) {
	std::cout << "Sum (object): " << tab.GetSum() << '\n';
}

void UserInterface::Sum(CTable* ptrTab) {
	std::cout << "Sum (pointer): " << ptrTab->GetSum() << '\n';
}