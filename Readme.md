# 8-Puzzle Solver using A* Algorithm

## Overview

This project solves the **8-Puzzle Problem** using the **A* search algorithm**. The puzzle consists of a 3x3 grid of tiles numbered 1 through 8 and one empty space, represented by 0. The goal is to move the tiles around until the puzzle reaches a goal configuration, using the fewest moves.

### Key Features
- **A* Search Algorithm** to solve the puzzle efficiently.
- Uses **Manhattan** and **Hamming** heuristics to guide the search.
- **Detects Unsolvable Puzzles** by inversion counting.
- Displays the sequence of board states from the initial configuration to the goal.

## Files

- **Board.java**: Contains methods to represent the puzzle, calculate Manhattan and Hamming distances, generate neighbors, and check if the puzzle is solvable.
- **Solver.java**: Implements the A* algorithm to solve the puzzle, read input from a file, and output the solution path.
- Input files :  (e.g., puzzle04.txt, puzzle-impossible3x3.txt)

## How to run
### Compile the Code:
```bash
javac Board.java Solver.java
```

### Run the Solver with an Input File:
```bash
java Solver puzzle4x4-06.txt
```




