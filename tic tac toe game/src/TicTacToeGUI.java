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
    private JLabel scoreLabel;
    private int playerWins = 0;
    private int computerWins = 0;
    private int ties = 0;

    public TicTacToeGUI() {
        game = new TicTacToe();
        ai = new Computer();

        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Your turn (X)", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        statusLabel.setForeground(new Color(50, 50, 150));
        headerPanel.add(statusLabel, BorderLayout.NORTH);

        scoreLabel = new JLabel("You: 0 | Computer: 0 | Ties: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(80, 80, 80));
        headerPanel.add(scoreLabel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Segoe UI", Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(new Color(100, 150, 255), 2));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(buttons[i][j]);
            }
        }

        mainPanel.add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton resetButton = new JButton("New Game");
        resetButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resetButton.setBackground(new Color(0, 51, 102));
        resetButton.setForeground(Color.WHITE);
        resetButton.setOpaque(true);
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(e -> resetGame());

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exitButton.setBackground(new Color(0, 51, 102));
        exitButton.setForeground(Color.WHITE);
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        controlPanel.add(resetButton);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(exitButton);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);
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
                buttons[row][col].setForeground(new Color(0, 100, 255));
                buttons[row][col].setBackground(new Color(240, 245, 255));

                if (!game.isGameOver()) {
                    currentPlayer = 'O';
                    statusLabel.setText("Computer's turn (O)");
                    statusLabel.setForeground(new Color(200, 50, 50));
                    setBoardEnabled(false);
                    Timer timer = new Timer(800, ev -> {
                        ai.makeMove(game, 'O');
                        updateBoard();
                        currentPlayer = 'X';
                        statusLabel.setText("Your turn (X)");
                        statusLabel.setForeground(new Color(50, 50, 150));
                        setBoardEnabled(true);
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
                    buttons[i][j].setForeground(cell == 'X' ? new Color(0, 100, 255) : new Color(200, 50, 50));
                    buttons[i][j].setBackground(new Color(240, 245, 255));
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
                playerWins++;
            } else if (winner == 'O') {
                statusLabel.setText("Computer wins!");
                computerWins++;
            } else {
                statusLabel.setText("It's a tie!");
                ties++;
            }
            updateScore();
            disableAllButtons();
            highlightWinningCells();
        }
    }

    private void highlightWinningCells() {
        int[][] winningCells = game.getWinningCells();
        if (winningCells != null) {
            for (int[] cell : winningCells) {
                int row = cell[0];
                int col = cell[1];
                buttons[row][col].setBackground(new Color(220, 255, 220));
            }
        }
    }

    private void updateScore() {
        scoreLabel.setText(String.format("You: %d | Computer: %d | Ties: %d", playerWins, computerWins, ties));
    }

    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void setBoardEnabled(boolean enabled) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(enabled && buttons[i][j].getText().isEmpty());
            }
        }
    }

    private void resetGame() {
        game = new TicTacToe();
        currentPlayer = 'X';
        statusLabel.setText("Your turn (X)");
        statusLabel.setForeground(new Color(50, 50, 150));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(Color.WHITE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {}
            new TicTacToeGUI();
        });
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

    public int[][] getWinningCells() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return new int[][]{{i, 0}, {i, 1}, {i, 2}};
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return new int[][]{{0, j}, {1, j}, {2, j}};
            }
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};
        }

        return null;
    }
}

class Computer {
    private Random rand = new Random();

    public void makeMove(TicTacToe game, char player) {
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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.board[i][j] == ' ') {
                    game.makeMove(i, j, player);
                    return;
                }
            }
        }
    }
}
