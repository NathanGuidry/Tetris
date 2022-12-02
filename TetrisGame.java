/*
 * This class provides the data structure and logic for
 * the tetris game.
 */

/**
 * @authors P. Cochran and N. Guidry
 * @version 1.000
 * last modified 10/18/2022
 */
import java.util.*;
import javax.swing.*;
import java.io.*;

public class TetrisGame 
{
    private int background[][];
    private TetrisBrick fallingBrick;
    private int rows;
    private int cols;
    private int numBrickTypes = 7;
    Random randGen;
    private int score = 0;
    private int state = 1;
    
    public TetrisGame(int rows, int cols)
    {
        
        this.rows = rows;
        this.cols = cols;
        randGen = new Random();
        initBoard();
        initLeaderboard();
        spawnBrick();
    }
    
    public void initBoard()
    {
        this.background = new int[getRows()][getCols()];
        for(int index = 0;index < background.length; index++)
        {
            for(int count = 0; count < background[0].length; count++)
            {
                this.background[index][count] = 0;
            }
        }
    }
    
    public boolean newGame()
    {
        state = 1;
        score = 0;
        initBoard();
        spawnBrick();
        return true;
    }
    
    public int fetchBoardPosition(int row, int col)
    {
        return this.background[row][col];
    }
    
    public int getRows()
    {
        return rows;
    }
    
    public int getCols()
    {
        return cols;
    }
    
    private void spawnBrick()
    {
        int randBrick = randGen.nextInt(numBrickTypes) + 1;
        switch (randBrick)
        {
            case 1:
                fallingBrick = new StackBrick();
                break;
            
            case 2:
                fallingBrick = new SquareBrick();
                break;
            
            case 3:
                fallingBrick = new LongBrick();
                break;
            
            case 4:
                fallingBrick = new JayBrick();
                break;
                
            case 5:
                fallingBrick = new EssBrick();
                break;
                      
            case 6:
                fallingBrick = new ElBrick();
                break;
                
            case 7:
                fallingBrick = new ZeeBrick();
                break;
        }   
    }
    
    public boolean isGameOver()
    {
        if(state == 0)
        {
            return true;
        }
        return false;
    }
    
    public List initLeaderboard()
    {
        int maxIndex = 10;
        List<Integer> leaderboard = new ArrayList<Integer>();
        
        File fileConnection = new File ("Leaderboard/Leaderboard");
        
        for(int i = 0; i < maxIndex; i++)
        {
            leaderboard.add(0);
        }
        
        try
        {
            int counter = 0;
            Scanner inScan = new Scanner(fileConnection);
            do
            {
                leaderboard.set(counter, inScan.nextInt());
                counter++;
            }
            while(inScan.hasNextInt());
        }
        catch(IOException IOE)
        {
            String errorMessage = "cannot retrieve leaderboard.";
            JOptionPane.showMessageDialog(null,errorMessage,
                    "Error Message  ", 1, null);
        }
        
        return leaderboard;
    }
    
    
    private List addToLeaderboard(int score)
    {
        List<Integer> leaderboard = initLeaderboard();
        int maxIndex = 10;
        
        for(int counter = 0; counter <= maxIndex; counter++)
        {
            if(leaderboard.get(counter) == null || score >= leaderboard.get(counter))
            {
                leaderboard.add(score);
                leaderboard.sort(Collections.reverseOrder());
                leaderboard = leaderboard.subList(0, maxIndex); 
                saveLeaderboard(leaderboard);
                return leaderboard;
            }
        }
        return leaderboard;
    }
    
    private void saveLeaderboard(List leaderboard)
    {
        String leaderboardToString = "";
        String successMessage = "You've been placed on the leaderboard!";
        
        for(int counter = 0; counter < leaderboard.size(); counter++)
        {
            leaderboardToString += leaderboard.get(counter) + " ";
        }
        
        File fileConnection = new File ("Leaderboard/Leaderboard");
        try
        {
            FileWriter outWriter = new FileWriter (fileConnection);
            outWriter.write(leaderboardToString);
            outWriter.close();
        }
        catch (IOException ioe)
        {
            String errorMessage = "cannot save leaderboard stats.";
            JOptionPane.showMessageDialog(null,errorMessage,
                    "Error Message  ", 1, null);
        }
    }
    
    public void makeMove(char symbol)
    {
        switch (symbol)
        {
            case 'D':
                fallingBrick.moveDown();
                if(validateMove() == false)
                {
                    fallingBrick.moveUp();
                    transferColor();
                    detectFullRows();
                    isGameOver();
                    if(state == 0)
                    {
                       addToLeaderboard(score);
                    }
                    else
                    {
                        spawnBrick();
                    }
                }
                break;
            case 'L':
                fallingBrick.moveLeft();
                if(validateMove() == false)
                {
                    fallingBrick.moveRight();
                }
                break;
            case 'R':
                fallingBrick.moveRight();
                if(validateMove() == false)
                {
                    fallingBrick.moveLeft();
                }
                break;
            case 'T':
                fallingBrick.rotate();
                if(validateMove() == false)
                {
                    fallingBrick.unrotate();
                }
                break;
            case 'U':
                fallingBrick.unrotate();
                break;
            case 'N':
                newGame();
                break;
        }
        validateMove(); 
    }
    
    private boolean validateMove()
    {
        int counter = 0;
        
        while(counter < getNumSegs())
        {
            if(getSegRow(counter) < 0)
            {
                makeMove('U');
            }
            if(getSegCol(counter) < 0)
            {
                fallingBrick.moveRight();
            }
            if(getSegCol(counter) > getCols() - 1)
            {
                fallingBrick.moveLeft();
            }
            if (getSegRow(counter) > getRows() - 1 || fetchBoardPosition(fallingBrick.position[counter][0],fallingBrick.position[counter][1]) != 0)
            {
                if(getSegRow(counter) == 0)
                {
                    state = 0;
                }
                return false;
            }
            counter ++;
        }
        return true;
    }
    
    public int getNumSegs()
    {
        int numSegs = 4;
        return numSegs;
    }
    
    public int getFallingBrickColor()
    {
        return fallingBrick.getColorNumber();
    }
    
    public int getSegRow(int segNum)
    {
        return fallingBrick.position[segNum][0];
    }
    
    public int getSegCol(int segNum)
    {
        return fallingBrick.position[segNum][1];
    }
    
    private int getMinimumRow(TetrisBrick fallingBrick)
    {
        int counter = 0;
        int minimumRow = fallingBrick.position[0][0];
        while(counter < fallingBrick.numSegments)
        {
            if(fallingBrick.position[counter][0] < minimumRow)
            {
                minimumRow = fallingBrick.position[counter][0];
            }
            counter++;
        }
        return minimumRow;
    }
    
    private int getMaximumRow(TetrisBrick fallingBrick)
    {
        int firstRowTested = fallingBrick.position[0][0];
        int counter = 0;
        int maximumRow = firstRowTested;
        while(counter < fallingBrick.numSegments)
        {
            if(fallingBrick.position[counter][0] > maximumRow)
            {
                maximumRow = fallingBrick.position[counter][0];
            }
            counter++;
        }
        return maximumRow;
    }
    
    private boolean rowHasSpace(int rowNumber)
    {
        for (int col = 0; col < getCols(); col ++)
        {
            if(this.background[rowNumber][col] == 0)
            {
                return true;
            }
        }
        return false;
    }
    
    private void copyRow(int rowNumber)
    {
        for(int col = 0; col < getCols(); col++)
        {
            this.background[rowNumber][col] = this.background[rowNumber - 1][col];
        }
    }
    
    private void copyAllRows(int rowNumber)
    {
        for(int row = rowNumber; row > 0; row--)
        {
            copyRow(row);
        }
    }
    
    private void detectFullRows()
    {
        int numOfRowsDeleted= 0;
        int minRowOfRange = getMinimumRow(fallingBrick);
        for(int currentRow = getMaximumRow(fallingBrick); currentRow >= minRowOfRange; currentRow--)
        {
            if(rowHasSpace(currentRow) == false)
            {
                copyAllRows(currentRow);
                currentRow++;
                minRowOfRange--;
                numOfRowsDeleted ++;
            }
        }
        switch(numOfRowsDeleted)
        {
            case 1: 
                score += 100;
                break;
            case 2: 
                score += 300;
                break;
            case 3:
                score += 600;
                break;
            case 4:
                score += 1200;
                break;
        }
    }
    
    public String getScore()
    {
        String scoreToString = String.valueOf(score);
        return scoreToString;
    }
    
    private void transferColor()
    {
        int counter = 0;
        while(counter < getNumSegs())
        {
            this.background[fallingBrick.position[counter][0]][fallingBrick.position[counter][1]] = fallingBrick.getColorNumber();
            counter++; 
        }     
    }
    
    public String toString()
    {
        String boardToString = "" + background.length + " " + background[0].length + "\n";
        for (int row = 0; row < background.length; row ++)
        {
            for(int col = 0; col < background[0].length; col ++)
            {
                boardToString += background[row][col] + " ";
            }
            boardToString += "\n";
        }
        boardToString = boardToString.substring(0,boardToString.length()-1) + "\n";
        boardToString += this.score + "\n";
        return boardToString;
    }
    
    public void saveToFile(String fName)
    {
        String data = this.toString() + fallingBrick.toString();
        File fileConnection = new File ("SavedGames/"+fName);
        try
        {
            FileWriter outWriter = new FileWriter (fileConnection);
            outWriter.write(data);
            outWriter.close();
        }
        catch (IOException ioe)
        {
            String errorMessage = "cannot save to file.";
            JOptionPane.showMessageDialog(null,errorMessage,
                    "Error Message  ", 1, null);
        }
    }
    
    public void initFromFile(String fName)
    {
        File fileConnection = new File("SavedGames/" + fName);
        try
        {
            Scanner inScan = new Scanner(fileConnection);
            rows = inScan.nextInt();
            cols = inScan.nextInt();
            background = new int[rows][cols];
            for(int row = 0; row < rows; row++){
                for(int col = 0; col < cols; col++)
                {
                    background[row][col] = inScan.nextInt();
                }
        }
            
        this.score = inScan.nextInt();

        int colorNum = inScan.nextInt();
        fallingBrick.colorNum = colorNum;

        switch (colorNum)
        {
            case 1:
                fallingBrick = new StackBrick();
                break;

            case 8:
                fallingBrick = new SquareBrick();
                break;

            case 4:
                fallingBrick = new LongBrick();
                break;

            case 3:
                fallingBrick = new JayBrick();
                break;

            case 5:
                fallingBrick = new EssBrick();
                break;

            case 2:
                fallingBrick = new ElBrick();
                break;

            case 6:
                fallingBrick = new ZeeBrick();
                break;
        }
            
        for(int counter = 0; counter < fallingBrick.numSegments; counter++)
        {
            fallingBrick.position[counter][0] = inScan.nextInt();
            fallingBrick.position[counter][1] = inScan.nextInt();
        }
             
        }
        catch(Exception e)
        {
            String errorMessage = "cannot load file.";
            JOptionPane.showMessageDialog(null,errorMessage,
                    "Error Message  ", 1, null);
        }
    }

}
