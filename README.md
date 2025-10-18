# Towers of Hanoi 1.0.0

![Towers of Hanoi](https://github.com/NeuralCortex/Towers_Of_Hanoi/blob/main/images/towers.png)

## Overview

Towers of Hanoi is a mathematical puzzle game based on a divide-and-conquer approach. The game consists of three stacks with discs of varying sizes arranged in ascending order. The objective is to move all discs from the first stack to the third stack (rightmost), using only the available stacks and ensuring no larger disc is placed on top of a smaller one.

This JavaFX application provides a graphical interface to play and solve the puzzle, supporting up to 10 discs (requiring 1023 steps to solve). The program uses a recursive algorithm to compute the solution, which is displayed in a table within the interface. Users can export the solution to a CSV file for further analysis.

## Features

- Interactive JavaFX interface to visualize the Towers of Hanoi puzzle.
- Recursive algorithm to generate the optimal solution.
- Table displaying all required steps to solve the puzzle.
- Option to export the solution to a CSV file.
- Supports up to 10 discs.

## Technologies Used

This project was developed using the following tools and frameworks:

- **Apache NetBeans 27 IDE**: [NetBeans 27](https://netbeans.apache.org/)
- **Java SDK**: [JDK 24](https://www.oracle.com/java/technologies/javase/)
- **SceneBuilder**: [Gluon SceneBuilder](https://gluonhq.com/products/scene-builder/) for GUI development
- **JavaFX SDK**: [JavaFX](https://gluonhq.com/products/javafx/)

## Usage

1. Start the application to view the Towers of Hanoi interface.
2. Select the number of discs (up to 10).
3. The program will display the steps to solve the puzzle in a table on the right.
4. Use the export feature to save the solution as a CSV file.