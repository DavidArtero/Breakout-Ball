package brickBracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    //Starting position
    private int playerX = 310;

    //Position for ball
    private int ballPosX = 120;
    private int ballPosY = 350;
    
    Random random = new Random();
    int n = random.nextInt(2+1-2) - 2;
    private int ballXdir = n;
    private int ballYdir = -2;


    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        // bakground
        g.setColor(Color.black);
        g.fillRect(1,1, 692, 592);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        // paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550,100,8);

        // ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY,20,20);

        g.dispose();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            //rectangle behind ball
            if(new Rectangle(ballPosX, ballPosY,20,20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }

            ballPosX += ballXdir;
            ballPosY += ballYdir;
            //Left
            if(ballPosX < 0){
                ballXdir = -ballXdir;
            }
            //Top
            if(ballPosY < 0){
                ballYdir = -ballYdir;
            }
            //right
            if(ballPosX >= 670){
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (playerX <= 10) {
                    playerX = 10;
                } else {
                    moveLeft();
                }
            }
        }

    public void moveRight(){
            play = true;
            playerX+=20;
        }

    public void moveLeft(){
        play = true;
        playerX-=20;
    }
}
