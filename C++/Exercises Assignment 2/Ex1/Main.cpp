//Written by Alex Ebbage - 1504283
//Uses C++ v11

#include <fstream>
#include <algorithm>
#include "Student.h"

using namespace std;

//Outputs all Student data for Students who's average mark is greater or equal to the specified float.
void outputStudentData(vector<Student> studentList, float mark)
{
	//Finds students who have an average mark greater than or equal to the one specified.
	//Then adds them to a vector which is sorted in order of regNo afterwards.
	vector<Student> sortedList;
	for(int i = 0; i < studentList.size(); i++)
	{
			if(studentList[i].getAverageMark() >= mark)
			{
				sortedList.push_back(studentList[i]);
			}
	}
	sort(sortedList.begin(), sortedList.end(), [](const Student& a, const Student& b){return a.getRegNo() > b.getRegNo();});
	
	cout << endl << "Students with an average mark equal to or greater than '" << mark << "' are:" << endl;
	
	//Outputs the results or reports that no students were found.
	if(sortedList.size() > 0)
	{
		for(int i = 0; i < sortedList.size(); i++)
		{
			cout << sortedList[i] << endl;
		}
	}
	else
	{
		cout << "No students got an average mark greater than or equal to " << mark << "." << endl;
	}
}


//Outputs all Student data for Student's who have a mark for the module specified.
void outputModuleData(vector<Student> studentList, string module)
{
	bool isStudentFound = false;
	
	cout << endl << "Marks for module '" << module << "' are as follows:" << endl;
	
	//Trys to get mark each student got for specified module, throwing a NoMarkException if failing.
	for(int i = 0; i < studentList.size(); i++)
	{
		try
		{
			cout << studentList[i].getName() << "	" << studentList[i].getMark(module) <<	endl;
			isStudentFound = true;
		}
		catch(NoMarkException e){}
	}
	
	//Reports that no student marks could be found for the given module.
	if(!isStudentFound)
	{
		cout << "No students marks found for module: " << module << endl;
	}
}


int main()
{   
	//All the variables used.
	vector<Student> studentList;
	string input;
	ifstream file;
	string forename;
	string surname;
	string name;
	int regNo;
	float mark;
	string module;
	bool isLoopRunning = true;
	float inputMark;
	string inputModule;
	
	//Gets the file containing the student details, specified by the user.
	cout << endl << "Enter the name of the file containing the students:" << endl;
	cin >> input;
	const char* studentFile = input.c_str();
	file.open(studentFile);
	
	//Iterates through the file creating Student objects and adding them to a vector.
	while(file >> regNo >> forename >> surname)
	{
		name = forename + " " + surname;
		studentList.push_back(Student(name, regNo));
	}
	file.close();
	
	
	//Gets the file containing the module details, specified by the user.
	cout << endl << "Enter the name of the file containing the module data:" << endl;
	cin >> input;
	const char* moduleFile = input.c_str();
	file.open(moduleFile);
	
	//Iterates through the file add modules-mark entries to the corresponding students marks map.
	while(file >> regNo >> module >> mark)
	{
		bool isStudentFound = false;
		
		for(int i = 0; i < studentList.size(); i++)
		{
			if(regNo == studentList.at(i).getRegNo())
			{
				studentList.at(i).addMark(module, mark);
				isStudentFound = true;
			}
		}
		
		//Reports if no student was found matching the given regNo.
		if(!isStudentFound)
		{
			cout << "No student with registration number '"<< regNo << "' was found." << endl;
		}
	}
	file.close();
	
	
	//Outputs all students and their marks.
	cout << endl;
	for(Student student : studentList)
	{
		cout << student.getName() << " - " << student.getRegNo() << endl;
		student.outputMarks();
		cout << endl;
	}
	
	
	//Loop for user input
	while(isLoopRunning)
	{
		cout << endl << "Please enter one of the numbers to select an option:" << endl
			 << "1 - Student Data Function" << endl
			 << "2 - Module Data Function" << endl
			 << "3 - Close the Program" << endl;
			
		cout << endl << "Choice: ";
		cin >> input;
		
		//Returns student data if their average mark is matching or greater than the given mark.
		if(input == "1")
		{
			cout << endl << "Enter a mark for the lower boundary: " << endl;
			
			cin >> inputMark;
			
			outputStudentData(studentList, inputMark);
		}
		
		//Returns module data, showing the names and marks of students who took the given module.
		else if(input == "2")
		{
			cout << endl << "Enter a module code to search for marks: " << endl;
			
			cin >> inputModule;
			
			outputModuleData(studentList, inputModule);
		}
		
		//Closes the program.
		else if(input == "3")
		{
			isLoopRunning = false;
		}
		
		//Reports that the input was invalid.
		else
		{
			cout << endl << "Invalid input, please try again." << endl;
		}
	}
	
}