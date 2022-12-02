/**
 * This class creates the graphic display that shows the status of 
 * the game by extending a JPanel
 * 
 */

/**
 * @authors P. Cochran and N. Guidry
 * @version 1.000
 * last modified 10/18/2022
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;


public class TetrisDisplay extends JPanel
{
    private TetrisGame game;
    private int cellSize = 30;
    private int start_x = 380;
    private int start_y = 80;
    private int speed;
    private int delay = 300;
    private Timer timer;
    private boolean pause = false;
    
    private Color[] colors = {Color.WHITE, Color.MAGENTA, Color.YELLOW
        ,Color.ORANGE, Color.BLUE, Color.GREEN, Color.RED, Color.BLACK, Color.CYAN};
    
    public TetrisDisplay(TetrisGame gam)
    {
        game = gam;
        
        this.addKeyListener( new KeyAdapter(){
            public void keyPressed(KeyEvent ke)
            {
                translateKey(ke);
            }
        });
        
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        
        timer = new Timer(delay, new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                cycleMove();
            }
        });
        
        timer.start();
        if(pause == true)
        {
            timer.stop();
        }
        if(game.isGameOver() == true)
        {
            timer.stop();
        }
        
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        drawBackground(g);
        drawWell(g);
        drawBrick(g);
        drawScoreBox(g);
        
        if(game.isGameOver())
        {
            drawGameOver(g);
        }
        
    }
    
    private void drawGameOver(Graphics g)
    {
        int initialY = (game.getCols() / 2 + 3) * cellSize;
        int width = game.getCols() * cellSize;
        int height = (game.getRows() / 3) * cellSize;
        int xText = start_x + 30;
        int yText = initialY + 110; 
        int strokeWeight = 10;
        
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(strokeWeight));
        
        g.setColor(colors[0]);
        g.fillRect(start_x, initialY, width, height);
        
        g.setColor(colors[4]);
        g.drawRect(start_x, initialY, width, height);
          
        g.setColor(colors[4]);
        g.drawRect(start_x, initialY, width, height);
        
        g.setColor(colors[4]);
        g.drawString("GAME OVER",xText , yText);
    }
    
    private void drawWell(Graphics g)
    {
        int rect2_x = start_x +(cellSize * game.getCols());
        int rect3_y = start_y + (cellSize * game.getRows());
        
        int rect1n2Width = cellSize;
        int rect1n2Hei = cellSize * game.getRows();
        int rect3Width = cellSize * game.getCols() + cellSize*2;
       
        g.setColor(colors[7]);
        g.drawRect(start_x - cellSize, start_y,rect3Width, rect1n2Hei + cellSize);
        g.fillRect(start_x - cellSize, start_y, rect1n2Width, rect1n2Hei);
        g.fillRect(rect2_x,start_y, rect1n2Width, rect1n2Hei);
        g.fillRect(start_x - cellSize, rect3_y, rect3Width, cellSize);
    }
    
    private void drawScoreBox(Graphics g)
    {
        int xSpacing = start_x - (cellSize * 12);
        int ySpacing = start_y + (cellSize * 7);
        int width = (cellSize * (game.getRows()/2));
        int height = cellSize * 3;
        int xSpacing2 = xSpacing + 10;
        int ySpacing2 = ySpacing + 10;
        int newWidth = width - 20;
        int newHeight = height - 20;
        int xSpacing3 = xSpacing2 + 100;
        int ySpacing3 = ySpacing2 + 50;
        int fontSize = 50;
        int labelX = xSpacing;
        int labelY = ySpacing - 10;
        String label = "Score:";
        
        g.setColor(colors[7]);
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
        g.drawString(label, labelX, labelY);
        g.fillRect(xSpacing, ySpacing, width, height);
        
        g.setColor(colors[0]);
        g.fillRect(xSpacing2, ySpacing2, newWidth, newHeight);
        
        g.setColor(colors[7]);
        g.drawString(game.getScore(), xSpacing3, ySpacing3);
    }
    
    private void drawBackground(Graphics g)
    {

        for(int index = 0; index < (game.getRows()); index++)
        {
            for(int ind = 0; ind < (game.getCols()); ind++)
            {
                g.setColor(colors[game.fetchBoardPosition(index, ind)]);
                g.fillRect(start_x + (cellSize * ind), start_y + (cellSize * index), cellSize, cellSize);
                
                if(game.fetchBoardPosition(index, ind) != 0)
                {
                    g.setColor(colors[7]);
                    g.drawRect(start_x + (cellSize * ind), start_y + (cellSize * index), cellSize, cellSize);
                }        
            }
        }
    }
 
    private void drawBrick(Graphics g)
    {
        int counter = 0;
        while(counter < game.getNumSegs())
        {
            g.setColor(colors[game.getFallingBrickColor()]);
            g.fillRect(start_x + (cellSize * game.getSegCol(counter) + 1),start_y + (cellSize * game.getSegRow(counter) + 1), cellSize, cellSize);
            
            g.setColor(colors[7]);
            g.drawRect(start_x + (cellSize * game.getSegCol(counter) + 1),start_y + (cellSize * game.getSegRow(counter) + 1), cellSize, cellSize);
            counter ++;
        }
    }

    
    private void cycleMove()
    {
        game.makeMove('D');
        repaint();
    }
    
    private void translateKey(KeyEvent ke)
    {
        int code = ke.getKeyCode();
        switch(code)
        {
            case KeyEvent.VK_UP:
                game.makeMove('T');
                repaint();
                break;
            case KeyEvent.VK_DOWN:
                game.makeMove('D');
                repaint();
                break;
            case KeyEvent.VK_RIGHT:
                game.makeMove('R');
                repaint();
                break;
            case KeyEvent.VK_LEFT:
                game.makeMove('L');
                repaint();
                break;
            case KeyEvent.VK_SPACE:
                if(pause == false)
                {
                    timer.stop();
                    pause = true;
                }
                else
                {
                    timer.start();
                    pause = false;
                }
                break;
            case KeyEvent.VK_N:
                game.makeMove('N');
                repaint();
                break;
        }
    }
}
