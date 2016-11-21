package com.example.jackrg94.app2048;

import sofia.graphics.Color;
import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 *  Tests all methods and responses in Tile class.
 *
 *  @author Jack Guttman
 *  @version Aug 10, 2015
 */
public class TileTest
    extends TestCase
{
    private Tile tile;
    public void setUp()
    {
        tile = new Tile(200);
    }

    /**
     * tests proper initialization and colors
     */
    public void test1()
    {
        assertEquals(tile.getValue(), Number.TWO);
        tile.setValue(Number.FOUR);
        assertEquals(tile.getValue(), Number.FOUR);
        assertEquals(tile.getFillColor(), Color.beige);
        for (int i = 0; i < 9; i++)
        {
            tile.incrementEnum();
        }
        assertEquals(tile.getValue(), Number.TWENTY_FOURTY_EIGHT);
    }

    /**
     * tests equals method
     */
    public void testEquals()
    {
        assertTrue(tile.equals(new Tile(200)));
        tile.setValue(Number.FOUR);
        assertFalse(tile.equals(new Tile(200)));
    }
}
