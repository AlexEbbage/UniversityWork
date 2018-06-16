# ex1.py
# CE151 Assignment 2
# Created by Alex Ebbage 30/11/15
#======================================================================================================

# Takes a string input and returns it as a tuple.
def tupleConversion(inputString):
    inputSplit = inputString.split(None,10)
    inputTuple = tuple(inputSplit)
    return inputTuple

#======================================================================================================

# Takes a tuple input, then returns a formatted line.
def tuplePrint(tupleInput):
    #Variables.
    tupleLength = len(tupleInput)
    counter = 3
    tuplePayroll = tupleInput[0]
    tupleJob = tupleInput[2]
    tupleSalary = "£" + tupleInput[1]
    tupleName = tupleInput[-1] + ", "
    
    #Adds non-secondary names to a string.
    while counter < (tupleLength - 1):
        tupleName += tupleInput[counter] + " "
        counter += 1
        
    #Returns formatted string.
    return "{:30}{:6}{:15}{:6}".format(tupleName, tuplePayroll, tupleJob, tupleSalary)

#======================================================================================================

# Opens a file, converts each line to a tuple, then adds each tuple to a list. 
def openFile(file, tupleList):
    print("")
    try:
        fileContent=open(file)
        myLine=fileContent.readline()
        #Adds tuple form to a list, for each line in the file.
        for line in fileContent:
            while(len(myLine)>0):
                newLine = tupleConversion(myLine)
                tupleList.append(newLine)
                myLine=fileContent.readline()
            fileContent.close
            
    #Returns error and close program.
    except IOError as e:
        print("ERROR: File does not exist!\n")
        print("="*58)
        sys.exit()
    except FileNotFoundError:
        print("ERROR: File does not exist!\n")
        print("="*58)
        sys.exit()
        
#======================================================================================================

# Takes list of tuples and prints employee data for employee with matching payroll number, or prints
# a statement saying otherwise.
def op1(tupleList):
    #Error checking and input.
    lengthCheck = False
    typeCheck = False
    while (lengthCheck==False) or (typeCheck==False):
        payrollNumber = input("\nPlease input a payroll number: ")
        length = len(payrollNumber)
        if (0 > length) or (length > 5):
            print("\nERROR:  Digit count wrong!")
            lengthCheck = False
        else:
            lengthCheck = True
        try:
            payrollNumber = int(payrollNumber)
        except:
            print("\nERROR:  Number not entered!")          
        if type(payrollNumber) != int:
            typeCheck = False
        else:
            typeCheck = True       
    print("")
    
    #Checks payroll against tuple data.
    valid = False
    for tupleItem in tupleList:
        currentTuple = tupleItem
        currentPayroll = currentTuple[0]
        currentPayroll = int(currentPayroll)
        #Returns employee data for matching payroll.
        if str(currentPayroll) == str(payrollNumber):
            print(tuplePrint(currentTuple) + "\n")
            valid = True
            
    #Formatting and no results string.
    if valid:
        print("="*58)
    else:
        print("There is no employee data for this payroll number!\n"+"="*58)
        
#======================================================================================================

#Takes list of tuples and prints employee data for employee with salary between input brackets, or
# prints a statement saying otherwise.
def op2(tupleList):
    #Error checking and input.
    Test1,Test2,Test3 = False,False,False
    valid = False
    while Test1==False or Test2==False or Test3==False:
        salaryLower = float(input("\nPlease input the lower salary bracket: "))
        salaryUpper = float(input("Please input the upper salary bracket: "))
        if salaryLower < 0:
            print("\nERROR: The lower salary bracket value must not be negative!")
            Test1=False
        else:
            Test1=True
        if salaryUpper < 0:
            print("\nERROR: The upper salary bracket value must not be negative!")
            Test2=False
        else:
            Test2=True
        if salaryLower > salaryUpper:
            print("\nERROR: The lower salary bracket value must be lower than the upper salary bracket value!")
            Test3=False
        else:
            Test3=True
    print("")
    
    #Checks tuple salary against input brackets.
    for tupleItem in tupleList:
        currentTuple = tupleItem
        currentSalary = currentTuple[1]
        currentSalary = int(currentSalary)
        #Returns employee data if salary within brackets.
        if (salaryUpper >= currentSalary >= salaryLower):
            print(tuplePrint(currentTuple))
            valid = True
            
    #Formatting and no results string.
    if valid:
        print("\n"+"="*58)
    else:
        print("There is no employee data for this salary range!\n"+"="*58)

#======================================================================================================

# Takes list of tuples and prints employee name for employee with input job, or prints a statement
# saying otherwise.
def op3(tupleList):
    #Variables.
    jobTitle = str(input("\nPlease input the job title as one word: "))
    jobTitle = jobTitle.title()
    namesList = []
    valid = False
    
    #Checks input job against tuple job.
    for tupleItem in tupleList:
        currentTuple = tupleItem
        currentJob = currentTuple[2]
        currentJob = currentJob.title()
        counter = 3
        tupleLength = len(currentTuple)
        currentName = currentTuple[-1] + ", "
        #Gets name from tuple as string.
        while counter < (tupleLength - 1):
            currentName += currentTuple[counter] + " "
            counter += 1
        if currentJob == jobTitle:
            namesList.append(currentName)
            valid = True
            
    #Formatting and returns string depending on search result.
    if valid:
        namesList.sort()
        print("")
        for name in namesList:
            print(name)
        print("\n"+"="*58)
    else:
        print("\nThere are no employees with that job title!\n\n"+"="*58)      

#======================================================================================================

# A loop which lets the the user choose from a list of options and then calls a function based on
# which option was selected.    
def inputLoop(tupleList):
    #Prints information about options.
    print("\n"+"="*58+"\n\nOPTIONS")
    print("1: Full details of employee through payroll number")
    print("2: Full details of employee within specific salary range")
    print("3: Names of employees with specific job title")
    print("4: Quit")
    print("\n"+"="*58)
    
    #Loop returnining function results based on user input.
    options = [None, op1, op2, op3, 4]
    active = True
    while active :
        userChoice = input("\nPlease select an option: ")
        if userChoice == "4":
            print("\n"+"="*58)
            active = False
        elif len(userChoice)==1 and "1"<=userChoice<="4":
            options[int(userChoice)](tupleList)
        else:
            print("\nERROR: Invalid input!\n")

#======================================================================================================

# RUNTIME
import sys

#Formatting.
print("="*58+"\n" + "CE151 Assignment 2 : Ex1             Alex Ebbage : 1504283","\n"+"="*58)

#User input, then data collection.
file = input("\nPlease enter the filename: ")
tupleList = []
openFile(file, tupleList)

#Data output in table form.
print("{:28}{:8}{:15}{:6}".format("EMPLOYEE NAME", "PAYROLL", "JOB_TITLE", "SALARY"))
for item in tupleList:
    print(tuplePrint(item))

#Query loop.  
inputLoop(tupleList)



