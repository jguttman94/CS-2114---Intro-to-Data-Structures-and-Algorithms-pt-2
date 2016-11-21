package cs2114.mazesolver;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// -------------------------------------------------------------------------
/**
 *  tests the class Location to confirm that its methods and constructor(s)
 *  work properly
 *
 *  @author Jack G
 *  @version Jul 19, 2015
 */

public class LocationTest
{
    private Location testLoc;
    private ILocation temp;
    private Location testLoc2;
    private Location testLoc3;
    private Location testLoc4;
    private Location testLoc5;
    private Object obj;
    // ----------------------------------------------------------
    /**
     * sets up all the tests before being run.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp()
        throws Exception
    {
        testLoc = new Location(4, 5);
        testLoc2 = new Location(4, 5);
        testLoc3 = new Location(3, 5);
        testLoc4 = new Location(4, 6);
        testLoc5 = new Location(0, 0);
        temp = null;
        obj = new Location(0, 0);
    }

    //-----------------------------------------------------------
    /**
     * tests that x coordinate is retrieved properly
     */
    @Test
    public void testX()
    {
        assertEquals(testLoc.x(), 4);
    }
    //-----------------------------------------------------------
    /**
     * tests that y coordinate is retrieved properly
     */
    @Test
    public void testY()
    {
        assertEquals(testLoc.y(), 5);
    }
    //-----------------------------------------------------------
    /**
     * tests that method east() returns correct value
     * x coordinate + 1
     */
    @Test
    public void testEast()
    {
        temp = testLoc.east();
        assertEquals(temp, new Location(5, 5));
        assertEquals(temp.x(), 5);
        assertEquals(temp.y(), 5);
    }
    //-----------------------------------------------------------
    /**
     * tests that method west() returns correct value
     * x coordinate - 1
     */
    @Test
    public void testWest()
    {
        temp = testLoc.west();
        assertEquals(temp, new Location(3, 5));
        assertEquals(temp.x(), 3);
        assertEquals(temp.y(), 5);
    }
    //-----------------------------------------------------------
    /**
     * tests that method north() returns correct value
     * y coordinate - 1
     */
    @Test
    public void testNorth()
    {
        temp = testLoc.north();
        assertEquals(temp, new Location(4, 4));
        assertEquals(temp.x(), 4);
        assertEquals(temp.y(), 4);
    }
    //-----------------------------------------------------------
    /**
     * tests that method south() returns correct value
     * y coordinate  + 1
     */
    @Test
    public void testSouth()
    {
        temp = testLoc.south();
        assertEquals(temp, new Location(4, 6));
        assertEquals(temp.x(), 4);
        assertEquals(temp.y(), 6);
    }
    //-----------------------------------------------------------
    /**
     * tests that toString returns the coordinates as a pair
     * between parentheses and separated by a comma
     */
    @Test
    public void testToString()
    {
        assertEquals(testLoc.toString(), "(4, 5)");
    }
    //-----------------------------------------------------------
    /**
     * tests that equals(Object) returns true if two Locations have
     * same x and y coordinates, false if object is not of type
     * Location, and false if object is null
     */
    @Test
    public void testEquals()
    {
        //tests if objects are of same type and same coordinates
        assertEquals(testLoc.equals(testLoc2), true);
        //tests if objects are of same type and differing x coordinates
        assertFalse(testLoc.equals(testLoc3));
        //tests if objects are of same type and differing y coordinates
        assertFalse(testLoc.equals(testLoc4));
        //tests if objects are of same type and differing x & y coords
        assertFalse(testLoc.equals(testLoc5));

        //tests if objects are not of same type
        assertFalse(testLoc.equals(obj));
        //tests if object is null
        assertFalse(testLoc.equals(temp));
    }

}
