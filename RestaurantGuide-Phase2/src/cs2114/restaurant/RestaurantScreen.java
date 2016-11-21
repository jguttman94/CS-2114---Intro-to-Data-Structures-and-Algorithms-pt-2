package cs2114.restaurant;

import java.util.List;
import android.widget.*;
import android.net.Uri;
import sofia.app.Screen;
import sofia.widget.ImageView;
import sofia.content.ContentViewer;
import realtimeweb.yelp.*;
import realtimeweb.yelp.exceptions.BusinessSearchException;


//-------------------------------------------------------------------------
/**
*  Responsible for displaying the proper information on the
*  restaurant screen. Also correctly handles any user input data.
*
* @author jackrg94
* @version 2015.08.09
*/
public class RestaurantScreen
    extends Screen implements BusinessSearchListener
{
    // ~ Fields ................................................................
    /**
     * the field to use when calling a business search event
     */
    BusinessSearch search;
    /**
     * the circular linked list created by that class, responsible for
     * holding all the businesses
     */
    CircularLinkedList<Business> restaurant;

    //holds all the fields for the types of widgets in the gui
    private RatingBar   ratingBar;
    private Button      viewMap;
    private ImageView   imageView;
    private Button      next;
    private EditText    searchField;
    private TextView    restaurantName;
    private TextView    numRatings;
    private Button      previous;

    // ~ Public methods ........................................................
    /**
     *starts the screen
     */
    public void initialize()
    {
        //fills the search list with businesses, sets all buttons to be
        //toggled off
        search = BusinessSearch.getInstance();
        restaurant = new CircularLinkedList<Business>();
        button(false);
    }

    //-------------------------------------------------------------------------
    @Override
    /**
     *Override for implementation of businesssearchlistener. What occurs
     *when a search is completed
     */
    public void businessSearchCompleted(SearchResponse res1)
    {
        //if there is not a list of businesses
        if (res1.getBusinesses().isEmpty())
        {
            button(false);
        }
        //if there is a list of businesses
        else
        {
            //toggle the button and clear the circularlinkedlist
            button(true);
            restaurant.clear();
            List<Business> tempList = res1.getBusinesses();
            //creates a new list to store the businesses in
            int size = tempList.size() - 1;
            for (int i = size; i >= 0; i--)
            {
                restaurant.add(tempList.get(i));
            }
            changeOccur();
        }
    }
    //----------------------------------------------------------------------
    @Override
    /**
     *Override for implementation of businesssearchlistener. What occurs
     *when a search fails
     */
    public void businessSearchFailed(BusinessSearchException e)
    {
        button(false);
    }
    //......................................................................
    /**
     *Response when the search field is edited by user
     */
    public void searchFieldEditingDone()
    {
        String str = searchField.getText().toString();
        search.searchBusinesses(
            new BusinessQuery(str), new BusinessSearchGUIAdapter(this));
    }
    //----------------------------------------------------------------------
    /**
     *What to do when the next button is clicked
     */
    public void nextClicked()
    {
        restaurant.next();
        changeOccur();
    }
    //----------------------------------------------------------------------
    /**
     *What to do when the previous button is clicked
     */
    public void previousClicked()
    {
        restaurant.previous();
        changeOccur();
    }
    //----------------------------------------------------------------------
    /**
     *What to do when the map button is clicked. Retrieves lat and long to
     *input into the google map search
     */
    public void viewMapClicked()
    {
        double lat = restaurant.getCurrent().getLocation().getLatitude();
        double lon = restaurant.getCurrent().getLocation().getLongitude();
        String uriStr = "http://maps.google.com/maps?q=";
        Uri uri = Uri.parse(uriStr + lat + "," + lon);
        new ContentViewer(uri).start(this);
    }
    //-----------------------------------------------------------------------
    /**
     *this method is called when a button is clicked causing a change
     *in the appearance of the screen
     */
    public void changeOccur()
    {
        //retrieves info for number of ratings, sets the average rating
        //and sets the restaurants name, then sets the image
        numRatings.setText(
            Integer.toString(restaurant.getCurrent().getReviewCount()));

        ratingBar.setRating(restaurant.getCurrent().getRating());

        restaurantName.setText(restaurant.getCurrent().getName());

        if (restaurant.getCurrent().getImageUrl() != null)
        {
            imageView.setImageURI(
                Uri.parse(restaurant.getCurrent().getImageUrl()));
        }
        else
        {
            imageView.setImageURI(null);
        }
    }
    //-----------------------------------------------------------------------
    /**
     * sets the buttons to enabled when called from a dif method
     *@param enable     true or false field
     */
    public void button(boolean enable)
    {
        previous.setEnabled(enable);
        next.setEnabled(enable);
        viewMap.setEnabled(enable);
    }
}
