package cs2114.mazesolver;

// -------------------------------------------------------------------------
/**
 *  A class implementation of ILocation (Location Interface). Locations
 *  do not have a reference back to a maze, they represent (x, y) values
 *  only.
 *
 *  @author Jack G
 *  @version Jul 19, 2015
 */

public class Location
    implements ILocation
{
    private int x;
    private int y;
    //----------------------------------------------------------------------
    /**
     * Constructor that sets up variables for x and y coordinates.
     * @param x     represents x coordinate
     * @param y     represents y coordinate
     */
    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    //----------------------------------------------------------------------
    /**
     * An overridden equals(Object) method that returns true
     * if the object passed to it is another Location that has
     * the same x- and y-coordinates.
     * @param obj       the object to check locations with
     * @return          true if same location, false if not.
     */
    @Override
    public boolean equals(Object obj)
    {
        boolean returnB;
        //handle objects of different type (false)
        //handle null exceptions (false)
        if (!(obj instanceof Location))
        {
            returnB = false;
        }
        //handle objects of same type
        else
        {
            //objects have same x and y coordinates
            if (this.x() == ((Location)obj).x() &&
                this.y() == ((Location)obj).y())
            {
                returnB = true;
            }
            //objects don't have same x and y
            else
            {
                returnB = false;
            }
        }
        return returnB;
    }
    //----------------------------------------------------------------------
    /**
     * Override of toString method returns coordinates in formatted form
     * @return  ...(x, y)...
     */
    public String toString()
    {
        return "(" + this.x() + ", " + this.y() + ")";
    }
    // ----------------------------------------------------------
    @Override
    /**
     *  Gets a new location that represents the (x, y)
     *  coordinates one cell east of this location.
     *  This method should not perform any bounds checking,
     *  because locations are simply (x, y) pairs.
     *
     * @return   a new location representing the (x, y)
     *           coordinates one cell east of this location
     */
    public Location east()
    {
        Location newLoc = new Location(this.x() + 1, this.y());
        return newLoc;
    }


    // ----------------------------------------------------------
    @Override
    /**
     *  Gets a new location that represents the (x, y)
     *  coordinates one cell north of this location.
     *  This method should not perform any bounds checking,
     *  because locations are simply (x, y) pairs.
     *
     * @return   a new location representing the (x, y)
     *           coordinates one cell north of this location
     */
    public Location north()
    {
        Location newLoc = new Location(this.x(), this.y() - 1);
        return newLoc;
    }


    // ----------------------------------------------------------
    @Override
    /**
     *  Gets a new location that represents the (x, y)
     *  coordinates one cell south of this location.
     *  This method should not perform any bounds checking,
     *  because locations are simply (x, y) pairs.
     *
     * @return   a new location representing the (x, y)
     *           coordinates one cell south of this location
     */
    public Location south()
    {
        Location newLoc = new Location(this.x(), this.y() + 1);
        return newLoc;
    }


    // ----------------------------------------------------------
    @Override
    /**
     *  Gets a new location that represents the (x, y)
     *  coordinates one cell west of this location.
     *  This method should not perform any bounds checking,
     *  because locations are simply (x, y) pairs.
     *
     * @return   a new location representing the (x, y)
     *           coordinates one cell west of this location
     */
    public Location west()
    {
        Location newLoc = new Location(this.x() - 1, this.y());
        return newLoc;
    }


    // ----------------------------------------------------------
    @Override
    /**
     * Gets the x coordinate
     * @return      x coordinate
     */
    public int x()
    {
        return this.x;
    }


    // ----------------------------------------------------------
    @Override
    /**
     * Gets the y coordinate
     * @return      y coordinate
     */
    public int y()
    {
        return this.y;
    }

}
