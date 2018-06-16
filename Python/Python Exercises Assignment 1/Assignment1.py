#ass1.py
#CE151 Assignment 1
#Created by sands 30/10/10
#Modified by Alex Ebbage 27/10/15
#=============================================================================================

#Exercise 1
#Using the height and hypotenuse, output the 2 interior angles and width of the triangle.
def ex1() :    
    #Presentation
    print("\nExercise 1")
    print("==========")

    #Import math module
    import math

    #User input and error checking
    height = abs(float(input("Please input the height of the triangle: ")))
    hypotenuse = abs(float(input("Please input the length of the hypotenuse: ")))
    while hypotenuse < height:
        print("\nError, the height you've entered is greater than the hypotenuse.\n")
        height = abs(float(input("Please input a new height of the triangle: ")))
        hypotenuse = abs(float(input("Please input a new length of the hypotenuse: ")))

    #Variables and processing: Calculates the width and size of the two interior angles.
    width = round(math.sqrt(hypotenuse**2 - height**2),2)
    interiorAngle1 = round(math.degrees(math.asin(height / hypotenuse)),2)
    interiorAngle2 = round(90 - interiorAngle1,2)
    height = round(height,2)
    hypotenuse = round(hypotenuse,2)

    #Presentation and output
    print("\nThe width of the triangle is: " + str(width) + ".")
    print("The size of the two interior angles are:", str(interiorAngle1) + "°,", str(interiorAngle2) + "°.\n")

#=============================================================================================
    
#Exercise 2
#Using an inputted value, output the first n numbers in the Fibonacci series.
def ex2() :    
    #Presentation
    print("\nExercise 2")
    print("==========")

    #User input
    quantityCap = abs(int(input("Please input the value which dictates how long the Fibonacci series will be: ")))

    #Variables
    a = 1
    b = 0
    c = 0
    quantity = 0
    fibonacci = []
    
    #Processing: Finds the sum of the previous 2 values and appends it to a list.
    while quantity < quantityCap:
        c = a + b
        b = a
        a = c
        fibonacci.append(str(c))
        quantity += 1

    #Presentation and output
    print("\nThe first", quantityCap, "numbers of the Fibonacci series are as follows:")
    print(",".join(fibonacci) + "\n")

#=============================================================================================
    
#Exercise 3
#Determine if the inputted number is a prime or not.
def ex3() :    
    #Presentation
    print("\nExercise 3")
    print("==========")

    #Import math module
    import math

    #User input
    number = abs(int(input("Please input a positive integer for prime evaluation: ")))

    #Variables
    prime = True

    #Processing: Determines if input is prime.
    if number == 2:
        pass
    elif (number <= 1) or (number % 2 == 0):
        prime = False
    else:
        for i in range(3, int(math.sqrt(number)), 2):
            if (number % i) == 0:
                prime = False
                break;

    #Presentation and output
    if prime:
        print("\nThe number", number, "is prime.\n")
    else:
        print("\nThe number", number, "is not prime.\n")
    
#=============================================================================================
  
#Exercise 4
#Using an input for amount of rows and columns, output a  nicely formatted power table.  
def ex4() :   
    #Presentation
    print("\nExercise 4")
    print("==========")

    #User input
    row = abs(int(input("Please input the amount of rows the table will have: ")))
    column = abs(int(input("Please input the amount of columns the table will have: ")))

    #Variables    
    rowString = ""
    columnWidth = 1 
    columnWidth = len(str(row**column))+1

    #Presentation
    print("\nThe powers table you wanted appears as follows: \n")

    #Processing, presentation and output: Find r**c in each column, then repeats on the next row.
    for r in range(1, row+1):
        rowString = ""
        for c in range (1, column+1):
            rowString += (format(r**c, str(columnWidth) + "d"))
        print(rowString)
        
    #Presentation
    print("")

#=============================================================================================
 
#Exercise 5
#Using an inputted string, output each word in the string on a seperate line.   
def ex5() :    
    #Presentation
    print("\nExercise 5")
    print("==========")

    #User input
    string = str(input("Please input the string you want seperated below: \n"))

    #Variable and processing
    words = string.split()
    
    #Presentation
    print("\nWhen each word is on a seperate line, the string you entered appears as follows:\n")

    #Processing and output
    print("\n".join(words), "\n")
    
#=============================================================================================

#Exercise 6
#Using an inputted string, output which vowels are used the most.    
def ex6() :    
    #Presentation
    print("\nExercise 6")
    print("==========")

    #User input
    string = str(input("Please input a string for evaluation below: \n"))
    
    #Variables
    vowels = False
    largestCount = 0

    #Presentation
    print("")
    
    #Processing: Counts vowels in the string.
    aCount = string.lower().count("a")
    eCount = string.lower().count("e")
    iCount = string.lower().count("i")
    oCount = string.lower().count("o")
    uCount = string.lower().count("u")

    #Processing: Checks which vowel is found in the string the most.
    if aCount > largestCount:
        largestCount = aCount
    if eCount > largestCount:
        largestCount = eCount
    if iCount > largestCount:
        largestCount = iCount
    if oCount > largestCount:
        largestCount = oCount
    if uCount > largestCount:
        largestCount = uCount

    #Error checking
    if largestCount != 0:
        vowels = True

    #Processing: Output vowels equal to largest count, as well as their value.
    if (largestCount == 1) and (vowels == True):
        if (largestCount == aCount) and (vowels == True):
            print("There is", aCount, "occurrence of 'a'.")
        if (largestCount == eCount) and (vowels == True):
            print("There is", eCount, "occurrence of 'e'.")
        if (largestCount == iCount) and (vowels == True):
            print("There is", iCount, "occurrence of 'i'.")
        if (largestCount == oCount) and (vowels == True):
            print("There is", oCount, "occurrence of 'o'.")
        if (largestCount == uCount) and (vowels == True):
            print("There is", uCount, "occurrence of 'u'.")
    elif (largestCount > 1) and (vowels == True):
        if (largestCount == aCount) and (vowels == True):
            print("There are", aCount, "occurrences of 'a'.")
        if (largestCount == eCount) and (vowels == True):
            print("There are", eCount, "occurrences of 'e'.")
        if (largestCount == iCount) and (vowels == True):
            print("There are", iCount, "occurrences of 'i'.")
        if (largestCount == oCount) and (vowels == True):
            print("There are", oCount, "occurrences of 'o'.")
        if (largestCount == uCount) and (vowels == True):
            print("There are", uCount, "occurrences of 'u'.")
    elif vowels == False:
        print("There are no vowels in the string.")
        
    #Presentation
    print("")

#=============================================================================================

#Exercise 7
#Using an inputted integer and string, output a decrypted or encrypted string.    
def ex7() :   
    #Presentation
    print("\nExercise 7")
    print("==========")

    #Input
    key = int(input("Please input a key for encryption or decryption: "))
    code = str(input("Please input a string for encryption or decryption below: \n"))

    #Error checking for input
    codeCheck = True
    code = code.lower()    
    control = "abcdefghijklmnopqrstuvwxyz "
    for i in code:
        if i not in control:
            codeCheck = False
            print("\nError, you've used characters other than letters and spaces in the code. \n")
            break
        
    #Variables
    output = ""
    position = 0
    letter = ""
    length = len(code)

    #Processing and presentation: Performs encryption or decryption process.
    while (position < length) and (codeCheck == True):
        print("\nThe encrypted or decrypted code is written below.")
        for i in code:
            if i == " ":
                output += i
                position += 1
            elif i != " ":
                letter = ord(i) - 97
                letter += key
                if (letter % 26) >= 0:
                    letter = (letter % 26)
                    letter = chr(letter + 97)
                    output += str(letter)
                    position += 1
                else:
                    letter = chr(letter + 97)
                    output += str(letter)
                    position += 1

        #Presentation and output
        print(output + "\n")

#=============================================================================================
#Defines function for first triangle.
"""
Loops a print statement, which prints each row of the level 1 triangle out.

Arguments:
a: String: Character 1, the first character inputted by the user.
c: Integer: Inner width of the triangle, used to calculate largest width and most outputs.
d: Integer: Largest width equal to the width of the largest triangle, used for alignment.

Returns: String: Empty string, which stops Python outputting 'None' after the triangle output.
"""
def level1(a,c,d): 
    #Presentation
    print((format("Level 1", "^" + str(d) + "s")))
    print((format("=======", "^" + str(d) + "s")))

    #Variables
    count1 = c
    
    #Processing, presentation and output
    while count1 >= 1:
        print((format(a * count1, "^" + str(d) + "s")))
        count1 -= 2
    else:
        print("")
        return("")

#---------------------------------------------------------------------------------------------
        
#Defines function for second triangle.
"""
Loops a print statement, which prints each row of the level 2 triangle out. Using while loops
and a variable called stage. Stages are used to split the triangle up into levels of patterns.

Arguments:
a: String: Character 1, the first character inputted by the user.
b: String: Character 2, the second character inputted by the user.
c: Integer: Inner width of the triangle, used to calculate largest width and most outputs.
d: Integer: Largest width equal to the width of the largest triangle, used for alignment.

Returns: String: Empty string, which stops Python outputting 'None' after the triangle output.
"""
def level2(a,b,c,d):  
    #Presentation
    print((format("Level 2", "^" + str(d) + "s")))
    print((format("=======", "^" + str(d) + "s")))
    
    #Variables
    stage = 1

    #Processing, presentation and output
    #Stage 1
    while stage == 1:
        count2 = 1
        while count2 <= c:
            print((format(b * count2, "^" + str(d) + "s")))
            count2 += 2
        stage = 2
    #Stage 2    
    while stage == 2:
        count1 = c
        count2 = 1
        while count1 >= 1:
            string1 = (a * count1)
            string2 = (b * count2)
            print((format(string2 + string1 + string2, "^" + str(d) + "s")))
            count1 -= 2
            count2 += 2
        stage = 0
    
    #Presentation    
    if stage == 0:
        print("")
        return("")

#---------------------------------------------------------------------------------------------

#Defines function for third triangle.
"""
Loops a print statement, which prints each row of the level 3 triangle out. Using while loops
and a variable called stage. Stages are used to split the triangle up into levels of patterns.

Arguments:
a: String: Character 1, the first character inputted by the user.
b: String: Character 2, the second character inputted by the user.
c: Integer: Inner width of the triangle, used to calculate largest width and most outputs.
d: Integer: Largest width equal to the width of the largest triangle, used for alignment.

Returns: String: Empty string, which stops Python outputting 'None' after the triangle output.
"""
def level3(a,b,c,d):   
    #Presentation
    print((format("Level 3", "^" + str(d) + "s")))
    print((format("=======", "^" + str(d) + "s")))
    
    #Variables
    stage = 1
    
    #Processing, presentation and output
    #Stage 1
    while stage == 1:
        count1 = (2*c + 1)
        count2 = 1
        while count2 <= c:
            string1 = (a * count1)
            string2 = (b * count2)
            print((format(string1 + string2 + string1, "^" + str(d) + "s")))
            count2 += 2
            count1 -= 2
        stage = 2
    #Stage 2  
    while stage == 2:
        count1 = c
        count2 = 1
        while count2 <= c:
            string1 = (a * count1)
            string2 = (b * count2)
            print((format(string1 + string2 + string1 + string2 + string1, "^" + str(d) + "s")))
            count2 += 2
            count1 -= 2
        stage = 3
    #Stage 3   
    while stage == 3:
        count1 = (2*c + 1)
        while count1 >= 1:
            string1 = (a * count1)
            print((format(string1, "^" + str(d) + "s")))
            count1 -= 2
        stage = 0
    
    #Presentation    
    if stage == 0:
        print("")
        return("")

#---------------------------------------------------------------------------------------------

#Defines function for fourth triangle.
"""
Loops a print statement, which prints each row of the level 4 triangle out. Using while loops
and a variable called stage. Stages are used to split the triangle up into levels of patterns.

Arguments:
a: String: Character 1, the first character inputted by the user.
b: String: Character 2, the second character inputted by the user.
c: Integer: Inner width of the triangle, used to calculate largest width and most outputs.
d: Integer: Largest width equal to the width of the largest triangle, used for alignment.

Returns: String: Empty string, which stops Python outputting 'None' after the triangle output.
"""
def level4(a,b,c,d):   
    #Presentation
    print((format("Level 4", "^" + str(d) + "s")))
    print((format("=======", "^" + str(d) + "s")))
    
    #Variables
    stage = 1
    
    #Processing, presentation and output
    #Stage 1
    while stage == 1:
        count2 = 1
        while count2 <= (4*c + 3):
            string2 = (b * count2)
            print((format(string2, "^" + str(d) + "s")))
            count2 += 2
        stage = 2
    #Stage 2    
    while stage == 2:
        count1 =(2*c + 1)
        count2 = 1
        while count2 <= c:
            string1 = (a * count1)
            string2 = (b * count2)
            print((format(string2 + string1 + string2 + string1 + string2, "^" + str(d) + "s")))
            count2 += 2
            count1 -= 2
        stage = 3
    #Stage 3    
    while stage == 3:
        count1 = c
        count2 = 1
        count3 = (c + 2)
        while count1 >= 1:
            string1 = (a * count1)
            string2 = (b * count2)
            string3 = (b * count3)
            print((format(string3 + string1 + string2 + string1 + string2 + string1  + string3, "^" + str(d) + "s")))
            count1 -= 2
            count2 += 2
            count3 += 2
        stage = 4
    #Stage 4    
    while stage == 4:
        count1 =(2*c + 1)
        count2 =(2*c + 3)
        while count1 >= 1:
            string1 = (a * count1)
            string2 = (b * count2)
            print((format(string2 + string1 + string2, "^" + str(d) + "s")))
            count2 += 2
            count1 -= 2
        stage = 0
    
    #Presentation    
    if stage == 0:
        print("")
        return("")
            
#---------------------------------------------------------------------------------------------
  
#Exercise 8
#Using 2 character inputs and a input for minimum width, output a series of triangle patterns.  
def ex8() :   
    #Presentation
    print("\nExercise 8")
    print("==========")

    #User inputs
    char1 = str(input("Please input the first character: "))
    char2 = str(input("Please input the second character: "))
    innerWidth = int(input("Please input an odd number for the width on the innermost triangle: "))

    #Error checking for input
    while (innerWidth % 2) != 1:
        print("\nError, that's not odd, please try again!")
        innerWidth = int(input("\nPlease input an odd number for the width on the innermost triangle: "))
        
    #Variables
    largestWidth = (8*innerWidth + 7)

    #Presentation formatting
    print("")

    #Output of triangle functions      
    print(level1(char1, innerWidth,largestWidth), level2(char1,char2,innerWidth,largestWidth), level3(char1,char2,innerWidth,largestWidth), level4(char1,char2,innerWidth,largestWidth))

#=============================================================================================

#Main Loop
#Presentation
print("CE151 Assignment 1 - Alex Ebbage")

#Exercise selection
exlist = [None, ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8]
running = True
while running :
    line = input("Select exercise (0 to quit): ")
    if line == "0" : running = False
    elif len(line)==1 and "1"<=line<="8": exlist[int(line)]()
    else : print("Invalid input - try again")


