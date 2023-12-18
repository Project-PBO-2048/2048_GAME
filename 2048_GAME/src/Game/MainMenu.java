package Game;

import Api.JDBC;
import Api.Register;
import Tools.ImageTool;
import Tools.getImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

public class MainMenu extends JPanel implements getImage {
    //    Screen Setting
    public final int screenWidth = 485;
    private final int screenHeight = 658;

    private BufferedImage background;

    public MainMenu() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.decode("#fbf8ef"));
        this.setDoubleBuffered(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 330));
        background = setupImage("/Assets/bg.png");

        JLabel usrLabel = new JLabel("Input Your Name");

        JTextField usernameInput = new JTextField();

        JButton startButton = new JButton("Start Game");

        JButton leaderboardButton = new JButton("Leaderboard");

        JPanel buttonPanel = new JPanel();

        buttonPanel.setPreferredSize(new Dimension(300, screenHeight));
        buttonPanel.setOpaque(false);
        buttonPanel.add(usrLabel);
        buttonPanel.add(usernameInput);
        buttonPanel.add(startButton);
        buttonPanel.add(leaderboardButton);

        usrLabel.setPreferredSize(new Dimension(300, 20));
        usrLabel.setFont(new Font("Moonrising", Font.BOLD, 14));
        usrLabel.setForeground(Color.decode("#8d6731"));
        usrLabel.setHorizontalAlignment(JLabel.CENTER);

        usernameInput.setPreferredSize(new Dimension(300, 35));
        usernameInput.setBackground(Color.decode("#eee4da"));
        usernameInput.setForeground(Color.decode("#8d6731"));
        usernameInput.setFont(new Font("Moonrising", Font.BOLD, 14));
        usernameInput.setBorder(new MatteBorder(3, 3, 3, 3, Color.decode("#8d6731")));
        usernameInput.setCaretColor(Color.decode("#8d6731"));
        usernameInput.setHorizontalAlignment(JTextField.CENTER);

        startButton.setPreferredSize(new Dimension(300, 50));
        startButton.setBackground(Color.decode("#8d6731"));
        startButton.setForeground(Color.decode("#eee4da"));
        startButton.setFont(new Font("Moonrising", Font.BOLD, 25));

        leaderboardButton.setPreferredSize(new Dimension(300, 50));
        leaderboardButton.setBackground(Color.decode("#8d6731"));
        leaderboardButton.setForeground(Color.decode("#eee4da"));
        leaderboardButton.setFont(new Font("Moonrising", Font.BOLD, 25));

        add(buttonPanel);

        startButton.addActionListener(e -> {
            if (!usernameInput.getText().equals("")){
                try {
                    Register.register(JDBC.client,usernameInput.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JFrame window = new JFrame();
                window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                window.setTitle("2048");
                window.setSize(500, 700);
                window.setLocationRelativeTo(null);
                Board board = new Board();
                window.add(board);

                ImageIcon icon = new ImageIcon("D:\\unram\\TUGAS\\SEMESTER 5\\PBO\\PRAKTIKUM\\2048\\2048\\GAME2048\\src\\Assets\\icon.png");
                Image image = icon.getImage();

                window.setIconImage(image);
                window.setVisible(true);
            }
        });
        leaderboardButton.addActionListener(e -> {
            Leaderboard leaderboardPanel = new Leaderboard();
            JFrame leaderboardFrame = new JFrame("Leaderboard");
            leaderboardFrame.add(leaderboardPanel);
            leaderboardFrame.pack();
            leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            leaderboardFrame.setResizable(false);
            leaderboardFrame.setLocationRelativeTo(null);
            leaderboardFrame.setVisible(true);
            ImageIcon icon = new ImageIcon("D:\\unram\\TUGAS\\SEMESTER 5\\PBO\\PRAKTIKUM\\2048\\2048\\GAME2048\\src\\Assets\\icon.png");
            Image image = icon.getImage();
            leaderboardFrame.setIconImage(image);

        });

    }
    @Override
    public BufferedImage setupImage(String path){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(path));
            image = ImageTool.scaleImage(image, 350, 320);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.drawImage(background,65,20,null);

    }
}