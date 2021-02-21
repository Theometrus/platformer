The style used for this assignment was Google's Java Style, found here: https://google.github.io/styleguide/javaguide.html

The game runs on default settings through gradle build/run. 
The name of a JSON file containing configs can be changed in the App.java file (The format is /<nameOfMyJSONFile>.json, if the file is located in the "resources" folder).
The default JSON file is sampleLevel.json.
Most of the level can be built from the JSON file. It contains fields for platform and enemy positions, finish flag position, etc.
*IMPORTANT:* ALL numbers input into the JSON config file MUST be doubles!

Some assets were kindly provided by Kenney at https://www.kenney.nl/assets

*Please note:* upon running gradle test, a series of jump sounds will play. This is normal and linked to the fact that the program is testing the hero's jumping (as well as events such as landing on top of enemies). A test report is generated within gradle's build/reports folder.
*The additional feature mentioned in the spec:* I chose to implement the suggestion of having enemies have different movement patterns. I've included two using the Strategy pattern. Blue enemies patrol areas with a set speed while pink ones are erratic and unpredictable (random) with their movement.