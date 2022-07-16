# ContactsApp
A JavaFX application made using MVC pattern. Allows to save and view contacts' data such as their names, phone numbers, e-mails and their personal contacts lists. 

This app was developed as the final project for the Data Structures subject, so it works mainly with two types of data structures: A binary tree to save the user's contacts, and a doubly linked list within each contact, to save the contact's contact list. 

The logic is: There is a binary tree containing all user's contacts. Each node of this tree has a Contact as value. The Contact class has among its attributes a doubly linked list, which nodes have a Contact as value. This is:

Tree --> Tree Node --> Contact --> Linked List --> List Node --> Contact
