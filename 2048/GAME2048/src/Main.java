import Api.JDBC;
import Game.Board;
import Game.MainMenu;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        new JDBC();
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("2048");

        MainMenu mainMenu = new MainMenu();
        window.add(mainMenu);
        window.pack();

        ImageIcon icon = new ImageIcon("D:\\Kuliah\\SMT5\\PBO\\Praktikum\\Mine\\2048\\GAME2048\\src\\Assets\\icon.png");
        Image image = icon.getImage();

        window.setIconImage(image);
        window.setLocationRelativeTo(null);
        window.setVisible(true);



    }
}