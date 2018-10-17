#include "CTable.h"
#include <unordered_map>

#define ERR_OUT_OF_BOUNDS "Value out of bounds\n"
#define ERR_SPECIFY_IDX "Specify what table to remove\n"
#define ERR_PARAMS "Wrong number of parameters\n"
#define ERR_WRONG_COMMAND "Command doesn't exist\n"
#define ERR_WRONG_VALUE "Wrong value\n"

#define HELP	"list				Lists all created CTables\n" \
				"create	(NAME)	(LENGTH)	Creates a new CTable\n"  \
				"delete	ID			Deletes CTable with specified ID\n" \
				"clone	ID			Creates copy of CTable with specified ID and adds it to the container\n" \
				"resize	ID	NEWSIZE		Changes the size of already existing CTable\n" \
				"rename	ID	NEWNAME		Changes the name of already existing CTable\n" \
				"size	ID			Displays the size of CTable with specified ID\n" \
				"deleteall			Removes all CTables from the container and deletes them\n" \
				"setval	ID	IDX	NEWVAL	Sets the value at IDX to NEWVAL in specified CTable\n" \
				"display	ID			Displays the information about specified CTable\n" \
				"quit				Quit the program\n"

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