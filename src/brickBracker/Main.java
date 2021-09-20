package brickBracker;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //JFrame
        JFrame obj = new JFrame();
        Gameplay gamePlay = new Gameplay();
        //Properties
        obj.setBounds(10,10,700,600);
        obj.setTitle("Breakout Ball Game");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
        obj.setLocationRelativeTo(null);

    }

}
