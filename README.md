# Dots and Boxes

## User Input

User inputs the number of rows and columns for the matrix. 

User then inputs the Ply of MinMax algorithm for each player. If 0 ply is entered, a human player is implemented.


When it is a human player's turn, the game displays the board with unique symbols for each dot on the board. 

The game then asks the user to pick two adjacent dots using the symbol system.

## Board Size
With two AIs playing, any board can be used.

Any game with a human can only support words with less than 52 dots. The biggest square board that falls in this threshold is 6x6.

## Heuristic
MinMax algorithm is implemented with one heuristic. 

That is number of boxes created for me minus number of boxes created for my opponent.

## Output
Prints out whose turn it is.
Prints out the board with the new move.
If next player is human, asks user for new input.


