/**
 * This is the stack shaped brick subclass
 */

/**
 * @authors P. Cochran and N. Guidry
 * @version 1.000
 * last modified 10/18/2022
 */

public class StackBrick extends TetrisBrick
{
    public StackBrick()
    {
        this.colorNum = 1; 
        this.position = initPosition();
    }
    
    @Override
    public int[][] initPosition()
    {
        position[segmentZero][0] = 0;
        position[segmentZero][1] = 5;
        position[segmentOne][0] = 1;
        position[segmentOne][1] = 4;
        position[segmentTwo][0] = 1;
        position[segmentTwo][1] = 5;
        position[segmentThree][0] = 1;
        position[segmentThree][1] = 6;
        
        return position;
    }
    
    @Override
    public int[][] rotate()
    {
        orientation += 1;
        if (orientation == 4)
        {
            orientation  = 0;
        }
        switch (orientation)
        {
            case 0:
                position[segmentZero][0] -= 1;
                position[segmentZero][1] += 1;
                position[segmentOne][0] -= 1;
                position[segmentOne][1] -= 1;
                position[segmentThree][0] += 1;
                position[segmentThree][1] += 1;
                break;
            case 1:
                position[segmentZero][0] += 1;
                position[segmentZero][1] += 1;
                position[segmentOne][0] -= 1;
                position[segmentOne][1] += 1;
                position[segmentThree][0] += 1;
                position[segmentThree][1] -= 1;
                break;
                
            case 2:
                position[segmentZero][0] += 1;
                position[segmentZero][1] -= 1;
                position[segmentOne][0] += 1;
                position[segmentOne][1] += 1;
                position[segmentThree][0] -= 1;
                position[segmentThree][1] -= 1;
                break;
            
            case 3:
                position[segmentZero][0] -= 1;
                position[segmentZero][1] -= 1;
                position[segmentOne][0] += 1;
                position[segmentOne][1] -= 1;
                position[segmentThree][0] -= 1;
                position[segmentThree][1] += 1;
                break;
        }
        return position;
    }
    
    @Override
    public int[][] unrotate()
    {
        orientation -= 1;
        if (orientation == -1)
        {
            orientation = 3;
        }
        switch (orientation)
        {
            case 0:
                position[segmentZero][0] -= 1;
                position[segmentZero][1] -= 1;
                position[segmentOne][0] += 1;
                position[segmentOne][1] -= 1;
                position[segmentThree][0] -= 1;
                position[segmentThree][1] += 1;
                break;
            case 1:
                position[segmentZero][0] -= 1;
                position[segmentZero][1] += 1;
                position[segmentOne][0] -= 1;
                position[segmentOne][1] -= 1;
                position[segmentThree][0] += 1;
                position[segmentThree][1] += 1;
                break;
                
            case 2:
                position[segmentZero][0] += 1;
                position[segmentZero][1] += 1;
                position[segmentOne][0] -= 1;
                position[segmentOne][1] += 1;
                position[segmentThree][0] += 1;
                position[segmentThree][1] -= 1;
                break;
            
            case 3:
                position[segmentZero][0] += 1;
                position[segmentZero][1] -= 1;
                position[segmentOne][0] += 1;
                position[segmentOne][1] += 1;
                position[segmentThree][0] -= 1;
                position[segmentThree][1] -= 1;
                break;
        }
        return position;
    }
}
