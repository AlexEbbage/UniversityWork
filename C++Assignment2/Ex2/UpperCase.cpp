//Written by Alex Ebbage - 1504283
//Uses C++ v11

#include "UpperCase.h"

//Constructor for UpperCase.
//@Param string - the name of the file to be filtered.
UpperCase::UpperCase(string &wordfile):ReadWords(wordfile){};

// return true if the first letter of a string is a capital letter.
//@Param string - The word that needs to be filtered.
//@Return bool - Returns true if the word starts with an upper-case letter.
bool UpperCase::filter(string word)
{
	return(isupper(word[0]));
}