#ifndef OUTPUT_H
#define OUTPUT_H

#include <iostream>
#include <fstream>
#include <map>


//Output class. Provides mechanisms to format and output contents of a <string, int> map.
using namespace std;

typedef map<string, int> wordMap;

class Output{
	
    public:
	
		 // Constructor. Opens the file with the given filename and updates the map value.
		 // Prints an error message then terminates the program if file does not exist.
		 // @param map - a map<string, int> that contains the words and their occurence count.
		 // @param filename - a C string naming the file to read.
        Output(wordMap map, const char *filename);
		
		//Outputs the contents of the map to a file and the console.
        void output();
		
		//Closes the ofstream out.
        void close();
		
		
	private:
	
		//The output file stream and map with the word count.
        ofstream out;
		wordMap map;

};

#endif