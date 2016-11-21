package cs2114.restaurant;

import android.widget.*;
import android.content.Intent;
import realtimeweb.yelp.exceptions.BusinessSearchException;

// -------------------------------------------------------------------------
/**
 *  Tests all the proper responses from RestaurantScreen methods.
 *
 * @author jackrg94
 * @version 2015.08.09
 */
public class RestaurantScreenTests
    extends student.AndroidTestCase<RestaurantScreen>
{
    // ~ Fields ................................................................

    private Button viewMap;
    private Button next;
    private Button previous;
    private TextView numRatings;
    private EditText searchField;
    private TextView restaurantName;
    private RatingBar ratingBar;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public RestaurantScreenTests()
    {
        super(RestaurantScreen.class);
    }


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Initializes the text fixtures.
     */
    public void setUp()
    {
        // Nothing here
    }

    //-----------------------------------------------------------------------
    /**
     * tests the search fields proper response
     */
    public void testSearchField()
    {
        enterText(searchField, "Blacksburg, VA");
        assertEquals(20, getScreen().restaurant.size());
    }
    /**
     * tests the next buttons proper response
     */
    public void testNext()
    {
        enterText(searchField, "Blacksburg, VA");
        click(next);
        assertEquals("Carol Lee Donuts", restaurantName.getText());
        assertEquals("16", numRatings.getText());
        assertEquals(5.0, 0.1, ratingBar.getRating());
    }
    /**
     * tests the previous buttons proper response
     */
    public void testPrevious()
    {
        enterText(searchField, "Blacksburg, VA");
        click(previous);
        assertEquals("Virginia Polytechnic Institute and State University",
            restaurantName.getText());
        assertEquals("5", numRatings.getText());
        assertEquals(4.5, 0.1, ratingBar.getRating());
    }
    /**
     * tests the map button proper response
     */
    public void testViewMap()
    {
        enterText(searchField, "Blacksburg, VA");
        prepareForUpcomingActivity(Intent.ACTION_VIEW);
        click(viewMap);
        assertEquals(getScreen().restaurant.size(), 20);
    }
    /**
     * tests proper response for when the business search fails
     */
    public void testBusinessSearchFailed()
    {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run()
            {
                getScreen().businessSearchFailed(
                    new BusinessSearchException("", "", ""));
            }
        });
        assertEquals(false, next.isEnabled());
        assertEquals(false, previous.isEnabled());
        assertEquals(false, viewMap.isEnabled());
    }
}
