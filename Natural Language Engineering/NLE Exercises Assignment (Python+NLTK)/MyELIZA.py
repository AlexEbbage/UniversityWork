#===========================================================================================================
# PART 3 : MY ELIZA IMPLEMENTATION
# Written by Alex Ebbage - 1504283
#===========================================================================================================
import random
import re

# Contains a regular expression and all the available responses for matching that pattern.
pattern_response_pairings = [
    ['.*(paris|berlin) (on|in) the ((?:\w*)(?: of)?(?: \w*)?)',
     ["We are sorry, but all our flights {1} the {2} to {0} are fully booked.",
      "We apologise, but all our services to {0} {1} the {2} have been cancelled.",
      "There are no available flights {1} the {2} to {0}. Please accept our apologies."]],
    
    ['.*(paris|berlin) (on|in|next) ((?:\w*) ?(?:\w*)?)',
     ["We are sorry, but all our flights {1} {2} to {0} are fully booked.",
      "We apologise, but all our services to {0} {1} {2} have been cancelled.",
      "There are no available flights {1} {2} to {0}. Please accept our apologies."]],
    
    ['.*(paris|berlin) (tomorrow|today)',
     ["We are sorry, but all our flights {1} to {0} are fully booked.",
      "We apologise, but all our services to {0} {1} have been cancelled.",
      "There are no available flights {1} to {0}. Please accept our apologies."]],
    
    ['.*(on|in) the ((?:\w*)(?: of)?(?: \w*)?) to (paris|berlin)',
     ["We are sorry, but all our flights {0} the {1} to {2} are fully booked.",
      "We apologise, but all our services to {2} {0} the {1} have been cancelled.",
      "There are no available flights {0} the {1} to {2}. Please accept our apologies."]],
    
    ['.*(in|on|next) (\w*) to (paris|berlin)',
     ["We are sorry, but all our flights {0} {1} to {2} are fully booked.",
      "We apologise, but all our services to {2} {0} {1} have been cancelled.",
      "There are no available flights {0} {1} to {2}. Please accept our apologies."]],
    
    ['.*(tomorrow|today) to (paris|berlin)',
     ["We are sorry, but all our flights {0} to {1} are fully booked.",
      "We apologise, but all our services to {1} {0} have been cancelled.",
      "There are no available flights {0} to {1}. Please accept our apologies."]],
    
    ['.*(paris|berlin)',
     ["When would you like to book your flight to {0}?"]],
    
    ['.*to (\w*)',
     ["We don't do flights to {0}. Only to Paris or Berlin."]],

    ['^(in|on|next) ((?:\w*) ?(?:\w*)?)',
     ["We are sorry, but all our flights {0} {1} are fully booked.",
      "We apologise, but all our services {0} {1} have been cancelled.",
      "There are no available flights {0} {1}. Please accept our apologies."]],

    ['.* (in|on|next) ((?:\w*) ?(?:\w*)?)',
     ["We are sorry, but all our flights to {0} {1} are fully booked.",
      "We apologise, but all our services {0} {1} have been cancelled.",
      "There are no available flights {0} {1}. Please accept our apologies."]],
    
    ['.*(monday|tuesday|wednesday|thursday|friday|saturday)',
     ["We are sorry, but all our flights on {0} are fully booked.",
      "We apologise, but all our services on {0} have been cancelled.",
      "There are no available flights on {0}. Please accept our apologies."]],
   
    ['(?:hi|hey|hello|greetings)',
     ["Hello, where would you like to fly and when?"]],
   
    ['.*no (?:thanks|thank you)',
     ["Okay then."]],
    
    ['.*(?:thanks|thank you)',
     ["You're welcome."]],

    ['(?:quit|exit|end)',
     ["Thank you for using our service. Your account has been charged. Goodbye!"]],

    ['(.*)',
     ["Please provide information about where you'd like to travel and when.",
      "This service is for booking flights only. Please specify where you'd like to travel and when."]]
    ]



#===========================================================================================================
# PROCESS RESPONSE
#===========================================================================================================
# Takes the user's input as an argument, then iterates through all the expression-response pairings. If one
# is found then a random response is chosen from the available ones, then matched key words are slotted into
# the responses to adapt the response for each input. This is then returned.
#-----------------------------------------------------------------------------------------------------------

def process_response(user_input):
    #Iterate through the pattern_response_pairings and return when a match is found.
    for regular_expression, responses in pattern_response_pairings:
        processing_result = re.match(regular_expression, user_input.rstrip(".!?"), flags=re.I)
        # If a match is found then select a random response and process the response for returning.
        if(processing_result):
            response = random.choice(responses)
            # For each format space, place one of the matched values inside.
            return response.format(*[match for match in processing_result.groups()])



#===========================================================================================================
# MAIN
#===========================================================================================================
# Prints some presentation text, then starts with a message to the user prompting for an input about where
# they'd like to fly and when. Then a loop requests a user input, processes it, then outputs a response.
# This occurs until 'end', 'exit' or 'quit' are used as inputs, in which case the loop is broken and the
# program ends.
#-----------------------------------------------------------------------------------------------------------

print("=" * 80 + "\n PART 3 : WRITE YOUR OWN VERSION OF ELIZA \n" + "=" * 80) 

print("ELIZA: Hello, where would you like to fly and when?")

while(True):
    user_input = raw_input(" USER: ")
    print("ELIZA: " + process_response(user_input))

    if(user_input == "end" or user_input == "exit" or user_input == "quit"):
          break



#===========================================================================================================
# END OF PROGRAM
#===========================================================================================================
