/**
 *This class is the window that holds the game display and is a 
 *subclass of JFrame
 * 
 */

/**
 * @authors P. Cochran and N. Guidry
 * @version 1.000
 * last modified 10/18/2022
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class TetrisWindow extends JFrame
{
    private TetrisDisplay display;
    private TetrisGame game;
    
    private int rows = 20;
    private int cols = 12;
    private int winWid = 800;
    private int winHei = 800;
   
    public TetrisWindow()
    {
        this.setTitle("Tetris Game Board");
        this.setSize(winWid, winHei);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initMenus();
        
        game = new TetrisGame(rows, cols);
        display = new TetrisDisplay(game);
        
        this.add(display);
        this.setVisible(true);
    }
    
    public void initMenus()
    {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        
        JMenu leaderMenu = new JMenu("Leaderboard");
        menuBar.add(leaderMenu);
        
        JMenuItem newGame = new JMenuItem("New Game");
        gameMenu.add(newGame);
        newGame.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    game.newGame();
                }
            });
        
        
        JMenuItem saveGame = new JMenuItem("Save Game");
        gameMenu.add(saveGame);
        saveGame.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    String fName = askFileName();
                    if(fName == null || fName.equals(""))
                    {
                        String message = "Please give the file a name. Try again.";
                        JOptionPane.showMessageDialog(null, message);
                        return;
                    }
                    while(!checkFileName(fName))
                    {
                        fName = askFileName();
                    }
                    game.saveToFile(fName);
                }
            });
        
        JMenuItem loadGame = new JMenuItem("Load Game");
        gameMenu.add(loadGame);
        loadGame.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    JFileChooser j = new JFileChooser("SavedGames");
                    j.showSaveDialog(null);
                    String selectedFile = j.getSelectedFile().getName();
                    game.initFromFile(selectedFile);
                }
            });
        
        JMenuItem displayLeaders = new JMenuItem("Show Leaderboard");
        leaderMenu.add(displayLeaders);
        displayLeaders.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    displayLeaderboard();
                }
            });
        
        JMenuItem deleteLeaders = new JMenuItem("Clear Leaderboard");
        leaderMenu.add(deleteLeaders);
        deleteLeaders.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    
                }
            });
    }
    
    public String askFileName()
    {
        String userMessage = "Please choose a file name";
        String fName = JOptionPane.showInputDialog(null, userMessage, "CHOOSE FILE NAME", 1);
        
        return fName;
    }
    
    public boolean checkFileName(String fName)
    {
        File fileConnection = new File ("SavedGames/"+fName);
        if(fileConnection.exists())
        {
            String message = "File name already exists. Please try again with a different name.";
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        return true;
    }
    
    public void displayLeaderboard()
    {
        String leaderboardTitle = "Leaderboard";
        String leaderboardString = "Top Scores:\n";
        int maxIndex = 10;
        List<Integer> leaderboard = game.initLeaderboard();
        for(int counter = 0; counter < maxIndex; counter++)
        {
            if(leaderboard.get(counter) == 0)
            {
                leaderboardString += "#" + (counter + 1) + ".\n";
            }
            else
            {
                leaderboardString += "#" + (counter + 1) + ". " + leaderboard.get(counter) + "\n";
            }
        }
        JOptionPane.showMessageDialog(null, leaderboardString, leaderboardTitle, 1);
        
    }
    
    public void clearLeaderboard()
    {
        int maxIndex = 10;
        String leaderboardString = "Top Scores:\n";
        List<Integer> leaderboard = game.initLeaderboard();
        
        for(int counter = 0; counter < maxIndex; counter++)
        {
            leaderboard.set(counter, 0);
        }

        
    }
   
    public static void main(String[] args)
    {
        new TetrisWindow();
    }
}
