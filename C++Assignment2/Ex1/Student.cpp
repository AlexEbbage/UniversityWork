//Modified by Alex Ebbage - 1504283
//Uses C++ v11

#include "Student.h"

using namespace std;

//Constructor for Student.
//@Param string - name of the student.
//@Param int - registration number of the student.
Student::Student(const string &name, int regNo):Person::Person(name)
{
	this->name = name;
	this->regNo = regNo;
	//You say to initialise marks map to be empty but surely it is by default?
}


//Returns regNo of Student.
//@Return int - returns the registration number of the student.
int Student::getRegNo() const
{
	return regNo;
}


//Returns name of Student.
//@Return string - returns the name of the student.
string Student::getName() const
{
	return name;
}


//Adds a mark for the given module to the Student's map.
//@Param string - the module code to add a mark for.
//@Param float - the mark attained by the student for that module.
void Student::addMark(const string& module, float mark)
{
	this->marks[module] = mark;
}


//Gets the Student's mark for a given module or throws an exception.
//@Param string - the module code to find the mark for.
//@Return float - the mark attained in that module by the student.
float Student::getMark(const string &module) const throw (NoMarkException)
{
	if(marks.find(module) != marks.end())
	{
		return marks.at( module );
	}
	else
	{
		throw(NoMarkException());
	}		
}


//Outputs all the module names and corresponding marks.
void Student::outputMarks()
{
	for(auto const& entry : marks)
	{
		cout << entry.first << "	" << entry.second << endl;
	}
}


//Gets the average mark attained by a Student.
//@Return float - returns the average mark attained by the student.
float Student::getAverageMark() const
{
	float average = 0;
	int size = 0;
	for(auto const& entry : marks)
	{
		average += entry.second;
		size++;
	}
	
	if(average > 0)
	{
		return average / size;
	}
	else
	{
		return 0;
	}
}


//Outputs min, max and average mark for a Student.
//@Param ostream - output stream for student data.
//@Param Student - Student object to get data for.
//@Return ostream - returns the name, regNo amd min, max and avg marks.
ostream& operator<<(ostream &str, const Student &s)
{
	float minMark = s.marks.begin()->second;
	for(auto const& entry : s.marks)
	{
		if(minMark > entry.second)
		{
			minMark = entry.second;
		}
	}
	
	float maxMark = s.marks.begin()->second;
	for(auto const& entry : s.marks)
	{
		if(maxMark < entry.second)
		{
			maxMark = entry.second;
		}
	}
	
	//If the student has no marks, set min and max marks to 0.
	if(s.marks.size() == 0)
	{
		minMark = 0;
		maxMark = 0;
	}
	
	str << s.name << " - " << s.getRegNo() 
		<< "	MinMark: " << minMark 
		<< "	MaxMark: " << maxMark 
		<< "	AvgMark: " << s.getAverageMark();
					
	return str;
}