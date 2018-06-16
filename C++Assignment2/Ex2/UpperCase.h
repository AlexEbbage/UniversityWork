//Written by Alex Ebbage - 1504283
//Uses C++ v11

#ifndef _UPPERCASE_H
#define _UPPERCASE_H

#include "ReadWords.h"

class UpperCase : public ReadWords
{
	public:
		//Constructor for UpperCase.
		UpperCase(string &fileName);
		
		//Filter function for UpperCase.
		bool filter(string word);
};

#endif