/**
 * This is the square shaped brick subclass
 */

/**
 * @authors P. Cochran and N. Guidry
 * @version 1.000
 * last modified 10/18/2022
 */

public class SquareBrick extends TetrisBrick
{
    public SquareBrick()
    {
        this.colorNum = 8; 
        this.position = initPosition();
    }
    
    @Override
    public int[][] initPosition()
    {
        position[segmentZero][0] = 0;
        position[segmentZero][1] = 5;
        position[segmentOne][0] = 0;
        position[segmentOne][1] = 6;
        position[segmentTwo][0] = 1;
        position[segmentTwo][1] = 5;
        position[segmentThree][0] = 1;
        position[segmentThree][1] = 6;
        
        return position;
    }
    
    @Override
    public int[][] rotate()
    {
        return position;
    }
    
    @Override
    public int[][] unrotate()
    {
        return position;
    }
}
