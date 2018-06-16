//Written by Alex Ebbage - 1504283
//Uses C++ v11

#include "AdjacentDigits.h"

//Constructor for AdjacentDigits.
//@Param string - the name of the file to be filtered.
AdjacentDigits::AdjacentDigits(string &wordfile):ReadWords(wordfile){};
	
//A function which takes go through character by character, checking if the current and adjacent characters
//are digits. If so, it returns true.
//@Param string - The word that needs to be filtered.
//@Return bool - Returns true if the word has 2 adjacent digits in.
bool AdjacentDigits::filter(string word)
{
	for (int i = 0; i < word.size(); i++)
	{
		if((isdigit(word[i])) && (isdigit(word[i+1])))
		{
			return true;
			break;
		}
	} 

	return false;
}
