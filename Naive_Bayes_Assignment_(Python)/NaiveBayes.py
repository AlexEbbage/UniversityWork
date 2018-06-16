# PART 4
# Written by Alex Ebbage 1504283

#==========================================================================================
# IMPORT TRAINING DATA
#==========================================================================================
# Import the data from sampleTrain.txt, then read it line by line, storing the document id,
# class and words for each row inside the training data array. Keeping count of the classes
# for later on.
#------------------------------------------------------------------------------------------
training_data = []
class_0_count = 0
class_1_count = 0
class_count = 0

with open('sampleTrain.txt') as file:
    for line in file.readlines():
        split_line = line.split()
        document_id = split_line[0]
        class_id = int(split_line[1])
        words = split_line[2:]
        training_data.append([document_id, class_id, words])
        
        class_count += 1
        if(class_id == 0):
            class_0_count += 1
        elif(class_id == 1):
            class_1_count += 1

# Get a list of all the words used within the documents. I used the file contents instead
# of writing it by hand so the program is more flexible; in-case new vocab was added to
# the file.
word_list = []
with open('sampleTrain.vocab.txt') as file:
    for line in file.readlines():
        word_list.append(line.strip())

        
#==========================================================================================        
# COMPUTE MODEL USING TRAINING DATA
#==========================================================================================
# Calculate prior probabilities by dividing the class count from earlier by the total
# amount of classes. Counts how many words appear in each class and how many unique words
# are in the vocabulary of the documents. Calculates the feature likelihoods of each word
# in the vocab for both classes using laplace smoothing.
#------------------------------------------------------------------------------------------
class_0_prob = class_0_count / class_count
class_1_prob = class_1_count / class_count

# Count how many times a word appears for each class for use later on.
class_0_feature_count = {}
class_1_feature_count = {}

for word in word_list:
   class_0_feature_count[word] = 0
   class_1_feature_count[word] = 0

for row in training_data:
    for word in row[2]:
        if word in word_list:
            if(row[1] == 0):
               class_0_feature_count[word] += 1.0
            elif(row[1] == 1):
               class_1_feature_count[word] += 1.0

# Count how many unique words appear in all the documents and the total amount of words
# in each class.
unique_words = len(word_list)
class_0_words = 0
class_1_words = 0

for key, value in class_0_feature_count.items():
    class_0_words += value

for key, value in class_1_feature_count.items():
    class_1_words += value

# Calculate feature likelihoods for each class using the values gained earlier and by
# using laplace smoothing. The formula used is taken from the notes and for a given class,
# Class, and a given word, Word, does:

#               (Number of Documents in Class containing Word + 1)
#   ---------------------------------------------------------------------------------
#    (Number of words in Documents of Class + Number of Unique words in all Documents)

# Initiate dictionaries for each class to store the feature liklihoods for each word.
class_0_feature_likelihood = {}
class_1_feature_likelihood = {}

for word in word_list:
    class_0_feature_likelihood[word] = (class_0_feature_count[word] + 1) / (class_0_words + unique_words)
    class_1_feature_likelihood[word] = (class_1_feature_count[word] + 1) / (class_1_words + unique_words)


#==========================================================================================        
# PREDICT CLASS OF TEST SET
#==========================================================================================
# Store the test data from sampleTest.txt by iterating through it line by line, similarly
# to how the tranining data was stored. Keeping track of the true values for calculating
# the accuracy later on. Calculates the predicted probabilites using the prior probability
# and feature likelihood of each word for both classes, then selects the class with the
# greater probability.
#------------------------------------------------------------------------------------------
test_data = []
true_values = {}
predictions = {}

with open('sampleTest.txt') as file:
    for line in file.readlines():
        split_line = line.split()
        document_id = split_line[0]
        class_id = int(split_line[1])
        words = split_line[2:]
        test_data.append([document_id, class_id, words])
        predictions[document_id] = 0
        true_values[document_id] = class_id

# Find the predicted values for each feature in each class. Then calculate the final
# probability for each class. The class is determined by selecting the class with the
# higher predicted value.
for dataset in test_data:
    class_0_test_prob = class_0_prob
    class_1_test_prob = class_1_prob
    
    for word in dataset[2]:
        class_0_test_prob *= class_0_feature_likelihood[word]
        class_1_test_prob *= class_1_feature_likelihood[word]

    if(class_1_test_prob > class_0_test_prob):
        predictions[dataset[0]] = 1


#==========================================================================================
# CALCULATE ACCURACY OF TEST DATA
#==========================================================================================
# Iterate through the predicted classes and compare them to the given true values to
# determine if the predicted class is correct or not. Then, determine the accuracy by
# dividing the correct predictions by the total predictions.
#------------------------------------------------------------------------------------------
total_predictions = len(predictions)
correct_predictions = 0

for key, value in predictions.items():
    if(value == true_values[key]):
        correct_predictions += 1

accuracy = 100 * (correct_predictions / total_predictions)


#==========================================================================================
# PRINT OUTPUT AS REQUIRED IN BRIEF
#==========================================================================================
# A series of print statements used to output the desired information as specified in the
# brief. Uses formatting to produce a neat table of feature likelihoods.
#------------------------------------------------------------------------------------------
print("Prior probabilities")
print("class 0 = ", class_0_prob)
print("class 1 = ", class_1_prob)

print("\nFeature likelihoods")
header_format = "{:<9}" + "{:^11}" * len(word_list)
row_format = "{:<9}" + "{:^11.4f}" * len(word_list)
    
print(header_format.format("", *word_list))
print(row_format.format("class 0", *class_0_feature_likelihood.values()))
print(row_format.format("class 1", *class_1_feature_likelihood.values()))

print("\nPredictions on test data")
print("d5 = ", predictions["d5"])
print("d6 = ", predictions["d6"])
print("d7 = ", predictions["d7"])
print("d8 = ", predictions["d8"])
print("d9 = ", predictions["d9"])
print("d10 = ", predictions["d10"])

print("\nAccuracy on test data = {:5.4f}%".format(accuracy))


#==========================================================================================
# SUMMARY OF RESULTS
#==========================================================================================
# The predicted values which were incorrect were for d9 and d10, as they should have been
# class 1 (negative comments). The reason I think these were predicted wrong was down to
# having such a small sample size for the training data. There were fewer training
# documents with class 1 (negative), producing results leaning more towards a positive
# prediction due to the greater variety of words used in different senses.
#
# 'Movie' is used only in positive documents and 'sad' is used once in both positive and
# negative, resulting in positive prediction being made for d9. 'Just' is only used in
# positive documents and 'boring' is only used once in a negative document but the prior
# probability causes the prediction to fall just a bit closer to being positive than
# negative.
#------------------------------------------------------------------------------------------


