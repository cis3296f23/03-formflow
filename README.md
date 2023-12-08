# FormFlow
The product that we are developing is a Java application that assists users in filling out application forms. The target customers for this application are tech-savvy professionals who need to save time when filling out forms. The objective of developing this product is to reduce redundancies wherever possible when filling out forms. This can be especially useful in a series of forms that ask for the same information. In broad strokes, the way the application itself will work is first the user uploads the forms they need to fill out. Then FormFlow scans the PDF and presents the user with all the relevant fields that need to be answered by the forms. The user will fill them out in one place on FormFlow and the application will fill out the documents. 

The baseline goal is scanning documents, and presenting their fields, repeated or otherwise to the user to fill out. This way the user fills out their information once to complete a series of forms that ask for the same thing. This will only be a successful project if our product design itself is good. Once we have the mechanics of the application our goal is to make it easy to use. The market is tech-savvy professionals, but it should be useable and understandable for anyone with basic computer knowledge to consider it a success. There are also features we may be able to implement to supplement the application like pre-logged user information, but for the scope of this project that may present security issues we are not equipped to deal with yet. It is something to consider if we have time at the end of the semester, which is unlikely. 

These screenshots run through a FormFlow session in its current version.

![FormFlow Comp1 unfilled](https://github.com/cis3296f23/03-formflow/assets/93154019/40591ca4-184a-48a9-abc1-8bec5dd6dacf)
Blank new session

![FormFlow3](https://github.com/cis3296f23/03-formflow/assets/93154019/5c0059bb-4338-4a2d-b4c1-53690a8c7088)
Pushed Upload button and uploaded a job application and the Eagle Scout workbook

![FormFlow4](https://github.com/cis3296f23/03-formflow/assets/93154019/a067d6db-cb8c-4180-839b-ff09b20b1200)
Checked off the job application and the fields appeared

![FormFlow5](https://github.com/cis3296f23/03-formflow/assets/93154019/34760299-f5a3-414e-9318-dee05fc58db7)
Filled out three random fields and hit generate

![FormFlow6](https://github.com/cis3296f23/03-formflow/assets/93154019/6359424a-37d0-430e-b810-5f5353e69e43)
Opened the downloaded filled out file and found the fields where I input data

![image2](https://github.com/cis3296f23/03-formflow/assets/93154019/0dd3463b-fa9f-44cd-9010-3e4bdd81c49d)
This is what component 2 is supposed to look like

# How to run
- Unfortunately the JAR file still does not work on this release
- Get the project running on your machine by cloning our Github Repository

# How to contribute
Follow this project board to know the latest status of the project: [http://...]([http://...])  
Be sure to create your own branch using your name or Github user name so we know who is committing what

### How to build
- Use this github repository: ... 
- Specify what branch to use for a more stable release or for cutting edge development.  
- Use InteliJ 11
- You will need to download JavaFX and set up your IDE to use it. You can find specific instructions on how to do that here: https://openjfx.io/
- The file to run when you set everything up is Main 
- The app will display text in the output box of your IDE and it will open a JavaFX built UI module where all of FormFlow's action happens
