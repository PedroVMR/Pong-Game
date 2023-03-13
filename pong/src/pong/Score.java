package pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle{
    
    static int GAME_WIDHT;
    static int GAME_HEIGHT;
    int player1;
    int player2;

    Score(int GAME_WIDHT, int GAME_HEIGHT){
        Score.GAME_WIDHT = GAME_WIDHT;
        Score.GAME_HEIGHT = GAME_HEIGHT;

    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font ("Bit5x5",Font.PLAIN, 60));

        g.drawLine(GAME_WIDHT/2, 0, GAME_WIDHT/2, GAME_HEIGHT);

        g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), (GAME_WIDHT/2) -105, 50);
        g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), (GAME_WIDHT/2) +20, 50);
    }
    
}
