# Java-TicTacToe-GUI  

A **Java Swing-based Tic-Tac-Toe game** with an interactive graphical interface and a built-in **computer AI opponent**. This project provides a fun, easy-to-use desktop application where a human player competes against a simple yet smart AI that tries to win or block strategically.  

---

## Overview  

This desktop game recreates the classic **Tic-Tac-Toe (Noughts & Crosses)** experience in a sleek GUI.  
The player always plays as **X**, while the computer AI plays as **O**.  

The AI follows a **priority-based strategy**:  
- First tries to **win immediately**  
- If not possible, **blocks the opponent**  
- If no critical move exists, picks **center, then corners, then random cells**  

This project demonstrates **event-driven programming**, **AI decision-making**, and a clean separation of **GUI and game logic** using Java Swing.  

---

## Key Features  

### 1. Interactive GUI  
- **3×3 Grid Board** with large, clear symbols.  
- **Status Label** indicates whose turn it is or the final game result.  
- **New Game Button** to reset the board and start a new round anytime.  

### 2. AI Opponent with Basic Strategy  
- **Winning Move Detection**: AI looks for an immediate win.  
- **Blocking Player Moves**: Prevents the human player from winning.  
- **Strategic Move Preference**: Chooses center > corners > random moves.  

### 3. Game Logic  
- Detects **winning conditions** (rows, columns, diagonals).  
- Declares **winner**, **tie**, or allows the game to continue.  
- Disables further moves once the game is over.  

### 4. Smooth Gameplay  
- AI moves after a **short delay** for a natural experience.  
- **Blue X** and **Red O** for better visibility.  

---

## Why It Stands Out  

- ✅ **No Dependencies**: Built purely in Java Swing  
- ✅ **Smart AI**: Blocks and tries to win strategically  
- ✅ **Beginner-Friendly**: Easy to read, extend, and customize  
- ✅ **Cross-Platform**: Works on Windows, macOS, and Linux  

---

## System Architecture  

### Main Components  

- **`TicTacToeGUI`**  
  Handles GUI, event listeners, and player interaction.  

- **`TicTacToe`**  
  Core game logic that manages board state, win/tie detection, and move validity.  

- **`Computer`**  
  AI logic that selects the next best move for the computer player.  

### Design Approach  

- **Event-Driven Programming**: Button clicks trigger move logic.  
- **Separation of Concerns**: GUI is independent of core game logic.  
- **Randomized AI**: Adds unpredictability when no strategic move is required.  

---

## Usage  

### How to Play  
1. **Run the game**.  
2. **Player (X)** moves first by clicking any empty cell.  
3. The **computer (O)** will move automatically after a short delay.  
4. The game ends when:  
   - Player wins  
   - Computer wins  
   - Or it’s a tie  
5. Click **New Game** to restart the game anytime.  

---

## Requirements  

- **Java 8 or higher**  
- No external libraries needed  

---
## License

This project is for academic and institutional use. Please credit the developers if reused or modified for deployment.


## Developed By
BISMA SHAHID  
Department of Software Engineering  
FAST NUCES KHI


