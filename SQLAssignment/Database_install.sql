#Name : Alex Ebbage
#ID: 1504283
SET STORAGE_ENGINE = INNODB;
DROP DATABASE IF EXISTS world_news_corp_cms;
CREATE DATABASE world_news_corp_cms;
USE world_news_corp_cms;

##########
# TABLES #
##########

## AUTHORS
CREATE TABLE Author (
	Author_FName	VARCHAR(30) NOT NULL,
	Author_LName	VARCHAR(30) NOT NULL,
	Author_NI		BIGINT UNSIGNED NOT NULL,
	Author_Gender	ENUM('Male', 'Female') NOT NULL,
	Author_Age		Int(3) NOT NULL,
	Author_Salary	DECIMAL(8,2),
	Author_Country	VARCHAR(30) NOT NULL,
    Author_Written  ENUM('Yes', 'No') NOT NULL,
    
	PRIMARY KEY (Author_NI)
);

## ARTICLE_AUTHOR
CREATE TABLE Article_Author (
	Article_Author_Title 	VARCHAR(100) NOT NULL,
	Article_Author_Content 	VARCHAR(250) NOT NULL,
	Article_Author_Fname 	VARCHAR(30) NOT NULL,
	Article_Author_Lname 	VARCHAR(30) NOT NULL,
    
	PRIMARY KEY (Article_Author_Title)
);

## ARTICLES
CREATE TABLE Article (
	Article_Title	VARCHAR(100) NOT NULL,
	Article_Content	VARCHAR(250) NOT NULL,
	Article_Country	VARCHAR(30) NOT NULL,
	Article_Topic	ENUM('Financial', 'Politics', 'Sport', 'Science','Entertainment') NOT NULL,
	Article_Date	DATE NOT NULL,
    
	PRIMARY KEY (Article_Title)
);

## LIKES
CREATE TABLE Likes (
	Likes_Time	TIME NOT NULL,
    Article_Title VARCHAR(100) NOT NULL,
    
    FOREIGN KEY (Article_Title) REFERENCES Article(Article_Title)
);

## COMMENTS
CREATE TABLE Comments (
	Comments_Username	VARCHAR(30) NOT NULL,
	Comments_Content	VARCHAR(100) NOT NULL,
	Comments_Post_Date	DATE NOT NULL,
	Comments_Post_Time	TIME NOT NULL,
    Article_Title		VARCHAR(100) NOT NULL,
    
	PRIMARY KEY (Comments_Username),
    FOREIGN KEY (Article_Title) REFERENCES Article(Article_Title)
);


########
# DATA #
########
# AUTHOR DATA
INSERT INTO Author values('Adam', 'Zapel', 1987234920, 'Male', 34, 20000, 'England', 'Yes');
INSERT INTO Author values('Jack', 'Pott', 9348098234, 'Male', 32, 32000, 'Scotland', 'Yes');
INSERT INTO Author values('Joe', 'King', 4564547547, 'Male', 23, 14000, 'India', 'No');
INSERT INTO Author values('Matt', 'Tress', 5654674575, 'Male', 45, 24000, 'India', 'No');
INSERT INTO Author values('Orson', 'Carte', 5675675675, 'Male', 56, 40000, 'Ireland', 'Yes');
INSERT INTO Author values('Warren', 'Peace', 7775656765, 'Male', 33, 19000, 'England', 'Yes');
INSERT INTO Author values('Anne', 'Teak', 8964334324, 'Female', 18, 20000, 'America', 'No');
INSERT INTO Author values('Anna', 'Conda', 6612355667, 'Female', 19, 49000, 'France', 'No');
INSERT INTO Author values('Cara', 'Van', 3456456457, 'Female', 42, 32000, 'France', 'Yes');
INSERT INTO Author values('Gail', 'Force', 1875432325, 'Female', 53, 34000, 'America', 'No');
INSERT INTO Author values('Helen', 'Back', 1134576877, 'Female', 37, 20000, 'England', 'Yes');
INSERT INTO Author values('Hazel', 'Nutt', 2345457654, 'Female', 43, 34000, 'Indonesia', 'Yes');

# AUTHOR_ARTICLE DATA
INSERT INTO Article_Author values('Europe crackdown on jihadist network', 'Police target 17 people in raids in several European countries on suspicion of links to a jihadist network.', 'Adam', 'Zapel');
INSERT INTO Article_Author values('Modi visit historic opportunity for UK', 'Indian prime minister arrives in the UK for a three day visit, described by David Cameron as a historic opportunity for both countries.', 'Jack', 'Pott');
INSERT INTO Article_Author values('Sweden brings in migrant border checks', 'Sweden brings in temporary border checks to control the flow of migrants into the country, as an EU Africa summit continues.', 'Orson', 'Carte');
INSERT INTO Article_Author values('Apple apologises after racism outcry', 'Apple apologises to six schoolboys who were asked to leave one of their shops in Australia, in what the students described as a racist incident.', 'Warren', 'Peace');
INSERT INTO Article_Author values('HMRC reveals tax office shake-up', 'The UKs tax authority will close 137 local offices and replace them with 13 regional centres, raising fears over job losses.', 'Helen', 'Back');
INSERT INTO Article_Author values('Film star visits cafe for homeless', 'Hollywood star George Clooney visits a sandwich shop which helps homeless people during a visit to Edinburgh.', 'Adam', 'Zapel');
INSERT INTO Article_Author values('Rolls-Royce shares dive on profit woes', 'Shares in aerospace group RollsRoyce sink after it warns that its profits will be hit by sharply weaker demand.', 'Helen', 'Back');
INSERT INTO Article_Author values('Ex-MPs GBP13,700 on shredding and skips', 'The Independent Parliamentary Standards Authority releases expenses claims for 182 MPs who left the Commons at the election - with GBP705,000 spent on closing down their offices.', 'Helen', 'Back');
INSERT INTO Article_Author values('Action needed to protect UK coast', 'The UK is ignoring known risks of flood and erosion at the coast and immediate action is needed to manage the threats, the National Trust warns.', 'Cara', 'Van');
INSERT INTO Article_Author values('Venus twin excites astronomers', 'Astronomers hunting distant worlds say they have made one of their most significant discoveries to date, what could be a kind of hot twin to our Venus.', 'Hazel', 'Nutt');

# ARTICLE DATA
INSERT INTO Article values('Europe crackdown on jihadist network', 'Police target 17 people in raids in several European countries on suspicion of links to a jihadist network.', 'England', 'Politics', Current_Time());
INSERT INTO Article values('Modi visit historic opportunity for UK', 'Indian prime minister arrives in the UK for a three day visit, described by David Cameron as a historic opportunity for both countries.', 'India', 'Politics', Current_Time());
INSERT INTO Article values('Sweden brings in migrant border checks', 'Sweden brings in temporary border checks to control the flow of migrants into the country, as an EU Africa summit continues.', 'Sweden', 'Politics', Current_Time());
INSERT INTO Article values('Apple apologises after racism outcry', 'Apple apologises to six schoolboys who were asked to leave one of their shops in Australia, in what the students described as a racist incident.', 'Australia', 'Financial', Current_Time());
INSERT INTO Article values('HMRC reveals tax office shake-up', 'The UKs tax authority will close 137 local offices and replace them with 13 regional centres, raising fears over job losses.', 'England', 'Financial', Current_Time());
INSERT INTO Article values('Film star visits cafe for homeless', 'Hollywood star George Clooney visits a sandwich shop which helps homeless people during a visit to Edinburgh.', 'Scotland', 'Entertainment', Current_Time());
INSERT INTO Article values('Rolls-Royce shares dive on profit woes', 'Shares in aerospace group RollsRoyce sink after it warns that its profits will be hit by sharply weaker demand.', 'England', 'Financial', Current_Time());
INSERT INTO Article values('Ex-MPs GBP13,700 on shredding and skips', 'The Independent Parliamentary Standards Authority releases expenses claims for 182 MPs who left the Commons at the election - with GBP705,000 spent on closing down their offices.', 'England', 'Politics', Current_Time());
INSERT INTO Article values('Action needed to protect UK coast', 'The UK is ignoring known risks of flood and erosion at the coast and immediate action is needed to manage the threats, the National Trust warns.', 'England', 'Science', Current_Time());
INSERT INTO Article values('Venus twin excites astronomers', 'Astronomers hunting distant worlds say they have made one of their most significant discoveries to date, what could be a kind of hot twin to our Venus.', 'America', 'Science', Current_Time());

# LIKES DATA
INSERT INTO Likes values(Current_Time(), 'Europe crackdown on jihadist network');
INSERT INTO Likes values(Current_Time(), 'Europe crackdown on jihadist network');
INSERT INTO Likes values(Current_Time(), 'Europe crackdown on jihadist network');
INSERT INTO Likes values(Current_Time(), 'Sweden brings in migrant border checks');
INSERT INTO Likes values(Current_Time(), 'Modi visit historic opportunity for UK');
INSERT INTO Likes values(Current_Time(), 'Apple apologises after racism outcry');
INSERT INTO Likes values(Current_Time(), 'Apple apologises after racism outcry');
INSERT INTO Likes values(Current_Time(), 'HMRC reveals tax office shake-up');
INSERT INTO Likes values(Current_Time(), 'Film star visits cafe for homeless');
INSERT INTO Likes values(Current_Time(), 'Film star visits cafe for homeless');
INSERT INTO Likes values(Current_Time(), 'Film star visits cafe for homeless');
INSERT INTO Likes values(Current_Time(), 'Film star visits cafe for homeless');
INSERT INTO Likes values(Current_Time(), 'Rolls-Royce shares dive on profit woes');
INSERT INTO Likes values(Current_Time(), 'Rolls-Royce shares dive on profit woes');
INSERT INTO Likes values(Current_Time(), 'Ex-MPs GBP13,700 on shredding and skips');
INSERT INTO Likes values(Current_Time(), 'Ex-MPs GBP13,700 on shredding and skips');
INSERT INTO Likes values(Current_Time(), 'Action needed to protect UK coast');
INSERT INTO Likes values(Current_Time(), 'Action needed to protect UK coast');
INSERT INTO Likes values(Current_Time(), 'Action needed to protect UK coast');
INSERT INTO Likes values(Current_Time(), 'Action needed to protect UK coast');
INSERT INTO Likes values(Current_Time(), 'Action needed to protect UK coast');
INSERT INTO Likes values(Current_Time(), 'Venus twin excites astronomers');
INSERT INTO Likes values(Current_Time(), 'Venus twin excites astronomers');
INSERT INTO Likes values(Current_Time(), 'Venus twin excites astronomers');
INSERT INTO Likes values(Current_Time(), 'Venus twin excites astronomers');

# COMMENTS DATA
INSERT INTO Comments values('Boogie123', 'You are so inspiring!' , Current_Date(), Current_Time(), 'Europe crackdown on jihadist network');
INSERT INTO Comments values('TotalBiscuit', 'Revolutionary work you have here.' , Current_Date(), Current_Time(), 'Europe crackdown on jihadist network');
INSERT INTO Comments values('JesseCox', 'Incredible!! I like the use of layout and pattern!' , Current_Date(), Current_Time(), 'Sweden brings in migrant border checks');
INSERT INTO Comments values('FrankieIn1080p', 'Great news, its about time!' , Current_Date(), Current_Time(), 'Sweden brings in migrant border checks');
INSERT INTO Comments values('PressHeartToContinue', 'This is a fantastic oppurtunity, we should welcome it.' , Current_Date(), Current_Time(), 'Modi visit historic opportunity for UK');
INSERT INTO Comments values('Sark', 'Its great that our 2 nations are keeping the past behind them!' , Current_Date(), Current_Time(), 'Modi visit historic opportunity for UK');
INSERT INTO Comments values('ChilledChaos', 'Outrageous, do they really think they can get off so easily?' , Current_Date(), Current_Time(), 'Apple apologises after racism outcry');
INSERT INTO Comments values('Smarty8342', 'Seems to be the usual rubbish.' , Current_Date(), Current_Time(), 'Apple apologises after racism outcry');
INSERT INTO Comments values('LastStandGamers', 'I think this is being blown way out of proportion.' , Current_Date(), Current_Time(), 'Apple apologises after racism outcry');
INSERT INTO Comments values('Strider743', 'Typical, big companies getting let off the hook again.' , Current_Date(), Current_Time(), 'Apple apologises after racism outcry');
INSERT INTO Comments values('LordOfTheMemes', 'How can this drivel matter to anyone?!' , Current_Date(), Current_Time(), 'Film star visits cafe for homeless');
INSERT INTO Comments values('Big1337Trol', 'What a kind soul! Inspiring!' , Current_Date(), Current_Time(), 'Film star visits cafe for homeless');
INSERT INTO Comments values('Anonymous24512', 'Very interesting, provides a great insight to the inner workings of the company.' , Current_Date(), Current_Time(), 'Rolls-Royce shares dive on profit woes');
INSERT INTO Comments values('Glorfindel666', 'People are over reacting to this information, like always.' , Current_Date(), Current_Time(), 'Ex-MPs GBP13,700 on shredding and skips');
INSERT INTO Comments values('Matrix10101', 'This happens all the time, nothing unusual about it.' , Current_Date(), Current_Time(), 'Ex-MPs GBP13,700 on shredding and skips');
INSERT INTO Comments values('SeaNanners', 'Probably the usual lies theyre feeding us!' , Current_Date(), Current_Time(), 'Ex-MPs GBP13,700 on shredding and skips');
INSERT INTO Comments values('GeekAndSundry', 'Sleek work you have here.' , Current_Date(), Current_Time(), 'Action needed to protect UK coast');
INSERT INTO Comments values('LindseyStirling', 'This article has navigated right into my heart.' , Current_Date(), Current_Time(), 'Action needed to protect UK coast');
INSERT INTO Comments values('Malukah', 'Hugely sublime work, friend.' , Current_Date(), Current_Time(), 'Action needed to protect UK coast');
INSERT INTO Comments values('DropThePoptart', 'Highly thought out! Ahhhhhhh...' , Current_Date(), Current_Time(), 'Venus twin excites astronomers');

### This script ends here
SELECT * FROM Author;
SELECT * FROM Article;
SELECT * FROM Likes;
SELECT * FROM Comments