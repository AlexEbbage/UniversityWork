#include "ReadWords.h"
#include "Output.h"
#include <iostream>
#include <string>

using namespace std;

typedef map<string, int> wordMap;


//Takes the next word from the file as an input, then strips the punctuation from the front and
//end of the word, leaving punctuation in the center of the word. Then converts the remaining
//characters to lower case. The resulting string is returned.
//@param word - a std:string containing the next 'word' in the file.
//@return string - The formatted string for use in the exercise.
string fixWord(string word)
{
	string output = word;
	int length = output.length();	
	
	//Removes punctuation from the end.
	while((length > 0) && ((ispunct(output.at(length-1)))))
	{
		output.erase(length-1, length);
		length--;
	}
	
	//Removes punctuation from the beginning.
	while((length > 0) && (ispunct(output.at(0))))
	{
		output.erase(0,1);
		length--;
	}
	
	//Converts remaining characters to lower case.
	for(int i = 0; i < length; i++)
	{
		output[i] = tolower(output[i]);
	}
	
	return output;
}


//The main function, contains the IO for the files.
int main(){
	string input;
	wordMap wordCount;
	
	//Gets the file containing the search words, specified by the user.
	//cout << endl << "Enter the name of the file containing the search words:" << endl;
	//cin >> input;
	//const char* searchWordFile = input.c_str();
	//ReadWords searchWords(searchWordFile);
	ReadWords searchWords("testwords.txt");
	
	//Searches through the searchWord file and creates an entry for each word,
	//where the key is the word.
	while(searchWords.isNextWord())
	{
		string nextWord = searchWords.getNextWord();
		if(nextWord.length() > 0)
		{
			wordCount[nextWord] = 0;
		}
	}
	
	//Gets the file containing the script, specified by the user.
	//cout << endl << "Enter the name of the file containing the script:" << endl;
	//cin >> input;
	//const char* scriptFile = input.c_str();
	//ReadWords script(searchWordFile);
	ReadWords script("hamlet.txt");
	
	//Searches through the scriptFile word by word.
	while(script.isNextWord())
	{
		string nextWord = script.getNextWord();
		if(nextWord.length() > 0)
		{
			//Reformats each word, then checks if the word is a searchWord.
			//Incrementing the entry value for that word if it matches.
			nextWord = fixWord(nextWord);
			if(wordCount.find( nextWord ) != wordCount.end())
			{
				wordCount[nextWord]++;
			}
		}
	}
	
	//Gets the name of the file for outputing the results from the user.
	//cout << endl << "Enter the name of the file you want to output to:" << endl;
	//cin >> input;
	//const char* outputFile = input.c_str();
	//Output output(wordCount, outputFile);
	Output output(wordCount, "Output.txt");
	
	//Then prints the output to the file and the console, then closes streams.
	cout << endl;
	output.output();
	searchWords.close();
	script.close();
	output.close();
	
	
}