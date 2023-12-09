import javax.swing.*;
import java.awt.*;

public class Board extends JPanel implements Runnable  {
    private int[][]board;
    private final int SIZE = 4;

    JButton resetButton;
    JButton undoButton;
    Thread thread ;
    GameLogic gameLogic;
    KeyHandler keyHandler = new KeyHandler();

    public Board() {
        board = new int[SIZE][SIZE];
        gameLogic = new GameLogic(this);
        gameLogic.startGame();
        thread = new Thread(this);
        setFocusable(true);
        setBackground(Color.decode("#fbf8ef"));
        addKeyListener(keyHandler);
        setLayout(null);
        setPreferredSize(new Dimension(500, 700));


        resetButton = new JButton("Reset");
        resetButton.setBounds(400,150,70,30);
        resetButton.setBackground(Color.decode("#bbada0"));
        resetButton.setBorder(null);
        resetButton.setFont(new Font("Moonrising", Font.PLAIN, 12));
        add(resetButton);

        undoButton = new JButton("Undo");
        undoButton.setBounds(320,150,70,30);
        undoButton.setBackground(Color.decode("#bbada0"));
        undoButton.setBorder(null);
        undoButton.setFont(new Font("Moonrising", Font.PLAIN, 12));

        resetButton.addActionListener(e -> {
            gameLogic.resetGame();
            requestFocusInWindow();
        });
        undoButton.addActionListener(e -> {
            gameLogic.undo();
            requestFocusInWindow();
        });


        add(undoButton);
        thread.start();
    }



    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(Color.decode("#bbada0"));
        g2.fillRoundRect(10,190,460,465,15,15);



        int xGap = 25;
        int yGap = 10;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE ; j++) {
                g2.setColor(Color.decode("#eee4da"));
                g2.fillRoundRect(i*100+xGap,200+j*100+yGap,100,100,20,20);
                g2.setColor(Color.BLACK);
                String s = String.valueOf(board[j][i]);
                if (board[j][i] == 0) {
                    s = "";
                }
                int stringLen = (int) g2.getFontMetrics().getStringBounds(s, g2).getWidth();

                g2.setFont(new Font("Moonrising", Font.PLAIN, 30));
                g2.drawString(s, i*100+xGap+50 - stringLen/2, 200+j*100+yGap+60);
                yGap+=10;
            }
            yGap = 10;
            xGap+=10;
        }

//        g2.dispose();
    }



    public void update() throws InterruptedException {

        if (keyHandler.upPressed){
            gameLogic.moveUp();
            gameLogic.addNewNumber();
        }
        else if (keyHandler.downPressed){
            gameLogic.moveDown();
            gameLogic.addNewNumber();
        }
        else if (keyHandler.leftPressed){
            gameLogic.moveLeft();
            gameLogic.addNewNumber();
        }
        else if (keyHandler.rightPressed){
            gameLogic.moveRight();
            gameLogic.addNewNumber();
        }
    }

    public int[][] getBoard() {
        return board;
    }
    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getSIZE() {
        return SIZE;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
                update();
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
