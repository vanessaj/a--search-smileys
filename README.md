# a--search-smileys
# Implementation of A* search on predefined maze.

## Instructions
The user presses the run button in an IDE (this was tested in Eclipse) to begin the program.
The user then follows the instructions in the IDE's console and inputs either 1 or 2.
  * Entering 1 starts the original maze
  * Entering 2 starts the "uneven" maze (the grid is not a rectangle)

The user can then click on the squares in the display of the maze to select starting points. The grid square for the goal position is in green and the maze obstacles are black.
To unselect a square the user simply clicks on the square again to remove it as a starting point.
Once the user has picked all the positions they want they press the "Done" button at the bottom of the screen.

The user then views the maze and can try out the different starting points (indicated in yellow). The console then shows the path and the path's length for each of the starting points. The path is also displayed on the grid in blue.
The user must then try all the possible starting points to determine the winner, which is the starting point with the shortest path. 
After all combinations of starting points have been tried the console displayed the winner by indicating the path's starting position and the path length.

* not really optimized, need to review *
