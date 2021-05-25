# Dutch Solitaire

## Overview
This is a Java graphics program that implements the game Dutch Solitaire. The game is played on 
a 4 x 14 grid of deck cards. The last column of the grid is 
made up of the aces in the deck. 4 slots are "empty", denoted by an empty yellow face card.
The rest of the 48 slots are made of the remaining cards found
in a standard deck, placed in random order. 

## Objective
The object of the game is to swap cards around until
each row is ordered in ascending order (according to the face value of the cards). Additionally,
each row can only contain 1 suit, determined by the ace card in the row. For example, if the 
last card in the first row is a "diamond", that first row must be filled only with diamonds.
The first column of the grid should be the empty, yellow face, cards in order to win.

## Rules
1. One of the cards to be swapped has to be the empty card
2. Cards with a face value of 2 can be swapped to the first column, as long as there is an 
empty card in that first column position.
3. Aces cannot be moved
4. In any other case, the swap must be either a left order or right order swap, and its new location must either be 
   to the left of a card with the same suit, and the next highest card value (i.e. 4 to the left of 5) OR to 
   the right of a card with the same suit, and a card value right below it (i.e. a 4 to the right of 3)
   1. Left order - in order to move a 4 of hearts, an empty face card must be located to the left
      of a 5 of hearts
      OR
   2. Right order - in order to move a 4 of hearts, an empty face card must be located to the right
      of a 3 of hearts. 