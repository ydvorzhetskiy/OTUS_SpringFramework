DROP TABLE IF EXISTS author;
CREATE TABLE author(id INT PRIMARY KEY, firstName VARCHAR(255), secondName VARCHAR(255));
DROP TABLE IF EXISTS book;
CREATE TABLE book(id INT PRIMARY KEY, name VARCHAR(255), description VARCHAR(255), id_author INT, id_genre INT);
DROP TABLE IF EXISTS genre;
CREATE TABLE genre(id INT PRIMARY KEY, genreName VARCHAR(255));
