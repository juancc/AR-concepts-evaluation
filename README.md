# Intro
2013 Research project aimed to get the users aesthetical perception feedback of a product design concept.

The current used software by designers allows creating images or videos that can be later shown to users to get a feedback but not in a personalized way, in which a user can interact with its own context of use, and neither in an entire experience of use will be given. Additionally the previously proposed application allows to consider one context that can useful when the designer is working with techniques such as personas (Martin, Hanington, & Hanington, 2012) , in which an imaginary user is created based on what the brand wants to reflect, or cultural probes where some representative user are interviewed and general characteristicsare generated and used as inputs to the design process.

However is not made (the color application) to consider several users with different contexts and lightings and somehow the decision is left to the designer criteria, although it gives more information about the relation between product and context.

The  AR system brings the concepts to the user context, which emulates the context lighting over the concept and asks for the user’s perception through a semantic differential,previously defined by the designer. Finally the feedback(differential semantic, context colors and lighting) from several users is send to the server in which the information can be visualized through a web page.
The proposed system is composed by two parts:
1. Target user application: the potential users of the product will evaluate according to how the concept looks in their context using the application in an Android mobile device, and send that information to the server.
1. Server: It hasthe next main functions:
    -  Storage of the projects that can be uploaded by the designers via HTTP through a web page.
    - Receives via Internet the feedback of the concepts
    - Deploy a web page in which different consults can be made.

# Users perception of products concept 
The decision-making activities are crucial throughout the whole design process in order to achieve success with the final product, though; nowadays, there is a limited range of technological tools available to support designers, especially at the earlier stages of the process (Ye J. , Campbell, Page, & Badni, 2006), even when what is done at the earlier stages affects to a larger extent the quality, cost, time and success of a product in the market (Baxter, 1995). In addition to this, the cost of fixing errors or making changes to a design increases drastically as the process advances in the product lifecycle (Ulrich & Eppinger, 2004).


# Android application
## Libraries
The application is developed in eclipse and for the graphical requirements is used Processing for android available in http://processing.org/

Also the required libraries are found in the libs folder.


# Server