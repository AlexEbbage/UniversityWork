//Written by Alex Ebbage - 1504283
//Uses C++ v11

#ifndef _STUDENT_H_
#define _STUDENT_H_

#include <iostream>
#include <iomanip>
#include <string>
#include <map>
#include <vector>
#include <stdexcept>
#include "Person.h"

using namespace std;

class NoMarkException: public exception
{
};

class Student: public Person
{   public:
        // constructor should initialise name and registration number using arguments
        // and initialise marks map to be empty
        Student(const string &name, int regNo);

	    // method to return registration number
	    int getRegNo() const;

	    // method to return registration number
	    string getName() const;
		
	    // method to add the mark to the map
	    // if a mark for the module is already present it should be overwritten
	    void addMark(const string& module, float mark);

	    // method to retrieve the mark for a module
	    // should throw NoMarkException if student has no mark for that module
	    float getMark(const string &module) const throw (NoMarkException);
		
		//Outputs all the module and corresponding marks for the student.
		void outputMarks();
		
		// method to find average mark for Student.
		float getAverageMark() const;

	private:
	    int regNo;
	    map<string, float> marks;  // keys are modules, values are marks in range 0.0 to 100.0

	// friend function to output details of student so stream
	// should output name, regno, and minimum, maximum and average marks on a single line
	// if the student has no marks "no marks" should be output instead of the marks
	friend ostream& operator<<(ostream &str, const Student &s);
};

#endif
