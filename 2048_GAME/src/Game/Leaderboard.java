package Game;

import Api.Score;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Leaderboard extends JPanel {

    public final int screenWidth = 485; // 768 pixels
    private final int screenHeight = 658;
    private String[] username;
    private Integer[] highscore;
    public BufferedImage background;
    public Leaderboard(){

//        background = setup("/bg/leaderBoard.png");
        this.setBackground(Color.decode("#fbf8ef"));
        try{
            Score.getUserScore();


        }catch (Exception e){
            e.printStackTrace();
        }
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));

        JPanel gap = new JPanel();
        gap.setPreferredSize(new Dimension(350, 100));
        gap.setOpaque(false);
        add(gap);

        for (int i = 0; i < 10; i++) {
            if (i<Score.usernames.size()){
                JPanel userPanel = new JPanel();
//                userPanel.setOpaque(false);
                userPanel.setPreferredSize(new Dimension(350, 45));
                userPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
//                userPanel.setOpaque(false);
                userPanel.setBackground(Color.decode("#8d6731"));
                userPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.decode("#8d6731")));

                JLabel nameLabel = new JLabel(Score.usernames.get(i));
                JLabel number = new JLabel(i+1+"");
                JLabel score = new JLabel(Score.highscores.get(i)+"");

                number.setPreferredSize(new Dimension(30, 30));
//                number.setOpaque(true);
                number.setHorizontalAlignment(JLabel.CENTER);
//                number.setBackground(Color.decode("#e1a23a"));
                number.setFont(new Font("Moonrising", Font.BOLD, 16));
                number.setForeground(Color.WHITE);

                nameLabel.setPreferredSize(new Dimension(200, 30));

                nameLabel.setFont(new Font("Moonrising", Font.BOLD, 14));
                nameLabel.setForeground(Color.WHITE);

                score.setPreferredSize(new Dimension(50, 30));
                score.setOpaque(true);
                score.setHorizontalAlignment(JLabel.CENTER);
                score.setBackground(Color.decode("#b3823e"));
                score.setFont(new Font("Moonrising", Font.BOLD, 14));
                score.setForeground(Color.WHITE);
                userPanel.add(number);
                userPanel.add(nameLabel);
                userPanel.add(score);
                add(userPanel);
            }
        }
        setPreferredSize(new Dimension(screenWidth, screenHeight));
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g2.drawImage(background,0,0,null);
        g2.setColor(Color.decode("#8d6731"));
        g2.setFont(new Font("Moonrising", Font.BOLD, 50));
        g2.drawString("Highscore", 95, 85);
//        g2.dispose();
    }
}
