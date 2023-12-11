import java.awt.*;
import java.util.Arrays;
import java.util.Random;


public class GameLog {

    private static final int WINNING_TILE = 2048;
    private static final int PROBABILITY_2 = 90; // Probability 90% for generating 2
//    KeyHandler keyHandler;

    private int[][] boards;
    private int[][] boardsCopy;
    Board board;

    public GameLog (Board board) {
        this.board = board;
        boards = board.getBoard();
    }

