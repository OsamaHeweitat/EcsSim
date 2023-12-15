I attempted (and hopefully succeeded in) all parts of the coursework.
My program runs with the normal coursework command line arguments e.g. java EcsSim staff.txt 2000 50
(java EcsSim <staff config file> <starting budget> <number of years to sim>)


Extensions:
1.  I added a new type of facility called Recreational (facility).
    There are two new facilities with this type:
        a. Cafeteria (Base Build Cost: 500, Base Profit per Student: 1 coin, Max level: 2)
        b. Gym (Base Build Cost: 650, Base Profit per Student: 3 coins, Max level: 2)
    You can only have one of each per University.
    The purpose of those facilities is to produce extra money. The profits are collected at the simulation stage
    as the money from the students in the coursework is. They are better the earlier on you get them, so I modified
    my algorithm in the "buildAndUpgrade" method in University to prioritise building and upgrading them.
    They are upgradable. The upgrade cost is 1.5 * (build cost if upgrading to level 2 or previous level cost otherwise).
    Upgrading increases the profit per student by 2 coins.
    I wanted to mimic the original buildings from the coursework I did in terms of structure so I created an interface "Recreational"
    and an abstract class implementing it "AbstractRecreational", with "Cafeteria" and "Gym" extending it.
    I modified my relevant methods (e.g. "build" in University, "addFacility" in Estate, etc..) and added new methods in University
    and Estate (e.g. "collectProfits", "getUnbuiltRecreationalTypes", etc..)

2.  I added the option to continue the simulation at the end of the number of years passed in as an argument at
    the start. If the user wishes to continue, they're asked to input the number of years they wish to continue
    for, otherwise input "no".