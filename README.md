# Music Album Management System(MAMS)
## ✨_The Best System for Music Album Rental_✨
![N|Solid](https://imgur.com/SfsSWqz.png)

## Features
A desktop application system that allows the shop owner to:
- Keep track of available music albums.
- Keep track of rented items.
- Track sales and stock reporting.
- Keep track of the customer details and the related rental history.
- View the summary of the overall popularity of the music albums.

## Installation

MAMS JAVA project requires several jars file to run.
Download them and add them through Project Structure.

| Jars | Website |
| ------ | ------ |
| SQL Connector | https://dev.mysql.com/downloads/connector/j/ |
| JavaFX | https://openjfx.io/openjfx-docs/ |

## Creating schema before connecting to project
1. Go to [mysql website](https://www.mysql.com/products/workbench/) and download MySQL Workbench
2. Look for *CREATE TABLE JAVA MAMS.sql* in the resource folder and run them in the query editor
3. Refresh the table and make sure the tables (i.e. album, album_rental, card, customer, rental) are created
4. Look for *INSERT VALUES JAVA MAMS.sql* in the resource folder and run them in the query editor
5. Make sure the data are inserted. 

## Setup of mySQL database before running
1. Download sql connector jar file in the [official website](https://dev.mysql.com/downloads/connector/j/).

2. Add the jar file into the project structure.

3. Create a sql file and take a look at the right side bar, and proceed to setup the database connection and select 'mySQL' as connection.
_[Only works in IntelliJ Ultimate edition]_

4. Search for 'plugins' in 'help' and download 'DB Navigator' if step 3  do not work)
And setup the initial connection as the article below.
https://stackoverflow.com/questions/55598914/connect-mysql-to-java-via-intellij-idea
_[Works in IntelliJ Community edition]_

5. Go to src folder and open _DatabaseConnection.java_. Look for databaseUser and databasePassword and change to your own credentials.

6. If you still need further guidance on how to connect JAVAFX with MYSQL, you may refer to [this video](https://www.youtube.com/watch?v=whhSR0wlWQY&t=479s).
## _Special Note on Implementation_

| Item | Description |
| ------ | ------ |
| Refresh Data | User need to manually click refresh after insert or update. |
| Search Function | User can only search for IDs, string data type and date or year. |


The entity-relationship diagram consists of 5 tables, which are CUSTOMER, CARD, RENTAL, ALBUM_RENTAL and ALBUM. 
![N|Solid](https://i.imgur.com/grb7yTQ.jpg)

## License

MIT
