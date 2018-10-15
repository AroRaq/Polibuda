#include "CTable.h"
#include <unordered_map>

#define ERR_OUT_OF_BOUNDS "Value out of bounds\n"
#define ERR_SPECIFY_IDX "Specify what table to remove\n"
#define ERR_PARAMS "Wrong number of parameters\n"
#define ERR_WRONG_COMMAND "Command doesn't exist\n"

#define HELP	"list\t\t\t\tLists all created CTables\n" \
				"create\t(NAME)\t(LENGTH)\tCreates a new CTable\n"  \
				"delete\tID\t\t\tDeletes CTable with specified ID\n" \
				"clone\tID\t\t\tCreates copy of CTable with specified ID and adds it to the container\n" \
				"resize\tID\tNEWSIZE\t\tChanges the size of already existing CTable\n" \
				"rename\tID\tNEWNAME\t\tChanges the name of already existing CTable\n" \
				"size\tID\t\t\tDisplays the size of CTable with specified ID\n" \
				"deleteall\t\t\tRemoves all CTables from the container and deletes them\n" \
				"setval\tID\tIDX\tNEWVAL\tSets the value at \"idx\" to NEWVAL in specified CTable\n" \
				"display\tID\t\t\tDisplays the information about specified CTable\n" \
				"quit\t\t\t\tQuit the program\n"

class UserInterface {
public:
	UserInterface();
	~UserInterface();
	void Run();

private:
	bool IsInBounds(int pos);
	std::vector<std::string> ParseInput(std::string input);
	void ListContent();
	void CreateCTable(std::string name = DEFAULT_NAME, int len = DEFAULT_LENGTH);
	void DeleteCTable(int ID);
	void CloneCTable(int ID);
	void ResizeCTable(int ID, int newSize);
	void RenameCTable(int ID, std::string newName);
	void ShowCTableSize(int ID);
	void SetValueCTable(int ID, int idx, int newVal);
	void DisplayCTable(int ID);

	std::vector<CTable*> ctVector;
	int excCode;
	bool running;
};