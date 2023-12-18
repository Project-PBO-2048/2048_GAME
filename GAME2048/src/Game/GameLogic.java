package Game;

import Api.Score;

import java.sql.SQLException;
import java.util.Random;

public class GameLogic {

    private static final int WINNING_TILE = 2048;
    private static final int PROBABILITY_2 = 90; // Probability 90% for generating 2

    private int[][] boardsArray;
    private int[][] boardsArrayCopy;
    Board board;

    public GameLogic(Board board) {
        boardsArrayCopy = new int[board.getSIZE()][board.getSIZE()];
        this.board = board;
        boardsArray = board.getBoard();
    }

    public void startGame() {
        initializeBoard();
        addNewNumber();
        addNewNumber();
    }

    public void resetGame() {
        try {
            Score.updateScore();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        initializeBoard();
        Score.setUserScore(0);
        addNewNumber();
        addNewNumber();
    }
    public void undo() {
        arrayCopy(boardsArray, boardsArrayCopy);
        board.setBoard(boardsArray);
        Score.setUserScore(Score.getPrevScore());
    }
    public void arrayCopy(int[][] a, int[][] b){
        for (int i = 0; i < board.getSIZE(); i++) {
            for (int j = 0; j < board.getSIZE(); j++) {
                a[i][j] = b[i][j];
            }
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < board.getSIZE(); i++) {
            for (int j = 0; j < board.getSIZE(); j++) {
                boardsArray[i][j] = 0;
            }
        }
        board.setBoard(boardsArray);
        arrayCopy(boardsArrayCopy, boardsArray);
        Score.setUserScore(0);

    }

    public void addNewNumber() {
        Random rand = new Random();
        int row, col;
        do {
            row = rand.nextInt(board.getSIZE());
            col = rand.nextInt(board.getSIZE());
        } while (boardsArray[row][col] != 0);
        int newNumber = (rand.nextInt(100) < PROBABILITY_2) ? 2 : 4;
        boardsArray[row][col] = newNumber;
        board.setBoard(boardsArray);
    }

    protected boolean isGameOver() {
        // Check if any cell contains the winning tile
        for (int[] row : boardsArray) {
            for (int cell : row) {
                if (cell == WINNING_TILE) {
                    System.out.println("Congratulations! You've reached 2048!");
                    try {
                        Score.updateScore();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    return true;
                }
            }
        }
        // Check if board is full
        for (int[] row : boardsArray) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        // Check for possible moves (adjacent cells with same value)
        for (int i = 0; i < board.getSIZE() - 1; i++) {
            for (int j = 0; j < board.getSIZE() - 1; j++) {
                if (boardsArray[i][j] == boardsArray[i + 1][j] || boardsArray[i][j] == boardsArray[i][j + 1]) {
                    return false;
                }
            }
        }
        System.out.println("Game Over! No more moves available.");
        try {
            Score.updateScore();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    public void moveUp() {
        arrayCopy(boardsArrayCopy, boardsArray);
//        boardsCopy = boards;
        for (int j = 0; j < board.getSIZE(); j++) {
            for (int i = 1; i < board.getSIZE(); i++) {
                if (boardsArray[i][j] != 0) {
                    int row = i;
                    while (row > 0 && boardsArray[row - 1][j] == 0) {
                        boardsArray[row - 1][j] = boardsArray[row][j];
                        boardsArray[row][j] = 0;
                        row--;
                    }
                    if (row > 0 && boardsArray[row - 1][j] == boardsArray[row][j]) {
                        boardsArray[row - 1][j] *= 2;
                        boardsArray[row][j] = 0;
                        Score.updateScore(boardsArray[row - 1][j]);

                    }
                }
            }
        }
        board.setBoard(boardsArray);
    }

    public void moveDown() {
        arrayCopy(boardsArrayCopy, boardsArray);
//        boardsCopy = boards;
        for (int j = 0; j < board.getSIZE(); j++) {
            for (int i = board.getSIZE() - 2; i >= 0; i--) {
                if (boardsArray[i][j] != 0) {
                    int row = i;
                    while (row < board.getSIZE() - 1 && boardsArray[row + 1][j] == 0) {
                        boardsArray[row + 1][j] = boardsArray[row][j];
                        boardsArray[row][j] = 0;
                        row++;
                    }
                    if (row < board.getSIZE() - 1 && boardsArray[row + 1][j] == boardsArray[row][j]) {
                        boardsArray[row + 1][j] *= 2;
                        boardsArray[row][j] = 0;
                        Score.updateScore(boardsArray[row + 1][j]);
                    }
                }
            }
        }
        board.setBoard(boardsArray);


    }

    public void moveLeft() {
        arrayCopy(boardsArrayCopy, boardsArray);
//        boardsCopy = boards;
        for (int i = 0; i < board.getSIZE(); i++) {
            for (int j = 1; j < board.getSIZE(); j++) {
                if (boardsArray[i][j] != 0) {
                    int col = j;
                    while (col > 0 && boardsArray[i][col - 1] == 0) {
                        boardsArray[i][col - 1] = boardsArray[i][col];
                        boardsArray[i][col] = 0;
                        col--;
                    }
                    if (col > 0 && boardsArray[i][col - 1] == boardsArray[i][col]) {
                        boardsArray[i][col - 1] *= 2;
                        boardsArray[i][col] = 0;
                        Score.updateScore(boardsArray[i][col - 1]);
                    }
                }
            }
        }
        board.setBoard(boardsArray);
    }

    public void moveRight() {
        arrayCopy(boardsArrayCopy, boardsArray);
//        boardsCopy = boards;
        for (int i = 0; i < board.getSIZE(); i++) {
            for (int j = board.getSIZE() - 2; j >= 0; j--) {
                if (boardsArray[i][j] != 0) {
                    int col = j;
                    while (col < board.getSIZE() - 1 && boardsArray[i][col + 1] == 0) {
                        boardsArray[i][col + 1] = boardsArray[i][col];
                        boardsArray[i][col] = 0;
                        col++;
                    }
                    if (col < board.getSIZE() - 1 && boardsArray[i][col + 1] == boardsArray[i][col]) {
                        boardsArray[i][col + 1] *= 2;
                        boardsArray[i][col] = 0;
                        Score.updateScore(boardsArray[i][col + 1]);
                    }
                }
            }
        }
        board.setBoard(boardsArray);
    }
}
