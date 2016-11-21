package com.example.jackrg94.app2048;

import sofia.graphics.Color;

/**
 * // -------------------------------------------------------------------------
/**
 *  Tests all methods in Grid class.
 *
 *  @author Jack Guttman
 *  @version Aug 10, 2015
 */
public class GridTest
    extends student.TestCase
{
    private Grid grid1;
    private App2048Screen parent;

    /**
     * sets up test fixtures
     */
    public void setUp()
    {
        parent = new App2048Screen();
        grid1 = new Grid(1000, parent);

    }

    /**
     * test method to check proper responses of grid methods
     */
    public void test1()
    {
        //Could not figure out why shape view was not being initialized
        //all of these tests fail because they have some interaction with
        //shape view.

        assertEquals(grid1.getFillColor(), Color.lightGray);
        assertFalse(grid1.getTile(2, 3).getValue().
            equals(Number.TWENTY_FOURTY_EIGHT));
        grid1.setTile(2, 3, Number.TWENTY_FOURTY_EIGHT);
        assertTrue(grid1.gameWon());
        assertEquals(grid1.getTile(2, 3), Number.TWENTY_FOURTY_EIGHT);
        grid1.gameLost();
        assertEquals(parent.getGameState().getText(),
         "You Lose, get better!");
    }
}
