#!/usr/bin/env python
#================================================================================
# CE306 ASSIGNMENT 1
# WRITTEN BY 1504283
#================================================================================

import nltk, operator, sys, urllib
from nltk.corpus import stopwords, wordnet
from bs4 import BeautifulSoup
from nltk import re

#================================================================================
# OUTPUT STAGE RESULTS
#================================================================================
# Takes a string, data and an int as arguments. It uses the string as a title and
# outputs it between 2 lines, then outputs the variable passed as the 2nd
# argument afterwards, using different formating based on the int indicator.
# The output is both printed and appended to the end of a file.
#--------------------------------------------------------------------------------
def Output_Stage_Results(title, results, indicator, file):
    print("=" * 80 + "\n" + title + "\n" + "=" * 80)
    file.write("=" * 80 + "\n" + title + "\n" + "=" * 80 + "\n")
    
    if(indicator == 0):
        print(results)
        file.write(results)
        
    elif(indicator == 1):
        for item in results:
            print(item)
            file.write(("(" + ", \"".join(item) + "\")\n"))
        
    elif(indicator == 2):
        for item in results:
            print(item)
            file.write("("+str(item[0]) + ", [\"" + "\", \"".join(item[1]) + "\"])\n")
        
    elif(indicator == 3):
        for item in results:
            print(item)
            tag = item[0]
            pairs = []
            for pair in item[1]:
                pairs.append("(\"" + pair[0] + "\", \"" + pair[1] + "\")")
            file.write("("+tag + ", [" + ", ".join(pairs) + "]\n")
        
    elif(indicator == 4):
        for item in results[0]:
            print(item)
            file.write(item + "\n")
            
        for item in results[1]:
            print(item)
            file.write(item + "\n")
        
    elif(indicator == 5):
        for item in results:
            print(item)
            file.write(item + "\n")
            
    print("\n")
    file.write("\n")


#================================================================================
# GET HTML FROM URL
#================================================================================
# Takes a string containing the name of a URL as an argument. Extracts the data
# from the webpage and converts it to raw source code using BeautifulSoup. This
# is then returned.
#--------------------------------------------------------------------------------
def Get_HTML_From_URL(url):
    data = urllib.request.urlopen(url).read()
    html = BeautifulSoup(data, 'html.parser')
    return html

    
#================================================================================
# PARSE HTML
#================================================================================
# Takes the url source code as an argument. Extracts the meta description and or
# keywords, if provided, then stores it for keyword/phrase selection in a later
# stage. Removes certain tags which contain useless information, extracts
# headings and then finally gets the text from the remaining content.
# The text is then formatted slightly by removing excessive whitespace to make
# it cleaner for both outputting and for processing later on.
#--------------------------------------------------------------------------------
def Parse_HTML(html):
    # Get the metadata if its available.
    meta_data = []
    meta_keywords = html.find("meta", attrs={"name":"keywords"})
    meta_description = html.find("meta", attrs={"name":"description"})
    if(meta_keywords):
        meta_data.append(("meta", meta_keywords.get("content")))
    elif (meta_description):
        meta_data.append(("meta", meta_description.get("content")))

    # Remove script tags which don't provide useful information.
    for script_tag in html("script"):
        script_tag.decompose()

    # Extract the headings and title from the html.
    headings = []
    for heading_tag in html.find_all(["title","h1","h2","h3","h4","h5","h6"]):
        heading = heading_tag.extract()
        tag_name = "h"
        if(heading.name == "title"):
            tag_name = "title"
        heading_data = (tag_name ,heading.get_text().replace("\n", " "))
        headings.append(heading_data)
        
    # Get the rest of the content and clean the format up.
    initial_text = html.get_text()
    edited_text = re.sub("\n +", "\n", initial_text)
    edited_text = re.sub("\n{3,}", "\n", edited_text)
    text = ("contents", edited_text)

    # Compiles all the extracted informatino into an arary.
    parsed_text = []
    for pair in headings:
        parsed_text.append(pair)
    for pair in meta_data:
        parsed_text.append(pair)
    parsed_text.append(text)

     
    return parsed_text

    
#================================================================================
# TOKENIZE
#================================================================================
# Takes the text, headings and meta_data as arguments within an array.
# Remove apostraphes from the text and replaced "/" with a space, seperating
# word clusters. Removing the apostraphe is an effective rule used by the TREC
# collections.
# The parsed text is looped through, split into sentences and pre-processed for
# tokenizing. NLTK's word tokenizer is used on the text, then each token is
# converted to lower-case. 
#--------------------------------------------------------------------------------
def Tokenize(parsed_text):
    tokenized_data = []

    # While looping through, pre-process the text.
    for pair in parsed_text:
        tag = pair[0]
        data = pair[1].lower()
        data = data.replace("'", "")
        data = data.replace(":", "")
        data = data.replace("/", " ")

        # If it is meta data, split the information and split it into sentences.
        # Then word tokenize each sentence.
        if(tag == "meta"):
            sentences = data.replace("\n", " ").split(", ")
            for sentence in sentences:
                tokenized_data.append(("meta", nltk.word_tokenize(sentence)))
            
        # Else, if it is a heading, simply word tokenize it.
        elif (tag in ["title", "h"]):
            tokenized_data.append((tag, nltk.word_tokenize(data)))

        # Else, it must be contents, so seperate it into sentences as best as
        # possible, then word tokenize each sentence.
        else:
            sentences = nltk.sent_tokenize(data)
            for sentence in sentences:
                tokenized_data.append(("contents", nltk.word_tokenize(sentence)))

    return tokenized_data

    
#================================================================================
# POS TAG TOKENS
#================================================================================
# Takes the tokenized data as an argument. Then iterates through it, using the
# NLTK POS tagger to tag its contents. It then returns the tagged data.
#--------------------------------------------------------------------------------
def POS_Tag_Tokens(tokenized_data):
    tagged_data = []
    
    for pair in tokenized_data:
        tag = pair[0]
        data = pair[1]
        tagged_data.append((tag, nltk.pos_tag(data)))
                                         
    return tagged_data

    
#================================================================================
# SELECT KEYWORDS AND PHRASES
#================================================================================
# Takes the tagged data as an argument. Iterates through the tagged data, first
# checking for noun phrases using POS grammar, keeping track of the frequency
# as it goes along. Secondly, perform stopping on a sentence to remove irrelevant
# words such as determiners (DT), then iterate through each word-tag pair,
# counting the frequency of selected tags, using the html tag to weight values.
# If the main tag is "meta" or "title", the word/phrase shall be added to the
# final indexed terms list. Lemmatization takes place during the assembly of
# noun phrases and when checking singular words.
#--------------------------------------------------------------------------------
def Select_Keywords_And_Phrases(tagged_data):
    # Variables used for keyword/phrase selection.
    tag_weightings = {"title":5, "meta":5, "h":3, "contents":1}
    stop_words = list(stopwords.words('english'))
    grammar = """
            NBAR:
                {<NN.*|JJ>*<NN.*>}
            NP:
                {<NBAR>}
                {<NBAR><IN><NBAR>}
            """

    # Variables to store information for selection.
    keywords = []
    key_phrases = []
    word_frequency = {}
    phrase_frequency = {}

    # Iterate through the pairs of data, keeping track of the weighting modifier.
    for pair in tagged_data:
        tag = pair[0]
        data = pair[1]
        weighted_value = tag_weightings[tag]

        # If meta or title data, add it to the keyphrases/keywords lists.
        if(tag in ["title", "meta"]):
            if(len(data) == 1):
                keywords.append(Lemmatize(data[0][0], data[0][1]))
            else:
                words = []
                for word, tag in data:
                    words.append(Lemmatize(word, tag))
                key_phrase = " ".join(words)
                if(key_phrase not in key_phrases):
                    key_phrases.append(key_phrase)

        # Use POS patterns to find noun phrases, then add weighted values to phrase frequency.
        chunker = nltk.RegexpParser(grammar)
        result = chunker.parse(data)
        for subtree in result.subtrees():           
            if((subtree.label() == "NP")):
                leaves = []
                noun_phrase = ""
                if(1 < len(subtree.leaves()) and len(subtree.leaves()) < 5):
                    for word, tag in subtree.leaves():
                        leaves.append(Lemmatize(word, tag))
                    noun_phrase = " ".join(leaves)
                    modifier = 1
                    if(len(subtree.leaves()) == 3):
                        modifier = weighted_value * 3
                    if(noun_phrase in phrase_frequency):
                        phrase_frequency[noun_phrase] += 1.0 * modifier
                    else:
                        phrase_frequency[noun_phrase] = 1.0 * modifier
        
        # Ignore stop-words, check for selected POS tag, then add weighted values to word frequency.
        no_punctuation_data = [(word.lower(), tag) for word, tag in data if re.search("\w", word)]
        for word_tag_pair in no_punctuation_data:
            if((word_tag_pair[0] not in stop_words) and (word_tag_pair[1][0:2] in ["NN", "VB", "JJ"])):
                lemma = Lemmatize(word_tag_pair[0], word_tag_pair[1])
                if(lemma in word_frequency):
                    word_frequency[lemma] += 1.0 * weighted_value
                else:
                    word_frequency[lemma] = 1.0 * weighted_value
        
    # Find values from the word frequencies to select keywords.
    total = 0.0
    count = 0
    for key, value in word_frequency.items():
        if(value > 1):
            count += 1
            total += value
    average = total /count
    cap = count / average

    # Select keywords from the frequencies.
    for key, value in word_frequency.items():
        if(average < value and value < cap):
            if(len(key) > 3):
                if(key not in keywords):
                    keywords.append(key)

    # Select key phrases from the frequencies.
    tracker = 0
    for key, value in reversed(sorted(phrase_frequency.items(), key=operator.itemgetter(1))):
        if(tracker < 7):
            if(key not in key_phrases):
                key_phrases.append(key)
            tracker += 1
        else:
            break

    return keywords, key_phrases


#================================================================================
# LEMMATIZER
#================================================================================
# Takes a word an a pos_tag as arguments, lemmatizes the word using the extra
# information provided by the pos tag, then returns the lemma.
#--------------------------------------------------------------------------------
def Lemmatize(word, pos_tag):
    lemmatizer = nltk.WordNetLemmatizer()
    
    if(pos_tag[0] == "N"):      
        return lemmatizer.lemmatize(word, wordnet.NOUN)
    elif(pos_tag[0] == "J"):      
        return lemmatizer.lemmatize(word, wordnet.ADJ)
    elif(pos_tag[0] == "V"):      
        return lemmatizer.lemmatize(word, wordnet.VERB)
    elif(pos_tag[0] == "R"):      
        return lemmatizer.lemmatize(word, wordnet.ADV)
    else:
        return word
    

#================================================================================
# FINALIZATION
#================================================================================
# Takes keywords and key phrases as an argument. It iterates through the values
# of the keywords array to compile the final array. It is then sorted into
# alphabetical order and then returned.
#--------------------------------------------------------------------------------
def Finalization(keywords, key_phrases):
    key_values = []

    for phrase in key_phrases:
        key_values.append(phrase)

    for word in keywords:
        key_values.append(word)

    key_values.sort()
    
    return key_values
    

#================================================================================
# MAIN ROUTINE
#================================================================================
# Opens a url input via command line, or returns an error. Goes through the
# processing pipeline for a given webpage step by step, printing the output
# to the console, as well as a file. The final result is a list of keywords and
# phrases which could be used by a search engine to find the website.
#--------------------------------------------------------------------------------
if len(sys.argv) < 2:
	print("Invalid Argument, Usage: " + str(sys.argv[0]))
	sys.exit(1)
	
else:
    url = sys.argv[1]
    html = Get_HTML_From_URL(url)
    url_title = html.find("title").get_text()
    file = open(url_title + "_Output.txt", "w")
    Output_Stage_Results("HTML FROM URL: " + url, html.prettify(), 0, file)

    parsed_text = Parse_HTML(html)
    Output_Stage_Results("TEXT PARSED FROM HTML", parsed_text, 1, file)

    tokenized_data = Tokenize(parsed_text)
    Output_Stage_Results("TOKENIZED TEXT", tokenized_data, 2, file)

    tagged_tokens = POS_Tag_Tokens(tokenized_data)
    Output_Stage_Results("POS TAGGED TOKENS", tagged_tokens, 3, file)

    keywords, key_phrases = Select_Keywords_And_Phrases(tagged_tokens)
    Output_Stage_Results("KEYWORDS AND PHRASES", [keywords, key_phrases], 4, file)

    key_values = Finalization(keywords, key_phrases)
    Output_Stage_Results("FINALIZATION", key_values, 5, file)

file.close()
    
#================================================================================
# END OF PROGRAM
#================================================================================
