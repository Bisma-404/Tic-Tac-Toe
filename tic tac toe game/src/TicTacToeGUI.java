import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToeGUI {
    private JFrame frame;
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private TicTacToe game;
    private Computer ai;
    private JLabel statusLabel;

    public TicTacToeGUI() {
        game = new TicTacToe();
        ai = new Computer();

        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(new BorderLayout());

        statusLabel = new JLabel("Your turn (X)", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(statusLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(buttons[i][j]);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton resetButton = new JButton("New Game");
        resetButton.addActionListener(e -> resetGame());
        controlPanel.add(resetButton);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().isEmpty() && currentPlayer == 'X' && !game.isGameOver()) {
                makeMove(row, col, 'X');
                buttons[row][col].setText("X");
                buttons[row][col].setForeground(Color.BLUE);

                if (!game.isGameOver()) {
                    currentPlayer = 'O';
                    statusLabel.setText("Computer's turn (O)");
                    // AI makes move after a short delay
                    Timer timer = new Timer(500, ev -> {
                        ai.makeMove(game, 'O');
                        updateBoard();
                        currentPlayer = 'X';
                        statusLabel.setText("Your turn (X)");
                        checkGameOver();
                    });
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    checkGameOver();
                }
            }
        }
    }

    private void makeMove(int row, int col, char player) {
        game.makeMove(row, col, player);
    }

    private void updateBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char cell = game.board[i][j];
                if (cell != ' ') {
                    buttons[i][j].setText(String.valueOf(cell));
                    buttons[i][j].setForeground(cell == 'X' ? Color.BLUE : Color.RED);
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }

    private void checkGameOver() {
        if (game.isGameOver()) {
            char winner = game.getWinner();
            if (winner == 'X') {
                statusLabel.setText("You win!");
            } else if (winner == 'O') {
                statusLabel.setText("Computer wins!");
            } else {
                statusLabel.setText("It's a tie!");
            }
            disableAllButtons();
        }
    }

    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        game = new TicTacToe();
        currentPlayer = 'X';
        statusLabel.setText("Your turn (X)");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGUI());
    }
}

class TicTacToe {
    protected char[][] board = new char[3][3];

    public TicTacToe() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean isGameOver() {
        return hasWon('X') || hasWon('O') || isTie();
    }

    public boolean hasWon(char player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
                return true;
            }
        }

        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    public boolean isTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public void makeMove(int row, int col, char player) {
        board[row][col] = player;
    }

    public char getWinner() {
        if (hasWon('X')) {
            return 'X';
        } else if (hasWon('O')) {
            return 'O';
        } else {
            return 'T';
        }
    }
}

class Computer {
    private Random rand = new Random();

    public void makeMove(TicTacToe game, char player) {
        // Check for a winning move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.board[i][j] == ' ') {
                    game.makeMove(i, j, player);
                    if (game.hasWon(player)) {
                        return;
                    } else {
                        game.makeMove(i, j, ' ');
                    }
                }
            }
        }

        char opponent = (player == 'X') ? 'O' : 'X';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.board[i][j] == ' ') {
                    game.makeMove(i, j, opponent);
                    if (game.hasWon(opponent)) {
                        game.makeMove(i, j, player);
                        return;
                    } else {
                        game.makeMove(i, j, ' ');
                    }
                }
            }
        }

        if (game.board[1][1] == ' ') {
            game.makeMove(1, 1, player);
            return;
        }

        int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int i = 0; i < 4; i++) {
            int index = rand.nextInt(4);
            int row = corners[index][0];
            int col = corners[index][1];
            if (game.board[row][col] == ' ') {
                game.makeMove(row, col, player);
                return;
            }
        }

        while (true) {
            int row = rand.nextInt(3);
            int col = rand.nextInt(3);
            if (game.board[row][col] == ' ') {
                game.makeMove(row, col, player);
                return;
            }
        }
    }
}