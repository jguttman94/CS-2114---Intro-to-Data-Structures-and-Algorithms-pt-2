package com.example.jackrg94.app2048;

import sofia.graphics.Color;
import sofia.graphics.RectangleShape;
import sofia.util.Random;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 *  Contains all logic for 2048 game. Creates a board to hold
 *  all the values of the tiles and appropriately move them when
 *  acted upon.
 *
 *  @author Jack Guttman
 *  @version Aug 10, 2015
 */

public class Grid extends RectangleShape
{
    private Tile[][] board;
    private boolean checkMove;
    private int size;
    private App2048Screen parent;

    //-----------------------------------------------------------------
    /**
     * creates a new Grid object.
     * @param size      size of grid x size of grid
     * @param parent    the screen's activity
     */
    public Grid(int size, App2048Screen parent)
    {
        super(0, 0, size, size);
        this.parent = parent;
        this.size = size;
        this.setFillColor(Color.lightGray);
        board = new Tile[4][4];
        Coord coord = findEmptyRand();
        board[coord.getY()][coord.getX()] = new Tile(size / 4);
        Coord coord2 = findEmptyRand();
        board[coord2.getY()][coord2.getX()] = new Tile(size / 4);
    }

    //-----------------------------------------------------------------
    /**
     * method to check if a 2048 tile has appeared on board
     * @return  true if game is won
     */
    public boolean gameWon()
    {
        boolean won = false;
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j <board[0].length; j++)
            {
                won = won || board[i][j].equals(Number.TWENTY_FOURTY_EIGHT);
            }
        }
        return won;
    }

    //----------------------------------------------------------------
    /**
     * returns the tile at specific location
     * @param i     row
     * @param j     column
     * @return      the tile at loc
     */
    public Tile getTile(int i, int j)
    {
        return board[i][j];
    }
    //----------------------------------------------------------------
    /**
     * sets the specific tile to a certain type
     * @param i    x coordinate
     * @param j    y coordinate
     * @param value   value to set
     */
    public void setTile(int i, int j, Number value)
    {
        board[i][j].setValue(value);
    }

    //game lose

    //-------------------------------------------------------------------
    /**
     *method that is executed when conditions for loss are met
     */
    public void gameLost()
    {
        parent.gameLost();
    }

    //score

    //----------------------------------------------------------------
    /**
     * uses a switch to move tiles in a certain direction.
     *@param dirToMove      the directional input
     */
    public void moveTiles(Dir dirToMove)
    {
        checkMove = true;
        switch (dirToMove)
        {
            case NORTH: moveNorth(); moveNorth(); moveNorth();
                break;
            case SOUTH: moveSouth(); moveSouth(); moveSouth();
                break;
            case WEST:  moveWest(); moveWest(); moveWest();
                break;
            case EAST:  moveEast(); moveEast(); moveEast();
                break;
            default: break;
        }
        Coord coord;
        if (checkMove) {
            gameLost();
        }
        else {
            coord = findEmptyRand();
            board[coord.getY()][coord.getX()] = new Tile(size / 4);
        }


    }

    //----------------------------------------------------------------
    /**
     *looks for an empty tile space and returns their coordinates
     *@return   set of x and y coordinates
     */
    public Coord findEmptyRand()
    {
        ArrayList<Coord> ans = new ArrayList<Coord>();
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (board[i][j] == null)
                {
                    ans.add(new Coord(j, i));
                }
            }
        }
        if (!ans.isEmpty())
        {
            int randIndex = Random.generator().nextInt(ans.size());
            return ans.get(randIndex);
        }
        else {
            gameLost();
            return null;
        }
    }

    //----------------------------------------------------------------
    /**
     *Method that is executed to move pieces downwards
     */
    private void moveSouth()
    {
        for (int i = 2; i >= 0; i--)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] != null && board[i][j].equals(board[i + 1][j]))
                {
                    board[i + 1][j].incrementEnum();
                    board[i][j] = null;
                    checkMove = false;
                }
                else if (board[i + 1][j] == null)
                {
                    board[i + 1][j] = board[i][j];
                    board[i][j] = null;
                    checkMove = false;
                }
                else
                {
                    checkMove = true && checkMove;
                }
            }
        }
    }

    //----------------------------------------------------------------
    /**
     *Method that is executed to move pieces upwards
     */
    private void moveNorth()
    {
        for (int i = 1; i <= 3; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] != null && board[i][j].equals(board[i - 1][j]))
                {
                    board[i - 1][j].incrementEnum();
                    board[i][j] = null;
                    checkMove = false;

                }
                else if (board[i - 1][j] == null)
                {
                    board[i - 1][j] = board[i][j];
                    board[i][j] = null;
                    checkMove = false;

                }
                else
                {
                    checkMove = true && checkMove;
                }
            }
        }

    }

    //----------------------------------------------------------------
    /**
     *method that is executed to move pieces to the right
     */
    private void moveEast()
    {
        for (int j = 2; j >= 0; j--)
        {
            for (int i = 0; i < board[0].length; i++)
            {
                if (board[i][j] != null && board[i][j].equals(board[i][j + 1]))
                {
                    board[i][j + 1].incrementEnum();
                    board[i][j] = null;
                    checkMove = false;

                }
                else if (board[i][j + 1] == null)
                {
                    board[i][j + 1] = board[i][j];
                    board[i][j] = null;
                    checkMove = false;

                }
                else
                {
                    checkMove = true && checkMove;
                }
            }
        }

    }

    //----------------------------------------------------------------
    /**
     *method that is executed to move pieces to the left
     */
    private void moveWest()
    {
        for (int j = 1; j <= 3; j++)
        {
            for (int i = 0; i < board[0].length; i++)
            {
                if (board[i][j] != null && board[i][j].equals(board[i][j - 1]))
                {
                    board[i][j - 1].incrementEnum();
                    board[i][j] = null;
                    checkMove = false;

                }
                else if (board[i][j - 1] == null)
                {
                    board[i][j - 1] = board[i][j];
                    board[i][j] = null;
                    checkMove = false;

                }
                else
                {
                    checkMove = true && checkMove;
                }
            }
        }

    }
    //----------------------------------------------------------------
    /**
     *Clears the entire board
     */
    public void clear()
    {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                if (board[i][j] != null) {
                    board[i][j].remove();
                }
            }
        }
    }

}
