#include "ReadWords.h"
#include <fstream>
#include <iostream>
#include <cstdlib>
#include <cctype>
#include <iomanip>
#include <map>

using namespace std;


//Constructor for ReadWords. Throws an error if the file can't be found and
//terminates the program.
ReadWords::ReadWords(const char *filename){	
	wordfile.open(filename);
	if(!wordfile)
	{
		cout << "Error: " << filename << " is not a valid file." << endl;
		exit(1);
	}
}


//Closes the file.
void ReadWords::close(){
	wordfile.close();
}


//Gets the next word from the file, then returns that word.
//@return string - Returns the next word in the file.
string ReadWords::getNextWord(){
	nextword = "";
	wordfile >> nextword;
	return nextword;
}


//Determines if there is more content in the file, returns the result.
//@return bool - Returns !eof.
bool ReadWords::isNextWord(){
	if(!wordfile.eof()){
		eoffound = true;
	}
	else{
		eoffound = false;
	}
	
	return eoffound;
}

