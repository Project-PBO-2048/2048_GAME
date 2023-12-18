package Game;

import Api.JDBC;
import Tools.KeyHandler;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Board extends JPanel implements Runnable {
    
    private int[][]board;
    public final int SIZE = 4;
    private BoxColor boxColor = new BoxColor();

    JButton resetButton;
    JButton undoButton;

    boolean isGameOver = false;

    BufferedImage image;

    ScoreLabel scoreLabel;
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

        String username = JDBC.getUsername();

        JLabel userNameLabel = new JLabel(username+"");
        userNameLabel.setBounds(10,150,145,30);
        userNameLabel.setOpaque(true);
        userNameLabel.setBackground(Color.decode("#d6cdc4"));
        userNameLabel.setFont(new Font("Moonrising", Font.PLAIN, 16));
        userNameLabel.setForeground(Color.decode("#8d6731"));
        userNameLabel.setBorder(new MatteBorder(2,10,2,2,Color.decode("#d6cdc4")));

        add(userNameLabel);


        scoreLabel = new ScoreLabel();
        scoreLabel.setBounds(165,150,145,30);
        add(scoreLabel);


        resetButton = new JButton("Reset");
        resetButton.setBounds(400,150,70,30);
        resetButton.setBackground(Color.decode("#8d6731"));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorder(null);
        resetButton.setFont(new Font("Moonrising", Font.PLAIN, 12));
        add(resetButton);

        undoButton = new JButton("Undo");
        undoButton.setBounds(320,150,70,30);
        undoButton.setBackground(Color.decode("#8d6731"));
        undoButton.setForeground(Color.WHITE);
        undoButton.setBorder(null);
        undoButton.setFont(new Font("Moonrising", Font.PLAIN, 12));

        resetButton.addActionListener(e -> {
            gameLogic.resetGame();
            requestFocusInWindow();

            isGameOver = false;
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
        g2.setColor(Color.decode("#8d6731"));
        g2.setFont(new Font("Moonrising", Font.BOLD, 100));
        g2.drawString("2048", 105, 120);
        g2.setColor(Color.decode("#bbada0"));
        g2.fillRoundRect(10,190,460,465,15,15);

        int xGap = 25;
        int yGap = 10;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                g2.setColor(boxColor.getColor(board[j][i])); 
                g2.fillRoundRect(i * 100 + xGap, 200 + j * 100 + yGap, 100, 100, 20, 20);    

                if (board[j][i] <= 4){
                    g2.setColor(Color.decode("#796f6b"));
                }else{
                    g2.setColor(Color.decode("#f7f6f2"));

                }
                String s = String.valueOf(board[j][i]);
                if (board[j][i] == 0) {
                    s = "";
                }
                int stringLen = (int) g2.getFontMetrics().getStringBounds(s, g2).getWidth();

                g2.setFont(new Font("Bahnschrift", Font.BOLD, 40));
                if (i==0&&j==0){
                    g2.drawString(s, i*100+xGap+70 - stringLen/2, 200+j*100+yGap+65);
                }else{
                    g2.drawString(s, i*100+xGap+50 - stringLen/2, 200+j*100+yGap+65);
                }
                yGap+=10;
            }
            yGap = 10;
            xGap+=10;
        }

        if (isGameOver){
            Color halfTransparent = new Color(187, 173, 160, 127); // Warna setengah transparan dengan alpha 127 (diambil dari kode hex #bbada0)
            g2.setColor(halfTransparent);
            g2.fillRoundRect(10,190,460,465,15,15);
            g2.setColor(Color.decode("#776e65"));
            g2.setFont(new Font("Moonrising", Font.PLAIN, 30));
            g2.drawString("Game Over", 150, 420);
        }
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
                isGameOver = gameLogic.isGameOver();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
