/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.brickbreaker;;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author jiten
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener{
  private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay, this);
        Timer.start();
    }

    public void Paint(Graphics g) {
        //canvas
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 5, 592);
        g.fillRect(0, 0, 692, 5);
        g.fillRect(682, 5, 5, 592);

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 560, 30);

        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 10);

        //ball
        g.setColor(Color.GREEN);
        g.fillOval(ballposX, ballposY, 20, 20);

        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("      Game Over!!", 190, 280);
            g.drawString("        Score: " + score, 190, 320);
            g.setColor(Color.white);
            g.drawString("  Press Enter to Restart", 190, 360);
        }
        if(totalbricks == 0){
            play = false;
            ballYdir = -2;
            ballXdir = -1;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("      You Won!!", 190, 280);
            g.drawString("      Score: " + score, 190, 320);
            g.setColor(Color.white);
            g.drawString("  Press Enter to Restart", 190, 360);


        }

        g.dispose();


    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;

                        if (ballrect.intersects(brickrect)) {
                            map.setBricksValue(0, i, j);
                            totalbricks--;
                            score += 5;
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }


                }
            }


            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 5) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 5) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 665) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

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
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }


    }

    public void moveRight ()
    {
        play = true;
        playerX += 20;
    }
    public void moveLeft ()
    {
        play = true;
        playerX -= 20;
    }

}