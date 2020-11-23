# User Manual

SYSC 3110 - Software Development Project - Term Project

Author: Tanner Trautrim

## Design Decisions

### Class Functions

The GameBoard is used to minimize the coupling between Actions, Continent, and territory.
The GameBoard handles the management, creation, and usage of the Continents and Territories.
It acts as an intermediate to simplify the code and interactions.

GameActions performs all the interactions between the player and the game board.
This allows the player to be decoupled from the system and to be used as a structure.

Player stores basic information for example name and army count.
This information is only accessible to the Actions class.

Territory stores information about each territory.
This data is only accessible to the GameBoard class.

Continent stores information about each territory.
This data is only accessible to the GameBoard class.

Risk is used for the GUI and handles all the interactions involving the GUI

RiskView is used to update the GUI state after a GameAction occurs

RiskEven is a storage class to handle passing events.

AITurn is used to perform the functions of the AI.

InitializeGame is used to create the game.

### Reasoning

This is done to reduce the coupling between systems.
This increased the Cohesion be allowing each class to handle one job.
Support the encapsulation of each method.
The encapsulation allow us to freely refactor code without worrying about how the other classes us them.
There is also reduced knowledge about the other classes.
You can refer to the uml class diagram to reference how the classes connect in a meaningful and reduced way.

## Variable Storage

In this project we used two different ways to store lists of information.
This was done using ArrayList and HashMap.

ArrayList was used when the access time of was of no importance.
For example, when the data will only be iterated through.
ArrayList was used to store the randomized list of territories because it only needed to be accessed iteratively. 

HashMap was used when there was a high access rate of the data.
For example, when the data will need to be called upon often.
HashMap is used to store territories because the O(1) access time with the key.
Territories' are the most frequently accessed methods.

Model is used to store information for JList in a way that updates the GUI.