# ex2.py
# CE151 Assignment 2
# Created by Alex Ebbage 1/12/15
#======================================================================================================

# Opens a file and transfers data stored to a list.
def openFile(file, inputList):
    #Adds a string to a list without \n on the end.
    try:
        data=open(file)
        for line in data:
            inputList.append(line[:-1])
        data.close()
        
    #Returns error and calls same function with new file name.
    except IOError as e:
        print("File does not exist!\n")
        newFile=input("Please enter a new filename: ")
        openFile(newFile, inputList)
    except FileNotFoundError:
        print("File does not exist!\n")
        newFile=input("Please enter a new filename: ")
        openFile(newFile, inputList)

#======================================================================================================

# Removes any empty strings from a list.
def removeBlanks(argsList):
    while '' in argsList:
        argsList.remove("")

#======================================================================================================

# Takes a list of words and returns reversed words to another list.
def reverseWords(argList, reverseArgList):
    for item in argList:
        reverseArgList.append(item[::-1])
    return reverseArgList

#======================================================================================================

# A function which searches through a list of strings, trying to find words from a list in the string
# from the grid list. If a word is found, the word found is added to a dictionary, along with a value
# matching the start and end coordinates of the word in the grid.    
def search(words, grid, indexGrid):
    #For each row in the grid...
    for y in range(0,len(grid)):
        #Check each word against grid.
        for i in range(0,len(words)):
            word = words[i]
            coords = []
            #Then store coordinates if found.
            if word in grid[y]:
                wordLength = len(word)-1
                xStart = grid[y].find(word)
                xEnd = (xStart+wordLength)
                startCoord = indexGrid[y][xStart]
                endCoord = indexGrid[y][xEnd]
                coords.append(startCoord)
                coords.append(endCoord)
                wordsFoundDict[word] = coords
                
        #Check each reversed word against grid.        
        for i in range(0,len(reverseWordsToFind)):
            word = words[i]
            reverseWord = reverseWordsToFind[i]
            coords = []
            #Then store reversed coordinates if found.
            if reverseWord in grid[y]:
                wordLength = len(reverseWord)-1
                xStart = (grid[y].find(reverseWord))
                xEnd = xStart+wordLength
                startCoord = indexGrid[y][xStart]
                endCoord = indexGrid[y][xEnd]
                coords.append(endCoord)
                coords.append(startCoord)
                wordsFoundDict[word] = coords

#======================================================================================================

# Rotates the grid, to allow for vertical searching.
def rotateGrid(grid):
    #Zips the grid, causing it to rotate.
    rotatedMatrix = [list(i) for i in zip(*grid)]
    rotatedGrid = []
    #Adds lines of zipped grid to a new list.
    for line in rotatedMatrix:
        for character in line:
            #If value is a tuple, add to a list.
            if type(character) is tuple:
                rotatedLine = []    
                for character in line:
                    rotatedLine.append(character)
            #Else, add to a string.
            else:                
                rotatedLine = ""    
                for character in line:
                    rotatedLine += character
        rotatedGrid.append(rotatedLine)
    return(rotatedGrid)

#======================================================================================================

# Converts the grid list into a new one, by taking strings of all the values in a diagonal from the
# bottom-left to the top-right. To allow for diagonal searching.
def diagonalGridNW(grid):
    #Determines start point for conversion.
    diagonalGridNW = []
    xLength = len(grid[0])-1
    yLength = len(grid)-1
    xStart = 0
    yStart = yLength
    x = xStart
    y = yStart
    #First corner of grid.
    while y != -1:
        if type(grid[y][x]) is tuple:                
            line = []
            while y < yLength+1:
                line.append(grid[y][x])
                y += 1
                x += 1
            diagonalGridNW.append(line)
            yStart -= 1
            y = yStart
            x = xStart
        else:                
            line = ''
            while y < yLength+1:
                line += grid[y][x]
                y += 1
                x += 1
            diagonalGridNW.append(line)
            yStart -= 1
            y = yStart
            x = xStart
            
    #Midsection of grid.
    yStart = 0
    x = 1
    y = yStart
    while x < xLength-yLength: 
        if type(grid[y][x])is tuple:     
            line = []
            while y < yLength+1:
                line.append(grid[y][x])
                y += 1
                x += 1
            diagonalGridNW.append(line)
            xStart += 1
            y = yStart
            x = xStart+1
        else:     
            line = ''
            while y < yLength+1:
                line += grid[y][x]
                y += 1
                x += 1
            diagonalGridNW.append(line)
            xStart += 1
            y = yStart
            x = xStart+1
            
    #Final corner of grid.
    xStart = xLength - yLength
    yStart = 0
    x = xStart
    y = yStart
    while x < xLength+1:
        if type(grid[y][x]) is tuple:    
            line = []
            while x < xLength+1:
                line.append(grid[y][x])
                y += 1
                x += 1
            diagonalGridNW.append(line)
            xStart += 1
            y = yStart
            x = xStart
        else:    
            line = ''
            while x < xLength+1:
                line += grid[y][x]
                y += 1
                x += 1
            diagonalGridNW.append(line)
            xStart += 1
            y = yStart
            x = xStart
    return diagonalGridNW

#======================================================================================================

# Converts the grid list into a new one, by taking strings of all the values in a diagonal from the
# top-left to the bottom-right. To allow for diagonal searching.
def diagonalGridNE(grid):
    #Determines start position of conversion.
    diagonalGridNE = []
    xLength = len(grid[0])-1
    yLength = len(grid)-1
    xStart = 0
    yStart = 0
    x = xStart
    y = yStart
    #First corner of grid.
    while y != yLength:
        if type(grid[y][x]) is tuple:    
            line = []
            while y > -1:
                line.append(grid[y][x])
                y -= 1
                x += 1
            diagonalGridNE.append(line)
            yStart += 1
            y = yStart
            x = xStart
        else:    
            line = ''
            while y > -1:
                line += grid[y][x]
                y -= 1
                x += 1
            diagonalGridNE.append(line)
            yStart += 1
            y = yStart
            x = xStart
            
    #Midsection of grid.
    yStart = yLength
    x = 1
    y = yStart
    while x < xLength-yLength:
        if type(grid[y][x]) is tuple:      
            line = []
            while y > -1:
                line.append(grid[y][x])
                y -= 1
                x += 1
            diagonalGridNE.append(line)
            xStart += 1
            y = yStart
            x = xStart+1
        else:      
            line = ''
            while y > -1:
                line += grid[y][x]
                y -= 1
                x += 1
            diagonalGridNE.append(line)
            xStart += 1
            y = yStart
            x = xStart+1
            
    #Final corner of grid.
    xStart = xLength - yLength
    yStart = yLength
    x = xStart
    y = yStart
    while x < xLength+1:
        if type(grid[y][x]) is tuple:    
            line = []
            while x < xLength+1:
                line.append(grid[y][x])
                y -= 1
                x += 1
            diagonalGridNE.append(line)
            xStart += 1
            y = yStart
            x = xStart
        else:    
            line = ''
            while x < xLength+1:
                line += grid[y][x]
                y -= 1
                x += 1
            diagonalGridNE.append(line)
            xStart += 1
            y = yStart
            x = xStart
    return diagonalGridNE

#======================================================================================================

# Creates a corresponding matrix of indexes to match the word grid. Making it easier to find the grid
# coordinates of found words.
def indexMatrix(grid, indexGrid):
    #Creates a matrix of tuples based on grid size.
    for y in range(0,len(grid)):
        yLine = []
        for x in range(0,len(grid[0])):
            index = (x,y+1)
            yLine.append(index)
        indexGrid.append(yLine)
    return indexGrid

#======================================================================================================

# Takes the dictionary and a list of words, then outputs the coordinates of found words, then words that
# weren't found in the search.
def wordsFound(wordDict, words):
    #Prints words found and their coordinates.
    print("\nWORDS FOUND")
    keyList = []
    for key in sorted(wordDict):
        keyList.append(key)
        startCoords = wordDict[key][0]
        endCoords = wordDict[key][1]
        print("%s can be found from [row %s column %s] to [row %s column %s]." % (key.title(), startCoords[1], startCoords[0], endCoords[1], endCoords[0]))   

    #Prints words not found.
    print("\nWORDS NOT FOUND")
    for word in words:
        if word not in keyList:
            print("%s not found!" % (word.title()))

#======================================================================================================

# RUNTIME
# Formatting.
print("\n" + "<*>" + "="*77 + "<*>"+"\n"  + " "*8 +  "CE151 Assignment 2 : Ex2" + " "*22 + "Alex Ebbage : 1504283","\n" + "<*>" + "="*77 + "<*>")

# Variables.
wordsToFind = []
reverseWordsToFind = []
wordsFoundDict = {}
grid = []
indexGrid = []
vertGrid = []

# Get name of grid file and take data.
gridInput = str(input("\nPlease input the grids file name: "))
openFile(gridInput, grid)

# Print the grid.
print("\nGRID")
for i in grid:
    print(i)

# Get name of words file and take data.    
wordsInput = str(input("\nPlease input the words file name: "))
openFile(wordsInput, wordsToFind)
removeBlanks(wordsToFind)

# Print words to find.    
print("\nWORDS TO FIND")
printWords = ""
for word in sorted(wordsToFind):
    printWords += word.title()
    printWords += ", "
printWords = printWords[0:-2]
print(printWords)

# Prepare index and word lists for search.
reverseWords(wordsToFind, reverseWordsToFind)
indexMatrix(grid, indexGrid)

# Formatting.    
print("\n" + "<*>" + "="*77 + "<*>")

# Search and print words found.
wordsFoundList = []
search(wordsToFind, grid, indexGrid)
search(wordsToFind, rotateGrid(grid), rotateGrid(indexGrid))
search(wordsToFind, diagonalGridNE(grid), diagonalGridNE(indexGrid))
search(wordsToFind, diagonalGridNW(grid), diagonalGridNW(indexGrid))
wordsFound(wordsFoundDict, wordsToFind)

# Formatting.
print("\n" + "<*>" + "="*77 + "<*>")

