# introsde-2017-assignment-2-client

# identification

Student: Wambo Ariane

E-Mail: ariane [dot] wambometuedjo [at] studenti [dot] unitn [dot] it

Course: Introduction to Service Design and Engineering Project Structure
project

The Client  project consist mainly on one package: introsde.rest.ehealth package with two classes :
  Assignment2Client.java: contains the link which connect to heroku
  ClientParser: a parser of the responses
The task are all the tasks required from the client side in the assignment2 from 1 to 10

# Execution

This project contains a build.xml file which can be run by Apache Ant. It will download Apache Ivy, this latter component will download all required dependencies from the Maven repository.

To compile execute 

       ant execute.client

To execute the server I call :

       ant start

On heroku I first call :https://assign21.herokuapp.com/sdelab/DataBase_init to initialise the database then I go on with https://assign21.herokuapp.com/sdelab/person
