# -*- coding: cp1252 -*-
#===========================================================================================================
# PART 1 : TOKENIZATION and PART-OF-SPEECH TAGGING
# Written by Alex Ebbage - 1504283
#===========================================================================================================
# Prints the presentation text. Instantiates the text in a variable. Tokenizes the words within in, then
# goes through the lower-case words removing any which are just punctuation. Then uses the pos_tag() method
# to tag all the tokens. It then prints out the PrivateEye text followed by a table of the tagged tokens.
#-----------------------------------------------------------------------------------------------------------
import nltk
from nltk import re
from nltk import word_tokenize
from nltk import sent_tokenize
from nltk.corpus import brown
from nltk.corpus import treebank

# Prints the presentation text.
print("=" * 80 + "\n PART 1 : TOKENIZATION and PART-OF-SPEECH TAGGING \n" + "=" * 80)

# Store the body of text in a variable.
text =  """
A Message From The Headmaster
Kow tow!
That's the Chinese for "hello", as I've learnt this week, because those were the first words said to me by
the Head of our Chinese sister academy, the Tiananmen Not At All Free School.
As you've probably gathered, we were rolling out the school "red"(!) carpet this week for Mr Xi Sho-pping,
who was on a visit to try and buy as much of the school as we could sell him.
...
Mr Sho-pping then visited the old boiler room and we signed an agreement for him to build not only a new
nuclear boiler to replace it, but to build another two boilers, just in case the other one blows up, which
it won't of course. Even better, all the boilers will be run entirely by himself and members of the Beijing
Nuclear Intelligence Services Department, who are delighted to be the given the chance of experimenting
with untried nuclear technology in someone else's school. How exciting is that! I can't have been the only
one to feel a warm glow at the thought of so much radioactivity at the very heart of the school. Who
knows, by this time next year I could be the Two-head-master! (Finkelstein, D., you're on fire – as is the
boiler room!)
D.C.
"""

# Print the PrivateEye text.
print(text)

# Use NLTKs method to tokenize the text, then convert the text to lowercase and remove all the punctuation.
text_tokens = word_tokenize(text)
text_sent = sent_tokenize(text)
text_tokens_nopunct = [word.lower() for word in text_tokens if re.search("\w", word)]

# Use NLTKs POS tagger method to tag all the tokens.
pos_tagged_tokens = nltk.pos_tag(text_tokens_nopunct)

print("Training data and generating the token-tag table...\n")

# Creates trainer data using the Brown corpus.
trainer_data = brown.tagged_sents()[:10000]

# Trains an HMM tagger using the trainer data, then uses it to tag all the tokens.
hmm_trainer = nltk.hmm.HiddenMarkovModelTrainer()
hmm_tagger = hmm_trainer.train_supervised(trainer_data)
hmm_tagged_tokens = []
for s in text_sent:
    s_tokens = nltk.word_tokenize(s)
    s_tokens = [word.lower() for word in s_tokens if re.search("\w", word)]
    s_tagged = hmm_tagger.tag(s_tokens)
    for t in s_tagged:
        hmm_tagged_tokens.append(t)

# Trains a unigram tagger using the Brown corpus, then uses it to tag all the tokens.
unigram_tagger = nltk.tag.UnigramTagger(trainer_data)
unigram_tagged_tokens = unigram_tagger.tag(text_tokens_nopunct)

# Print a table of the tagged word and the tags it receives from different taggers.
tagged_token_amount = len(pos_tagged_tokens)
longest_word = 0
header = ["Token", "POS", "HMM", "Unigram"]
rows = []

for i in range(tagged_token_amount):
    rows.append([pos_tagged_tokens[i][0], pos_tagged_tokens[i][1], hmm_tagged_tokens[i][1], unigram_tagged_tokens[i][1]])
    if(len(pos_tagged_tokens[i][0]) > longest_word):
        longest_word = len(pos_tagged_tokens[i][0])

row_string = "-" * (longest_word + 37)
format_string = "| {:"+str(longest_word+1)+"}| {:9}| {:9}| {:9}|"
print(row_string + "\n" +format_string.format(header[0], header[1], header[2], header[3]) + "\n" + row_string)
for r in rows:
    print(format_string.format(r[0], r[1], r[2], r[3]))
print(row_string)


#===========================================================================================================
# TAGGING ANALYSIS
#===========================================================================================================
# POS Tagger
# ==========
# The POS tagger produced the best results, accurately assigning correct tags to most of the tokens. There
# isn't much to say about it in regards to errors as it is pretty spot on. When it can't identify a word it
# appears to assign it as a noun. It wrongly assigned the token 'tow' to be a verb when it should have been
# a noun as it's part of the headmaster's name. Ambiguity is a big problem when it comes to tagging tokens
# and is hard to solve. This tagger selects the most probable tag and when it comes to 'tow' it is far more
# likely to be the verb than to be a name.

# Unigram Tagger
# ==============
# The Unigram tagger produced the second best results, assigning relatively accurate tags to most of the
# tokens. However, when it couldn't identify a word correctly it was assigned the 'None' tag, which is
# better than assigning AT to everything like the HMM tagger did. It was unable to assign tokens to ones
# which started with an apostrophe. It also struggled to assign several nouns and a few adjectives.

# HMM Tagger
# ==========
# The HMM tagger produced the worst results, occassionaly assigning correct tags to small groupings of
# tokens, mainly working for the first sentence. I thought this might be down to tagging the whole token
# set after removing punctuation, so I instead tried to tag each sentence individually, however this had
# a very minor improvement to the result. I also tried using the whole Brown corpus to train the trainer
# data, causing the program to take several minutes to run and having a minor effect on the results. The
# reason it doesn't work well anymore is because the functionality of the method was changed in a Python
# update, causing it to behave incorrectly, assigning AT incorrectly to most tokens.
#
# For the few tokens it does assign values to, it gets them right. Most of the tags it gets are identical
# to the results gained from the Unigram tagger. However, it assigns PPO to the token 'you' which is
# followed by ''ve', and Unigram assigend PPSS which I think is a better option.



#===========================================================================================================
# END OF PROGRAM
#===========================================================================================================
