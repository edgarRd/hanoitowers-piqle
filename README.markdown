# Hanoi Towers problem implemented with PIQLE Framework

This is a implementation of the very common known problem of the Hanoi Towers. This implementation uses QLearning algorithms in order to learn the correct and optimal behavior of the agent trying to solve the problem.

# How to run the Hanoi Towers implementation using PIQLE

This implementation of the Hanoi Towers is done using the framework for QLearning called PIQLE. It provides a series of algorithms implementations for QLearning as well as many support classes that helps to represent the problem in the way that they can use this algorithms provided by the framework.

## Files description.

build.xml – This file is used for ant compilation / running automation. If you have installed in your computer the Ant tool, you can use this file to run this project. Ej. Compilation:

    honoitowers-piqle$ ant compile

Run:

    honoitowers-piqle$ ant run
    
README.markdown – This files has the same information contained in this document.
src/ - This folder contains all the source files of the project, including the PIQLE framework, as well as the implementation of the hanoitowers/ sources and TestHanoiTowers.java wich are specific to our project.
hanoitowers/ - Folder that contains the files of the Hanoi Towers problem implementation.
HanoiTowersAction.java – Implements an action in the context of the Hanoi Towers problem, which is the movement of a disk of the top of a peg to another peg.
HanoiTowersAgent.java – Extension of the LoneAgent class of the PIQLE framework in order to count the number of actions performed in every episode.
HanoiTowersEnv.java – Implementation of the environment of the Hanoi Towers problem based on the AbstractEnvironment class of the PIQLE framework.
HanoiTowersState.java – Definition of a state in the implementation of the Hanoi Towers problem, which is based on the AbstractState class of the PIQLE framework.
TestHanoiTowers.java – This is the executable file to run the problem implementation. It is default to 4 disks, but this can be changed in the source code, changing line 10 as following:
    
    HanoiTowersEnv environment = new HanoiTowersEnv(4);

To:

    HanoiTowersEnv environment = new HanoiTowersEnv(n);

## Compilation (Not using Ant tool)

NOTE: In order to be able to compile and execute the project, you MUST have a version of java > v1.6, since our implementation uses the .pop() instruction of the LinkedList structure which was introduced in version 1.6 of the java language.
In a terminal window in your OS, enter to the folder src/ and execute the following command to compile the source code:

    src$ javac *.java

This will create all the .class files needed to run the project.

## Running

In order to run the project, being in the src/ folder after compilation process, execute the following command:

    src$ java TestHanoiTowers

This command will execute the main program of the project.

## Default Configurations of the Execution.

The main program of the project contains some default values for the execution of the problem, which can be changed intuitively in the project, such values are the following:
Max. Iterations of the QLearning algorithm: 100, Episodes to run: 50000, Alpha Decay Power: 0.5

Epsilon: Starting in 0.4
