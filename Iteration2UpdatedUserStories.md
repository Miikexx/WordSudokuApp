Arman Lodhra, 
Hussain Athaullah, 
Riley Lipp, 
Mike Kim, 

February 23, 2023

Hotel Iteration 2 User Story Updates from Iteration 1

Please refer to iteration 1 for diagrams relating to these user stories. 



User Story 1 is to be implemented in iteration 3. The functionality of the user story will remain the same. 

As a Sudoku novice, I want the option for hints to solve the puzzle because I may get frustrated solving difficult puzzles.

1. If the user clicks on the Puzzle Hint button (located in the hint menu accessible from the top left icon of the game board), and then they click on the “Spaces To Fill Button” button, then the user has a choice on how many grid spaces they would like filled in. After selecting how many they want to be filled, the user hits the “Fill in Random Spaces” button. 
2. In response to the user selecting the “Fill In Random Spaces” button, the system will fill in random grid spaces depending on how many spaces the user selected.



User Story 2 is implemented as an activity but instructions for the game are yet to be made. The functionality still remains the same. 

As a Sudoku novice, I would like a how-to-play page so that I would have an idea of how to go about completing the game.

1. The user may view the rules of the game upon opening the application, where they will see a How To Play button on the homepage. If they forget the rules during the game, they can navigate toward the settings menu in the top right where the same How To Play button will appear.
2. In response to the user selecting this button, a page will appear explaining the rules and how to win the game.


User Story 3 is to be implemented. We will implement this in iterations 3 or 4 with the same functionality. 
As a Sudoku expert, I would like to have a range of difficulty levels, so that I can progressively challenge myself to get better at the game. 

1. The visual state of the application during the game board may have fewer or more words filled in depending on the difficulty chosen. There will also be a lives counter underneath the board which may vary depending on the difficulty selected. The peaceful mode will have unlimited lives, easy will have 7 lives, the medium will have 5 lives, hard will have 3 lives and hardcore will have 1 life. 
2. Whenever a user decides to start a new game, they will have to configure some settings, including choosing the difficulty. 
3. In response to the user selecting their difficulty level, the system will generate a board filling in a predetermined number of word pairs depending on the difficulty level chosen. The number of words filled in will be higher if the user decides to select an easier game difficulty. The user will also have a certain amount of lives (attempts) depending on the difficulty chosen. 



User Story 4 is to be implemented. It will be implemented in iteration 3 with the same functionality. 

As someone who is just starting to learn a new language, I want the option to see the translation of certain words because I may get frustrated solving puzzles with words I do not know.

1. If during the game the user clicks on the Word Hint button (located in the hint menu accessible from the top left icon of the game board), then a list of the words the user can fill in appears. If the user clicks on one of the words, the translation of the word appears.
2. If the user clicks the Switch Language button, then the list of words will instead display the words that started on the grid. Clicking the button again will swap back.
3. If the user clicks on the word in the Word Hint menu, then clicks on the Display During Game button, the buttons to select a word to place on the grid change into a button displaying both the English and translated version of that word. The button may still be used to place words on the grid.



User Story 5 is to be implemented. It will be implemented in iterations 3 or 4 with the same functionality. 

As an intermediate language learner, I would like the ability to listen to music while I play the game so that I feel more like I’m having fun while learning.

1. This will have no significant change in the application or screen. Instead, there will be an option in the settings menu to turn the music on or off.
2. The user may navigate through the settings menu to find the Music button, allowing the user to turn the music on or off. The settings menu can be accessed through the top right button in the game screen or while configuring their settings for the game. 
3. If the user elects to turn the music off, the system will mute the music. If they later decide to turn it back on, the system will unmute the music. The system will play the music by default upon loading up the application.



User Story 6 is to be implemented. This will be implemented in iteration 3 with the same functionality. 

As an advanced language learner, I would like to be able to track how quickly I can solve puzzles to see if I am getting quicker at learning my desired language.

1. Below the game board, a game clock will appear, starting at 0 minutes and 0 seconds. It will keep incrementing by one second until the game is complete.
2. The game clock will only start when the user starts the game and will otherwise not stop unless the user ends or finishes the game.
3. In response to the user starting any game, the system will appropriately set up the game along with a game clock. 



User Story 7 is to be implemented in iteration 4 with the same described functionality. 

As a language teacher, I want to see all available words in the word bank along with their English translation so that I know what exactly my students will be learning.

1. At any point before the game, the user will be able to click on the Add Words/ See Words button. This will prompt a pop-up screen where one of the options will be a See All Words button. Clicking this opens a pop-up screen displaying all words available in the word bank for the given language along with each translation.
2. The user will click on the Add Words/ See Words buttons, select a language, then click on the See All Words button to invoke this command. 
3. In response to the user doing this, the system will reveal the word bank, displaying all words with all translations. 



User Story 8 is to be implemented in iteration 4 with the same described functionality. 

As a language teacher, I want the ability to edit what words appear in the puzzle because I would like the puzzle to go with my teaching.

1. At any point before the game, the user can click on the Add Words/ See Words button. This will prompt a pop-up screen. The user may then type into those blanks an English word and its translation into each pair. 
2. The user will have to select the language of the word they want to add, then enter that word along with its translation. After doing this, the user selects the Add Word button which adds the word to the word bank.
3. In response to this, the system will include the word in the word bank and when the user starts the game, the system will randomly generate words from the word bank to add to the puzzle. This may include the added word.
 


User Story 9 is to be implemented in iteration 4 with the same described functionality. 

As a language teacher, I want a grading system that could be used to keep track of my student's progression, because I want to see who is struggling.

1. At the end of the game, there will be a pop-up screen that gives the user a letter grade.
2. When the user’s game finishes (i.e. when the user wins or loses) the screen will pop up, regardless of what game mode or difficulty is chosen. 
3. In response to the game ending the system will calculate a letter grade using an algorithm involving the time taken to solve, the puzzle difficulty, and the lives remaining. The letter grade will pop up at the end of the game if and only if the user has won or lost the game. The system will do nothing if the user ends the game prematurely. 










