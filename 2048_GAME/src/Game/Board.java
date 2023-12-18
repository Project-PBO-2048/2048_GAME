package Game;

import Api.JDBC;
import Tools.KeyHandler;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;


public class Board extends JPanel implements Runnable {

    // array buat menyimpan angka-angka
    private int[][]board;
    // ukuran array 4x4 tidak bisa dirubah
    private final int SIZE = 4;
    // buat mengatur warna kotak
    private BoxColor boxColor = new BoxColor();

    JButton resetButton;
    JButton undoButton;

// buat mengecek apakah sudah kalah
    private boolean isGameOver = false;

// thread buat menampilkan score di papan
    ScoreLabel scoreLabel;
    Thread thread ;
    // buat menghandle logika game
    GameLogic gameLogic;
    // buat memanggil class keyhandler yang sudah di buat pada tools
    KeyHandler keyHandler = new KeyHandler();

    public Board() {

        // membuat object array
        board = new int[SIZE][SIZE];
        // membuat object gamelogic kemudian class board di passing untuk di proses kedalam gamelogic
        gameLogic = new GameLogic(this);
        // start game di panggil untuk mengisi array dari board
        gameLogic.startGame();
        // membuat object thread
        thread = new Thread(this);
        // untuk fokus pada gamenya agar tidak keluar dari container pas awal compile saat board di eksekusi
        setFocusable(true);
        setBackground(Color.decode("#fbf8ef"));
        addKeyListener(keyHandler);
        setLayout(null);
        //mengambil username pada awal saat memasukan username
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
//ketika meng klik reset maka akan terjadi aksi berupa pengahpusan angka atau mengulang dari awal game
        resetButton.addActionListener(e -> {
            gameLogic.resetGame();
            isGameOver = false;
        });
// ketika mengklik undo maka membalikan kondisi 1 gerakan sebelumnya
        undoButton.addActionListener(e -> {
            gameLogic.undo();
        });
        add(undoButton);
        thread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
// berfungsi untuk merender papan gamenya
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
// untuk menyeting warna judul game 2048 dan mengatur posision 2048
        g2.setColor(Color.decode("#8d6731"));
        g2.setFont(new Font("Moonrising", Font.BOLD, 100));
        g2.drawString("2048", 105, 120);
// untuk menyeting warna board panel background angka gamenya
        g2.setColor(Color.decode("#bbada0"));
        g2.fillRoundRect(10,190,460,465,15,15);

        // untuk menyeting jarak antara panel" angkanya
        int xGap = 25;
        int yGap = 10;
        // perulangan untuk membuat panel angka gamenya untuk yang horizontal
        for (int i = 0; i < SIZE; i++) {
            // perulangan panel angka game untuk vertical
            for (int j = 0; j < SIZE; j++) {
                // untuk setting warna panel game angkanya
                g2.setColor(boxColor.getColor(board[j][i])); 
                g2.fillRoundRect(i * 100 + xGap, 200 + j * 100 + yGap, 100, 100, 20, 20);    

                // digunakan untuk setting warna ketika angka lebih kecil dari sama dengan 4 maka akan bewarna hitam
                if (board[j][i] <= 4){
                    g2.setColor(Color.decode("#796f6b"));
                    // ketika angka lebih dari 4 maka akan bewarna putih
                }else{
                    g2.setColor(Color.decode("#f7f6f2"));

                }

                // digunakan untuk setting board menjadi 0 dulu semua
                String s = String.valueOf(board[j][i]);
                if (board[j][i] == 0) {
                    s = "";
                }

                // digunakan untuk mendapatkan ukuran panelnya agar dapat disetting secara manual menggunakan kode
                int stringLen = (int) g2.getFontMetrics().getStringBounds(s, g2).getWidth();

                // digunakan untuk setting font unntuk angka gamenya
                g2.setFont(new Font("Bahnschrift", Font.BOLD, 40));
                // memperbaiki sedikit error yang terdapat pada game ketika angka 128, angka tersebut melebihi panel gamenya
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

        // setting gameover ketika game over akan menampilkan syarat" seperti dibawah
        if (isGameOver){
            Color halfTransparent = new Color(187, 173, 160, 127); // Warna setengah transparan dengan alpha 127 (diambil dari kode hex #bbada0)
            g2.setColor(halfTransparent);
            g2.fillRoundRect(10,190,460,465,15,15);
            g2.setColor(Color.decode("#776e65"));
            g2.setFont(new Font("Moonrising", Font.PLAIN, 30));
            g2.drawString("Game Over", 150, 420);
        }
    }

    // game logic untuk keyHandler movenya
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

    // Accessor dan muttators
    public int[][] getBoard() {
        return board;
    }
    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getSIZE() {
        return SIZE;
    }


    // digunakan untuk ketika running
    @Override
    public void run() {
        while (true){
            try {
                // setiap 100 miliseconds panel akan terus diupdate agar dapat terus berjalan
                Thread.sleep(100);
                // maka akan diupdate
                update();
                // dan juga di gambar ulang untuk boardnya
                repaint();
                // dilakukkan pengecekan terus menerus saat bermain kondisi gameover akan berjalan ketika tidak ada angka yg sama bersebelahan
                // atas bawah ataupun kiri kanan sehingga tidak ada lagi pergerakan dilakukkan maka akan gameover
                isGameOver = gameLogic.isGameOver();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
