#include <iostream>
#include <iomanip>
#include "Time.h"

using namespace std;


//Empty constructor for Time. Sets hour, min and sec to 0.
Time::Time()
{
	hour = min = sec = 0;
}


//Constructor for Time that takes 3 ints as arguments.
//@param h - an int holding the value for hours.
//@param m - an int holding the value for minutes.
//@param s - an int holding the value for seconds.
Time::Time(int h, int m, int s)
{
	setTime(h, m, s);
}


//Sets the time to the desired value, being set to 0 if a value
//lies outside the range of 0 to 24.
//@param h - an int holding the value for hours.
//@param m - an int holding the value for minutes.
//@param s - an int holding the value for seconds.
void Time::setTime(int h, int m, int s)
{
	hour = (h>=0 && h<24) ? h : 0;
	min = (m>=0 && m<60) ? m : 0;
	sec = (s>=0 && s<60) ? s : 0;
}


//Allows the += operator to be used in conjunction with an unsigned int. Adds
//n seconds to Time.
//@param n - an  unsigned int holding the value for seconds to be added.
//@return Time& - a reference to the resulting Time object.
Time& Time::operator+=(unsigned int n)
{
	sec += n;
	if (sec >= 60)
	{
		min += sec/60;
		sec %= 60;
		if (min >=60)
		{
			hour = (hour + min/60) % 24;
			min %= 60;
		}
	}
	return *this;
}


//Allows the + operator to be used in conjunction with an unsigned int. Adds
//n seconds to Time. Uses the += operator in it's body.
//@param n - an  unsigned int holding the value for seconds to be added.
//@return Time - the resulting Time object.
Time Time::operator+(unsigned int n) const
{
	Time tCopy(*this);
	tCopy += n;
	return tCopy;
}


//Increments the Time by 1 second. Uses the += operator in its body.
//@return Time& - a reference to a resulting Time object.
Time& Time::operator++()        // prefix version
{
	*this += 1;
	return *this;
}


//Increments the Time by 1 second. Uses a copy and the += operator in its body.
//@param n - an int used to distinguish between postfix and prefix versions.
//@return Time - the resulting Time object.
Time Time::operator++(int n)    // postfix version
{
	Time tCopy(*this);
	*this += 1;
	return tCopy;
}


//Allows Time to be used in an output stream and formats the output.
//@param &o - a reference to the outstream being used.
//@param &t - a reference to the Time value being used.
//@return ostream& - a reference to an output stream.
ostream& operator<<(ostream &o, const Time &t)
{
	o << setfill('0') << setw(2) <<  t.hour << ':' << setw(2) << t.min << ':' << setw(2) << t.sec;
	return o;
}

//ASSIGNMENT FUNCTIONS

//Allows the -= operator to be used in conjunction with an unsigned int. Subtracts
//n seconds to Time.
//@param n - an  unsigned int holding the value for seconds to be subtracted.
//@return Time& - a reference to the resulting Time object.
Time& Time::operator-=(unsigned int n){
	sec -= n;
	if (sec < 0){
		min += sec/60 - 1;
		sec = 60 + (sec%60);
		if (min < 0){
			hour = (hour -1 + min/60) % 24;
			min = 60 + (min%60);
		}
	}
	return *this;
}


//Allows the - operator to be used in conjunction with an unsigned int. Subtracts
//n seconds to Time. Uses the -= operator in it's body.
//@param n - an  unsigned int holding the value for seconds to be subtracted.
//@return Time - the resulting Time object.
Time Time::operator-(unsigned int n) const{
	Time tCopy(*this);
	tCopy -= n;
	return tCopy;
}


//Decrements the Time by 1 second. Uses the -= operator in its body.
//@return Time& - a reference to the resulting Time object.
Time& Time::operator--(){        // prefix version
	*this -= 1;
	return *this;
}
 

//Decrements the Time by 1 second. Uses a copy and the -= operator in its body. 
//@param n - an int used to distinguish between postfix and prefix versions.
//@return Time - the resulting Time object.
Time Time::operator--(int n){        // postfix version
	Time tCopy(*this);
	*this -= 1;
	return tCopy;
}
 
 
//Determines if this Time is equal to Time given as argument.
//@parm &t - const Time object for comparison.
//@return bool - whether Time objects are equal.
bool Time::operator==(const Time &t) const{
	if((hour == t.hour) && (min == t.min) && (sec == t.sec)){
		return true;
	}
	else{	
		return false;
	}
}
 
 
//Determines if this Time is less than Time given as argument.
//@parm &t - const Time object for comparison.
//@return bool - whether this is less than given &t.
bool Time::operator<(const Time &t) const{
	if((hour <= t.hour) && (min <= t.min) && (sec <= t.sec) && !(*this==t)){
		return true;
	}
	else{
		return false;
	}
}
 
 
//Determines if this Time is greater than Time given as argument.
//@parm &t - const Time object for comparison.
//@return bool - whether this is greater than given &t.
bool Time::operator>(const Time &t) const{
	if((hour >= t.hour) && (min >= t.min) && (sec >= t.sec) && !(*this==t)){
		return true;
	}
	else{
		return false;
	}
}

 
//Determines if this Time is less than Time given as argument.
//@parm &t1 - const Time object for comparison.
//@parm &t2 - const Time object for comparison.
//@return bool - whether &t1 is less than or equal to &t2.
bool operator<=(const Time &t1, const Time &t2){
	if((t1.hour <= t2.hour) && (t1.min <= t2.min) && (t1.sec <= t2.sec)){
		return true;
	}
	else{
		return false;
	}
}

 
//Determines if this Time is greater than Time given as argument.
//@parm &t1 - const Time object for comparison.
//@parm &t2 - const Time object for comparison.
//@return bool - whether &t1 is greater than or equal to &t2.
bool operator>=(const Time &t1, const Time &t2){
	if((t1.hour >= t2.hour) && (t1.min >= t2.min) && (t1.sec >= t2.sec)){
		return true;
	}
	else{
		return false;
	}
}

 
//Determines if this Time is not equal to Time given as argument.
//@parm &t1 - const Time object for comparison.
//@parm &t2 - const Time object for comparison.
//@return bool - whether &t1 is not equal to &t2.
bool operator!=(const Time &t1, const Time &t2){
	if(!(t1.hour == t2.hour) || !(t1.min == t2.min) || !(t1.sec == t2.sec)){
		return true;
	}
	else{	
		return false;
	}
}

 
//Finds the time it takes between 2 Times, the 2nd parameter being the start time.
//@parm &tS - const Time object which is start time.
//@parm &tF - const Time object which is finish time.
//@return unsigned int - time elapsed in seconds between 2 given times.
unsigned int operator-(const Time &tS, const Time &tF){
	int secondsTS = (3600*tS.hour) + (60*tS.min) + (tS.sec);
	int secondsTF = (3600*tF.hour) + (60*tF.min) + (tF.sec);
	unsigned int difference = 0;
	
	if(secondsTF >= secondsTS){
		difference = secondsTF - secondsTS;
	}
	else{
		difference = ((24*60*60) - secondsTS) + secondsTF;
	}
	
	return difference;
}

