package pong;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyAdapter;
import java.util.*;
import java.util.Scanner;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable{
 
    static final int GAME_WIDHT = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDHT * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDHT, GAME_HEIGHT);
    static final int BALL_DIAMENTER = 20;
    static final int PADDLE_WIDHT = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel(){
        newPaddles();
        newBall();
        score = new Score(GAME_WIDHT, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void newBall(){
        //random = new Random();
        ball = new Ball((GAME_WIDHT/2)-(BALL_DIAMENTER),(GAME_HEIGHT/2)-(BALL_DIAMENTER) , BALL_DIAMENTER, BALL_DIAMENTER);

    }
    public void newPaddles(){
        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDHT,PADDLE_HEIGHT,1);
        paddle2 = new Paddle(GAME_WIDHT-PADDLE_WIDHT,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDHT,PADDLE_HEIGHT,2);

    }
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);

    }
    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);

    }
    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();

    }
    public void checkCollision() {
        // quice da bola nas bordas
        if(ball.y <=0){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= GAME_HEIGHT-BALL_DIAMENTER){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.x <=0){
            ball.setXDirection(-ball.xVelocity);
        }
        if(ball.x >= GAME_WIDHT-BALL_DIAMENTER){
            ball.setXDirection(-ball.xVelocity);
        }

        // colisão entre a bola e as barras
        if(ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if(ball.yVelocity>0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if(ball.yVelocity>0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        // para as barras nas bordas da janela
        if(paddle1.y<=0)
            paddle1.y = 0;
        if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        
        if(paddle2.y<=0)
            paddle2.y = 0;
        if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
        
        // dar ao jogador um ponto e criar uma nova barra e bola
        if(ball.x <=0 ){
            score.player2++;
            newPaddles();
            newBall();
        }
        if(ball.x >= GAME_WIDHT-BALL_DIAMENTER) {
            score.player1++;
            newPaddles();
            newBall();
        }
       
    }

    public void run(){
        // game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta --;
                
            }
        }

    }
    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);

        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
       
    }

    }
}