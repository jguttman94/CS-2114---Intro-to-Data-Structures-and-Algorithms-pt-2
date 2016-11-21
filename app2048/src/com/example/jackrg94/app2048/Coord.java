package com.example.jackrg94.app2048;

// -------------------------------------------------------------------------
/**
 *  Class just used to return a set of x and y coordinates.
 *
 *  @author Jack Guttman
 *  @version Aug 10, 2015
 */

public class Coord
{
    private int x;
    private int y;

    /**
     * creates a new coordinate object
     * @param x   x coordinate
     * @param y   y coordinate
     */
    public Coord(int x, int y)
    {
        this.setX(x);
        this.setY(y);
    }

    // ----------------------------------------------------------
    /**
     * getter method for x
     * @return the x
     */
    public int getX()
    {
        return x;
    }

    // ----------------------------------------------------------
    /**
     * setter method for x
     * @param x the x to set
     */
    public void setX(int x)
    {
        this.x = x;
    }

    // ----------------------------------------------------------
    /**
     * getter method for y
     * @return the y
     */
    public int getY()
    {
        return y;
    }

    // ----------------------------------------------------------
    /**
     * setter method for y
     * @param y the y to set
     */
    public void setY(int y)
    {
        this.y = y;
    }

}
