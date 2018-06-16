#ifndef _PERSON_H_
#define _PERSON_H_

#include <string>

using namespace std;

class Person
{ 
  public:
        Person(const string &name)
        {   this->name = name;
	    }

	    string getName() const
	    {   return name;
	    }

	    void changeName(const string &newName)
	    {   name = newName;
	    }

	protected:
	    string name;
};

#endif
