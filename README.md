expanded_needham_schroeder
==========================

implementation of an expanded needham-schroeder protocol in java


CS 5958/6490: Network Security – Fall 2013 
Programming Assignment 1 
Due by 11:59:59 PM MT on October 28th 2013 
 
Important: 
• No late submissions will be allowed so please plan well ahead of the due date. 
• Total Points for this Programming Assignment: 100 
 
The goal of your programming assignment is to build the expanded Needham Schroeder 
Mediated-Authentication Scheme (Figure 11-19 from the Perlman book). You need to 
write socket programs that run on three nodes (can be three processes on the same 
machine), Alice, Bob, and the KDC. You must use one of C, C++, or Java for your 
programs. 
 
Assume that Alice initiates the authentication exchange. Please ensure the following. 
• The challenges are at 64 bits long. 
• The secret key encryption scheme is 3DES. 
• You need to set up two keys for each 3DES based secure communication between 
two parties (Alice and KDC, Bob and KDC, Alice and Bob). 
• Use a unique number for identifying a user instead of IP addresses and port numbers. 
• Read sections 11.5 and 11.6 from the book before choosing values of the various N’s 
in Figure 11-19. 
 
When the initial two-message handshake is not used, and when NB is removed from the 
ticket, the extended version of Needham Schroeder reduces to the original version 
(Figure 11-18). For the original version of Needham Schroeder scheme first use the 
Electronic Code Book (ECB) for encrypting multiple blocks and demonstrate how Trudy 
is successful in impersonating Alice by causing a reflection attack. Remove this 
vulnerability by using Cipher Block Chaining (CBC) instead of ECB. In creating the 
reflection attack, you can assume that the information that Trudy needs to eavesdrop is 
available to her (i.e., you can make that information available to Trudy in your program, 
you do not need to sniff that information in real time). 
 
Include print commands in your code to show 
• one successful authentication (extended Needham Schroeder), 
• the reflection attack (original Needham Schroeder), and 
• the difference in CBC vs ECB outputs for the last two messages (original Needham 
Schroeder). 
Using the handin utility (cs5958 students should also use cs6490), electronically turn in 
your code along with the output files, and a readme file. The readme file should explain 
how the code is organized. Your code should be well commented. 5 points will be 
deducted for lack of comments. For this assignment you could take the help of existing 
tools such as Openssl for 3DES related functions. While you can use any computer to 
work on your programming assignment, make sure that it runs on the CADE lab 
machines. We cannot grade any programming assignments that do not run on the CADE 
lab machines. 
