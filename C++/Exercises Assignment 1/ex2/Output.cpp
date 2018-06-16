#include "Output.h"
#include <stdlib.h>
#include <iostream>
#include <iomanip>
#include <string>
#include <map>
#include <iterator>

using namespace std;

typedef map<string, int> wordMap;


//Constructor for Output. Opens the output file and updates the map.
//Prints an error and terminates the program if the file can't be created.
 // @param map - a map<string, int> that contains the words and their occurence count.
 // @param filename - a C string naming the file to read.
Output::Output(wordMap m, const char *filename)
{
	out.open(filename);
    if (!out)
	{
		cout << "Can't create " << filename << endl;
        exit(1);
    }
	
	map = m;
}


//Outputs the contents of the map containing the words and their occurence count.
//Use the largest value divided by 10 to find a scale. Then produces a bar chart,
//where if there is at least 1 occurence then an asterix will appear.
//Output is to the console and the chosen file.
void Output::output()
{
	int largestKey = 0;
	int largestValue = 0;
	int scale = 0;
	int barSize = 0;
	
	wordMap::iterator it;
	
	//Iterates through the map entries to find the length of the largest key, the
	//largest occurence count and then the scale.
	for(it = map.begin(); it != map.end(); it++ )
	{
		if(it->first.length() > largestKey)
		{
			largestKey = it->first.length();
		}
		if(it->second > largestValue)
		{
			largestValue = it->second;
		}
	}
	
	scale = largestValue/10 + 1;
	
	//Iterates through the map entries to produce the outputs.
	for(it = map.begin(); it != map.end(); it++ )
	{
		
		//Console and file output.
		cout << setw(largestKey) << it->first << " ";
		out << setw(largestKey) << it->first << " ";
		barSize = it->second/scale;
		if((it->second)%scale != 0){
			barSize++;
		}
		for(int i=0; i<barSize; i++)
		{
			cout << "*";
			out << "*";
		}
		cout << " " << it->second << endl;
		out << " " << it->second << endl;
	}
	
	//Output for scale system.
	cout << endl << "Scale: * = " << scale << endl;
	out << endl << "Scale: * = " << scale << endl;
}

//Closes the ofstream out.
void Output::close(){
	out.close();
}