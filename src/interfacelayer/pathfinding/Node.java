package interfacelayer.pathfinding;

public class Node
{
    private int xPos;
    private int yPos;
    private int direction;

    private boolean isInPath;
    private boolean isEndPos;
    private boolean isStartPos;

    public Node(int xPos, int yPos)
    {
        this.xPos       = xPos;
        this.yPos       = yPos;
        this.direction  = -1;
        this.isInPath   = false;
        this.isStartPos = false;
        this.isEndPos   = false;
    }

    public int getxPos()
    {
        return xPos;
    }

    public void setxPos(int xPos)
    {
        this.xPos = xPos;
    }

    public int getyPos()
    {
        return yPos;
    }

    public void setyPos(int yPos)
    {
        this.yPos = yPos;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    public boolean isInPath()
    {
        return isInPath;
    }

    public void setInPath(boolean inPath)
    {
        isInPath = inPath;
    }

    public boolean isEndPos()
    {
        return isEndPos;
    }

    public void setEndPos(boolean endPos)
    {
        isEndPos = endPos;
    }

    public boolean isStartPos()
    {
        return isStartPos;
    }

    public void setStartPos(boolean startPos)
    {
        isStartPos = startPos;
    }
}
