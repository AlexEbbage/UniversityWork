//Written by Alex Ebbage - 1504283
//Uses C++ v11

#include "Punctuation.h"

//Constructor for Punctuation.
//@Param string - the name of the file to be filtered.
Punctuation::Punctuation(string &wordfile): ReadWords(wordfile){};
	
//A function which takes a word and returns true if it contains a single punctuation character.
//@Param string - The word that needs to be filtered.
//@Return bool - Returns true if the word contains 1 punctuation character.
bool Punctuation::filter(string word)
{
	int count = 0;
	
	for(int i = 0; i < word.size(); i++)
	{
		if ( ispunct(word[i]) )
		{
			count++;
		}		
	}

	return(count == 1);
}