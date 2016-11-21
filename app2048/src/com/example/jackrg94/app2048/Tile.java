package com.example.jackrg94.app2048;

import sofia.graphics.Color;
import sofia.graphics.RectangleShape;

// -------------------------------------------------------------------------
/**
 *  Specific cells that are used in the Grid class to represent
 *  individual values.
 *
 *  @author Jack Guttman
 *  @version Aug 10, 2015
 */

public class Tile extends RectangleShape
{
    private Number value;
    private Number[] enumList = { Number.TWO, Number.FOUR, Number.EIGHT,
        Number.SIXTEEN, Number.THIRTY_TWO, Number.SIXTY_FOUR,
        Number.ONE_TWENTY_EIGHT, Number.TWO_FIFTY_SIX, Number.FIVE_TWELVE,
        Number.TEN_TWENTY_FOUR, Number.TWENTY_FOURTY_EIGHT};

    // ----------------------------------------------------------
    /**
     * Create a new Tile object.
     * @param size      size of tile
     */
    public Tile(int size)
    {
        super(0, 0, size, size);
        setValue(Number.TWO);
        update();
    }

    // ----------------------------------------------------------
    /**
     * getter method for tile value
     * @return the value
     */
    public Number getValue()
    {
        return value;
    }

    // ----------------------------------------------------------
    /**
     * setter method for tile value
     * @param value the value to set
     */
    public void setValue(Number value)
    {
        this.value = value;
        update();
    }

    //-----------------------------------------------------------
    /**
     *Sets the color of the rectangle shapes to the specific
     *color that is assigned to the value
     */
    private void update()
    {
        //set color
        switch (value)
        {
            case TWO: this.setFillColor(Color.gray);
            this.setColor(Color.black);
            break;

            case FOUR: this.setFillColor(Color.beige);
            this.setColor(Color.black);

            break;
            case EIGHT: this.setFillColor(Color.orange);
            this.setColor(Color.black);

            break;
            case SIXTEEN: this.setFillColor(Color.orangeRed);
            this.setColor(Color.black);

            break;
            case THIRTY_TWO: this.setFillColor(Color.red);
            this.setColor(Color.black);

            break;
            case SIXTY_FOUR: this.setFillColor(Color.yellow);
            this.setColor(Color.black);

            break;
            case ONE_TWENTY_EIGHT: this.setFillColor(Color.yellowGreen);
            this.setColor(Color.black);

            break;
            case TWO_FIFTY_SIX: this.setFillColor(Color.green);
            this.setColor(Color.black);

            break;
            case FIVE_TWELVE: this.setFillColor(Color.lightBlue);
            this.setColor(Color.black);

            break;
            case TEN_TWENTY_FOUR: this.setFillColor(Color.blue);
            this.setColor(Color.black);

            break;
            case TWENTY_FOURTY_EIGHT: this.setFillColor(Color.black);
            this.setColor(Color.black);

            break;
            default: this.setFillColor(Color.white);
            this.setColor(Color.black);

        }
        //set the font (number to display)
    }

    //--------------------------------------------------------------
    /**
     *@param other      the other tile to be compared
     *@return   true if tiles hold same values
     */
    public boolean equals(Tile other)
    {
        if (other != null) {
            return value.equals(other.getValue());
        }
        else
        {
            return false;
        }
    }

    //--------------------------------------------------------------
    /**
     *basic method to return the next incremented enum value
     *2-->4-->8-->16-->32 etc
     */
    public void incrementEnum()
    {
        for (int i = 0; i < enumList.length; i++)
        {
            if (enumList[i].equals(value))
            {
                this.setValue(enumList[i + 1]);
                break;
            }
        }
    }



}
