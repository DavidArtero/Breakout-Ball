package brickBracker;

import org.w3c.dom.css.Rect;

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
    private int delay = 2;

    //Starting position
    private int playerX = 310;

    //Position for ball
    private int ballPosX = 120;
    private int ballPosY = 350;
    
    Random random = new Random();
    int n = random.nextInt(2+1-2) - 2;
    private int ballXdir = n;
    private int ballYdir = -2;

    private MapGenerator map;


    public Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(1, this);
        timer.start();
    }

    public void paint(Graphics g){
        // bakground
        g.setColor(Color.black);
        g.fillRect(1,1, 692, 592);

        //draw map
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score,590,30);

        // paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550,100,8);

        // ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY,20,20);

        if(totalBricks <=0){

            play = false;
            ballXdir = 0;
            ballYdir =0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME OVER, Scores: " + score,260,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("HAS GANADO",230,350);
        }

        //Game Over
        if(ballPosY > 570){
            play = false;
            ballXdir = 0;
            ballYdir =0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME OVER, Scores: " + score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to restart ",230,350);
        }

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

            //detect bricks
            A: for (int i = 0; i < map.map.length ; i++) {
                for (int j = 0; j < map.map[0].length ; j++) {
                    if(map.map[i][j] > 0){
                        int brickX = j* map.brickWidth +80;
                        int brickY = i* map.brickHeight +50;
                        int brickWith = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        //Rectangle around brick
                        Rectangle rect = new Rectangle(brickX,brickY,brickWith,brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        //If intersect
                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks --;
                            score += 5;

                            if(ballPosX +10 <= brickRect.x || ballPosX +1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }

                    }
                }
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

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = n;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
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
