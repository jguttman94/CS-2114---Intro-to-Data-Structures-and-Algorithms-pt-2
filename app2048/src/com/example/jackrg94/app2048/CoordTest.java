package com.example.jackrg94.app2048;

import junit.framework.TestCase;

/**
 * // -------------------------------------------------------------------------
/**
 *  Test case for coord class
 *
 *  @author Jack Guttman
 *  @version Aug 10, 2015
 */
public class CoordTest
    extends TestCase
{
    private Coord coord;

    /**
     * sets up the test fixtures
     */
    public void setUp()
    {
        coord = new Coord(0, 0);
    }

    /**
     * method to test all methods in coord class
     */
    public void test()
    {
        assertEquals(coord.getX(), 0);
        assertEquals(coord.getY(), 0);
        coord.setX(2);
        coord.setY(5);
        assertEquals(coord.getX(), 2);
        assertEquals(coord.getY(), 5);
    }
}
