#Name : Alex Ebbage
#ID: 1504283
SET STORAGE_ENGINE = INNODB;
USE world_news_corp_cms;

## QUERY 1
SELECT 
Author_FName AS Forename, 
Author_LName AS Surname, 
Author_Age AS Age
FROM
Author;

## QUERY 2
SELECT
AVG(Author_Age) AS 'Average Male Age'
FROM
Author
WHERE
Author_Gender = 'Male';

## QUERY 3
SELECT
Author_Fname AS Forename,
Author_Lname AS Surname,
Author_Salary AS Salary
FROM
Author
WHERE
Author_Salary = (SELECT MAX(Author_Salary) FROM Author);

## QUERY 4
SELECT
Author_FName AS Forename,
Author_LName AS Surname
FROM
Author
WHERE
Author_Written = 'No';

## QUERY 5
SELECT
Article_Author_Title AS Title,
Article_Author_Content AS Article,
Article_Author_Fname AS 'Author Forename',
Article_Author_Lname AS 'Author Surname'
FROM
Article_Author
WHERE
Article_Author_Content LIKE '%George Clooney%'
OR Article_Author_Content LIKE '%David Cameron%';