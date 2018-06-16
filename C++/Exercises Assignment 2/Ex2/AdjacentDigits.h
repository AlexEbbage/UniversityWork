//Written by Alex Ebbage - 1504283
//Uses C++ v11

#ifndef _ADJACENTDIGITS_H
#define _ADJACENTDIGITS_H

#include "ReadWords.h"

class AdjacentDigits : public ReadWords
{
	public:
		//Constructor for AdjacentDigits.
		AdjacentDigits(string &fileName);
		
		//Filter function for AdjacentDigits.
		bool filter(string word);
};

#endif