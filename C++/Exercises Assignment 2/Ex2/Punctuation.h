//Written by Alex Ebbage - 1504283
//Uses C++ v11

#ifndef _PUNCTUATION_H
#define _PUNCTUATION_H

#include "ReadWords.h"

class Punctuation : public ReadWords
{
	public:
		//Constructor for Punctuation.
		Punctuation(string &fileName);
		
		//Filter function for Punctuation.
		bool filter(string word);
};

#endif