/**
 * This is the abstract superclass of all of the Brick classes
 */

/**
 * @authors P. Cochran and N. Guidry
 * @version 1.000
 * last modified 10/18/2022
 */

public abstract class TetrisBrick 
{
    protected int numSegments = 4;
    protected int[][] position= new int[numSegments][2];
    protected int colorNum;
    protected int orientation = 0;
    protected int segmentZero = 0;
    protected int segmentOne = 1;
    protected int segmentTwo = 2;
    protected int segmentThree = 3;
   
    
    public TetrisBrick()
    {
       
    }
    
    public int[][] moveDown()
    {
        int segNum = 0;
        while (segNum < numSegments)
        {
            
            this.position[segNum][0] += 1;
            
            segNum += 1;
        }
            return position;
    }
    
    public int[][] moveUp()
    {
        int segNum = 0;
        while (segNum < numSegments)
        {
            this.position [segNum][0]-= 1;

            segNum += 1;
        }
        
        return position;
    }

    public int [][] moveLeft()
    {
        int segNum = 0;
        while (segNum < numSegments)
        {
            this.position [segNum][1]-=1;
            
            segNum +=1;
        }
        
        return position;
    }
    
    public int[][] moveRight()
    {
        int segNum = 0;
        while (segNum < numSegments)
        {
            this.position [segNum][1] += 1;
            
            segNum += 1;
        }
        return position;
    }
    
    public int getColorNumber()
    {
        return colorNum;
    }
    
    public String toString()
    {
        String positionToString = "";
        positionToString += colorNum + "\n" + setSegPosition();
        
        return positionToString;
    }
    
    public String setSegPosition()
    {
        String positionToString = "";
        for(int counter = 0; counter < numSegments; counter++)
        {
            positionToString += position[counter][0] + " " + position[counter][1] + "\n";
        }
        return positionToString;
    }
    
    
    public abstract int[][] rotate();
    public abstract int[][] unrotate();
    
    public abstract int[][] initPosition();
}

