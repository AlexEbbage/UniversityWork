#===========================================================================================================
# PART 2 : REGULAR EXPRESSIONS, FSAs and FSTs
# Written by Alex Ebbage - 1504283
#===========================================================================================================
# Prints some presentation text. Then prints text at a few intervals to track progress as the program runs.
# Downloads the contents of the BBC website, then scrapes all the contents between <p> html tags. The
# regular expressions is then applied using the re.match() method to find all the matches which are stored
# in an array. Validation is then performed on the results: If no currency type indicator is provided, then
# nothing is returned. If no curreny amount is provided, then nothing is returned. The expression produces
# 3 results, the first contains where the currency sign (Â£, $ or â‚¬), the second contains the value in a
# variety of formats and the third contains an ending currency indicator (p or euro/s). The first and third
# values are used to determine which type of currency it is. The middle value determines the quanity and is
# not changed unless the third value is 'p', in which case the value is converted to a number, divided by
# 100 and returned to string format. After the validation the output is provided saying a match is found,
# what the match is, the currency type and the quanity.
#-----------------------------------------------------------------------------------------------------------

import urllib
import nltk
from nltk import re

# Prints presentation text.
print("=" * 80 + "\n PART 2 : REGULAR EXPRESSIONS, FSAs and FSTs \n" + "=" * 80)

# Add the website source and scrape the contents of the <p> tags.
print("Loading website 'http://www.bbc.co.uk/news/business-41779341'...")
website = urllib.urlopen('http://www.bbc.co.uk/news/business-41779341').read()
print("Finding text between <p></p> tags...")
pTagText = re.findall('<p>(.*?)</p>',website, flags=re.I)

# The regular expression.
regular_expression = '(?:([€$£])((?:\d\d{0,2})(?:,\d{3})*(?:\.\d+)?(?:k|mn|bn|tn)?))|(?:((?:\d\d{0,2})(?:,\d{3})*(?:\.\d+)?(?:k|mn|bn|tn)?)( ?pence|p| ?euros?| ?dollars?| ?pounds?))'

# Iterates through the text gained from the <p> tags and applies the regular expression on them to search
# for matches, which if found, are added to the results array.
results = []
print("Finding currency related text from website contents...\n")
for p in pTagText:
    results += re.findall(regular_expression, p, flags=re.I)

# If no results are found print so.    
if(len(results) == 0):
    print("No matches found.")
    
# Otherwise, commence the validation process for each of the matched results.
else:
    for r in results:
        result = ''.join(r)
        currency = ''
        amount = ''
        if(r[0]):
            amount = r[1]
            currency = r[0]
        if(r[2]):
            amount = r[2]
            currency = r[3].replace(' ', '')

        if(currency == '€' or currency == 'euros' or currency == 'euro'):
            currency = 'Euros'

        if(currency == '$' or currency == 'dollars' or currency == 'dollar'):
            currency = 'Dollars'

        if(currency == '£' or currency == 'pounds' or currency == 'pound'):
            currency = 'Pounds'

        if(currency == 'pence' or currency == 'p'):
            currency = 'Pence'

        # Print the output as requested in the brief.           
        print("Found a match: " + result)
        print("Currency: " + currency)
        print("Amount: " + amount + "\n")
            

#===========================================================================================================
# END OF PROGRAM
#===========================================================================================================
