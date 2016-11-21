package cs2114.mazesolver;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// -------------------------------------------------------------------------
/**
 *  Tests the maze class and all of its constructors and methods.
 *
 *  @author Jack G
 *  @version Jul 21, 2015
 */

public class MazeTest
{
    /**
     * test maze field
     */
    private Maze testMaze;
    // ----------------------------------------------------------
    /**
     * sets up each test before being run
     * @throws java.lang.Exception
     */
    @Before
    public void setUp()
        throws Exception
    {
        testMaze = new Maze(5);
    }
    //-----------------------------------------------------------
    /**
     * tests that the constructor sets up the Maze properly
     */
    @Test
    public void testConstructor()
    {
        Maze m1 = new Maze(7);
        assertEquals(m1.size(), 7);
        assertEquals(m1.getCell(new Location(4, 5)), MazeCell.UNEXPLORED);
        assertEquals(m1.getCell(new Location(1, 0)), MazeCell.UNEXPLORED);
        assertEquals(m1.getStartLocation(), new Location(0, 0));
        assertEquals(m1.getGoalLocation(), new Location(6, 6));
    }
    //-----------------------------------------------------------
    /**
     * tests that the maze is the correct size square
     */
    @Test
    public void testSize()
    {
        assertEquals(testMaze.size(), 5);
    }
    //-----------------------------------------------------------
    /**
     * tests that one can reassign the starting location in maze
     */
    @Test
    public void testSetStartLocation()
    {
        Location start1 = new Location(3, 4);
        testMaze.setStartLocation(start1);
        assertEquals(testMaze.getStartLocation(), start1);

        testMaze.setCell(start1, MazeCell.WALL);
        testMaze.setStartLocation(start1);
        assertEquals(testMaze.getStartLocation(), start1);

        testMaze.setStartLocation(new Location(7, 9));
        assertEquals(testMaze.getStartLocation(), start1);
        testMaze.setStartLocation(new Location(-1, 5));
        assertEquals(testMaze.getStartLocation(), start1);

        testMaze.setCell(new Location(0, 0), MazeCell.WALL);
        testMaze.setStartLocation(new Location(0, 0));
        assertEquals(new Location(0, 0), testMaze.getStartLocation());
        assertEquals(MazeCell.UNEXPLORED, testMaze.getCell(new Location(0, 0)));
    }
    //-----------------------------------------------------------
    /**
     * tests that one can reassign the goal location in maze
     */
    @Test
    public void testSetGoalLocation()
    {
        Location goal1 = new Location(3, 4);
        testMaze.setGoalLocation(goal1);
        assertEquals(testMaze.getGoalLocation(), goal1);

        testMaze.setCell(goal1, MazeCell.WALL);
        testMaze.setGoalLocation(goal1);
        assertEquals(testMaze.getGoalLocation(), goal1);

        testMaze.setGoalLocation(new Location(7, 9));
        assertEquals(testMaze.getGoalLocation(), goal1);
        testMaze.setGoalLocation(new Location(-1, 5));
        assertEquals(testMaze.getGoalLocation(), goal1);

        testMaze.setCell(new Location(4, 4), MazeCell.WALL);
        testMaze.setGoalLocation(new Location(4, 4));
        assertEquals(new Location(4, 4), testMaze.getGoalLocation());
        assertEquals(MazeCell.UNEXPLORED, testMaze.getCell(new Location(4, 4)));
    }
    //-----------------------------------------------------------
    /**
     * tests that one can reassign a cell at specific location
     */
    @Test
    public void testSetCell()
    {
        Location x = new Location(2, 2);
        testMaze.setCell(x, MazeCell.WALL);
        assertEquals(testMaze.getCell(x), MazeCell.WALL);

        testMaze.setCell(x, MazeCell.UNEXPLORED);
        assertEquals(testMaze.getCell(x), MazeCell.UNEXPLORED);
        testMaze.setStartLocation(x);
        assertEquals(testMaze.getCell(x), MazeCell.UNEXPLORED);
        testMaze.setGoalLocation(x);
        assertEquals(testMaze.getCell(x), MazeCell.UNEXPLORED);

        Location inval = new Location(-1, 0);
        testMaze.setCell(inval, MazeCell.WALL);
        assertEquals(testMaze.getCell(inval), MazeCell.INVALID_CELL);
    }
    //---------------------------------------------------------------
    /**
     * tests that the correct start location is retrieved
     */
    @Test
    public void testGetStartLocaton()
    {
        testMaze.setStartLocation(new Location(4, 4));
        assertEquals(testMaze.getStartLocation(), new Location(4, 4));

        testMaze.setStartLocation(new Location(6, 5));
        assertEquals(testMaze.getStartLocation(), new Location(4, 4));
    }
    //----------------------------------------------------------------
    /**
     * tests that the correct goal location is retrieved
     */
    @Test
    public void testGetGoalLocation()
    {
        testMaze.setGoalLocation(new Location(3, 3));
        assertEquals(testMaze.getGoalLocation(), new Location(3, 3));

        testMaze.setGoalLocation(new Location(7, 7));
        assertEquals(testMaze.getGoalLocation(), new Location(3, 3));
    }
    //-----------------------------------------------------------------
    /**
     * tests that the proper cell ILocation is retrieved from correct array spot
     */
    @Test
    public void testGetCell()
    {
        assertEquals(testMaze.getCell(new Location(6, 5)),
            MazeCell.INVALID_CELL);
        assertEquals(testMaze.getCell(new Location(6, 6)),
            MazeCell.INVALID_CELL);
        assertEquals(testMaze.getCell(new Location(5, 6)),
            MazeCell.INVALID_CELL);
        assertEquals(testMaze.getCell(new Location(0, -1)),
            MazeCell.INVALID_CELL);
        assertEquals(testMaze.getCell(new Location(-1, 0)),
            MazeCell.INVALID_CELL);

        testMaze.setCell(new Location(3, 3), MazeCell.WALL);
        assertEquals(testMaze.getCell(new Location(3, 3)),
            MazeCell.WALL);
    }
    //------------------------------------------------------------------
    /**
     * tests that the maze is solved correctly
     */
    @Test
    public void testSolve()
    {
        String string =
            "(0, 0)(0, 1)(0, 2)(0, 3)(0, 4)(1, 4)(2, 4)(3, 4)(4, 4)";
        assertEquals(string, testMaze.solve());

        Maze testMaze2 = new Maze(5);
        testMaze2.setCell(new Location(0, 1), MazeCell.WALL);
        testMaze2.setCell(new Location(2, 3), MazeCell.WALL);
        testMaze2.setCell(new Location(2, 4), MazeCell.WALL);
        string = "(0, 0)(1, 0)(1, 1)(1, 2)(2, 2)(3, 2)(3, 3)(3, 4)(4, 4)";
        assertEquals(string, testMaze2.solve());

        Maze testMaze3 = new Maze(5);
        testMaze3.setCell(new Location(0, 1), MazeCell.WALL);
        testMaze3.setCell(new Location(1, 2), MazeCell.WALL);
        testMaze3.setCell(new Location(2, 3), MazeCell.WALL);
        testMaze3.setCell(new Location(3, 3), MazeCell.WALL);
        string = "(0, 0)(1, 0)(1, 1)(2, 1)(2, 2)(3, 2)(4, 2)(4, 3)(4, 4)";
        assertEquals(string, testMaze3.solve());

        Maze testMaze4 = new Maze(5);
        testMaze4.setCell(new Location(0, 1), MazeCell.WALL);
        testMaze4.setCell(new Location(1, 0), MazeCell.WALL);
        assertNull(testMaze4.solve());

        Maze testMaze5 = new Maze(5);
        testMaze5.setStartLocation(new Location(2, 2));
        testMaze5.setGoalLocation(new Location(0, 0));
        testMaze5.setCell(new Location(1, 2), MazeCell.WALL);
        testMaze5.setCell(new Location(2, 1), MazeCell.WALL);
        testMaze5.setCell(new Location(3, 2), MazeCell.WALL);
        testMaze5.setCell(new Location(2, 3), MazeCell.WALL);
        assertNull(testMaze5.solve());

        Maze testMaze6 = new Maze(5);
        testMaze6.setStartLocation(new Location(2, 2));
        testMaze6.setGoalLocation(new Location(2, 3));
        string = "(2, 2)(2, 3)";
        assertEquals(string, testMaze6.solve());

    }
}
