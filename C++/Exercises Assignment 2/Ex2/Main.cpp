//Written by Alex Ebbage - 1504283
//Uses C++ v11

#include "UpperCase.h"
#include "AdjacentDigits.h"
#include "Punctuation.h"
#include <vector>

int main()
{
	//Variables used
	string input;
	string userInput;
	map<string, int> entries;
	int largestOccurence;
	vector<string> largestEntries;
	int smallestOccurence;
	vector<string> smallestEntries;
	
	
	//Input allowing the user to choose text to filter.
	cout << "Enter the name of the file containing the text:" << endl;
	//cin >> input;
	input = "hamlet.txt";

	//Input allowing the user to choose which filter to use.
	cout << endl << "Please enter one of the numbers to select a filter:" << endl
		 << "1 - Upper-Case" << endl
		 << "2 - Adjacent Digits" << endl
		 << "3 - Punctuation" << endl;		
	cout << endl << "Choice: ";
	cin >> userInput;

	
	//First choice searches the text using the upper-case filter.
	if(userInput == "1")
	{
		UpperCase upperCase(input);
		while(upperCase.isNextWord())
		{
			string nextWord = upperCase.getNextFilteredWord();
			if(!nextWord.empty())
			{
				entries[nextWord]++;
			}
		}
	}
	
	//Second choice searches the text using the adjacent digits filter.
	else if(userInput == "2")
	{
		AdjacentDigits adjacentDigits(input);
		while(adjacentDigits.isNextWord())
		{
			string nextWord = adjacentDigits.getNextFilteredWord();
			if(!nextWord.empty())
			{
				entries[nextWord]++;
			}
		}	
	}
	
	//Third choice searches the text using the single punctuation filter.
	else if(userInput == "3")
	{
		Punctuation punctuation(input);
		while(punctuation.isNextWord())
		{
			string nextWord = punctuation.getNextFilteredWord();
			if(!nextWord.empty())
			{
				entries[nextWord]++;
			}
		}
	}
	
	//Otherwise the input is reported as invalid.
	else{
		cout << endl << endl << "'" << userInput << "' is an invalid option." << endl;		
	}

	
	//Finds the smallest and largest occurence quantities.
	smallestOccurence = entries.begin()->second;
	for(auto const& entry : entries)
	{
		int entryValue = entry.second;
		
		if(largestOccurence < entryValue)
		{
			largestOccurence = entryValue;
		}
		
		if(smallestOccurence > entryValue)
		{
			smallestOccurence = entryValue;
		}

	}
	
	//Finds the entries with values equal to the largest occurence and smallest occurence.
	//Then adds them to new maps.
	for(auto const& entry : entries)
	{
		if(entry.second == smallestOccurence)
		{
			smallestEntries.push_back(entry.first);
		}
		
		if(entry.second == largestOccurence)
		{
			largestEntries.push_back(entry.first);
		}
	}
	
	
	//Number of entries in map
	cout << endl << "TOTAL ENTRIES" << endl
		 << "There are '" << entries.size() << "' entries in the map." << endl;
	
	
	//Words with the smallest occurences and their occurence count.
	//When there's one word with the smallest occurence.
	if(smallestEntries.size() == 1)
	{	
		cout << endl << "SMALLEST OCCURENCE" << endl
			 << "There is '1' word with '" << smallestOccurence << "' occurences:" << endl;

			cout << smallestEntries.at(0) << endl;
	}
	//When there's 2-10 words with the smallest occurence.
	else if(smallestEntries.size() <= 10)
	{
		cout << endl << "SMALLEST OCCURENCES" << endl
			 << "There are '" << smallestEntries.size() << "' words with '" << smallestOccurence << "' occurences:" << endl;
		
		for(string word : largestEntries)
		{
			cout << word << "	";
		}
	}
	//When there's 11+ words with the smallest occurence.
	else if(smallestEntries.size() > 10)
	{
		cout << endl << "SMALLEST OCCURENCES" << endl
			 << "There are '" << smallestEntries.size() << "' words with '" << smallestOccurence << "' occurences." << endl
			 << "Here are the first 10 words:" << endl;
		
		for(int i = 0; i < 10; i++)
		{
			cout << smallestEntries.at(i) << endl;
		}
		
		cout << "There are '" << smallestEntries.size()-10 << "' more words." << endl;
	}
	
	
	//Words with the largest occurences and their occurence count.
	//When there's one word with the largest occurence.
	if(largestEntries.size() == 1)
	{	
		cout << endl << "LARGEST OCCURENCE" << endl
			 << "There is '1' word with '" << largestOccurence << "' occurences:" << endl;

			cout << largestEntries.at(0) << endl;
	}
	//When there's 2-10 words with the largest occurence.
	else if(largestEntries.size() <= 10)
	{
		cout << endl << "LARGEST OCCURENCES" << endl
			 << "There are '" << largestEntries.size() << "' words with '" << largestOccurence << "' occurences:" << endl;
		
		for(string word : largestEntries)
		{
			cout << word << "	";
		}
	}
	//When there's 11+ words with the largest occurence.
	else if(largestEntries.size() > 10)
	{
		cout << endl << "SMALLEST OCCURENCES" << endl
			 << "There are '" << largestEntries.size() << "' words with '" << smallestOccurence << "' occurences." << endl
			 << "Here are the first 10 words:" << endl;
		
		for(int i = 0; i < 10; i++)
		{
			cout << largestEntries.at(i) << endl;
		}
		
		cout << "There are '" << largestEntries.size()-10 << "' more words." << endl;
	}

}