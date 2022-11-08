# Intro
2013 Research project aimed to get the users aesthetical perception feedback of a product design concept taking into the account the users context (lighting and background).

The decision-making activities are crucial throughout the whole design process in order to achieve success with the final product, though; nowadays, there is a limited range of technological tools available to support designers, especially at the earlier stages of the process (Ye J. , Campbell, Page, & Badni, 2006), even when what is done at the earlier stages affects to a larger extent the quality, cost, time and success of a product in the market (Baxter, 1995). In addition to this, the cost of fixing errors or making changes to a design increases drastically as the process advances in the product lifecycle (Ulrich & Eppinger, 2004).

The proposed system is composed by two parts:
1. Target user application: the potential users of the product evaluate the product (3d model of it) according to how the concept looks in their context using the application in an Android mobile device, and send that information to the server.
1. Server: Store the design projects that can be uploaded by the designers via HTTP through a web page. Receives via Internet the feedback of the concepts. And deploy a web page in which different consults can be made.

System general architecture:
![Alt text](screenshots/systme_architecture.png?raw=true "System Architecture")


# Users perception of products concept 
To evaluate the aesthetical response of an user towards the product aesthetic, the Semantic Differential (SD) Method is used. The SD is a seven levels scale between two opossitive product descriptors (e.g. Happy-Sad) 


# Android application
## Libraries
The application is developed in eclipse and for the graphical requirements is used Processing for android available in http://processing.org/

Also the required libraries are found in the libs folder.


# Server