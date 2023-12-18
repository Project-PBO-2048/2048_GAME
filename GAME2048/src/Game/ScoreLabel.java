package Game;

import Api.Score;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class ScoreLabel extends JLabel implements Runnable {
    
    Thread thread;

    public ScoreLabel(){
        setText(Score.getScore()+"");
        setFont(new Font("Moonrising", Font.PLAIN, 12));
        setBackground(Color.decode("#d6cdc4"));
        setForeground(Color.decode("#8d6731"));
        setOpaque(true);
        setBorder(new MatteBorder(2,10,2,2,Color.decode("#d6cdc4")));
//        setOpaque(true);
        thread = new Thread(this);
        thread.start();
    }
    public void update(){
        setText("Score: "+Score.getScore());
    }

    @Override
    public void run() {
        while (thread!=null){
            try {
                Thread.sleep(100);
                update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
