package com.example.jackrg94.app2048;

import sofia.graphics.Shape;
import android.widget.TextView;
import sofia.app.ShapeScreen;


// -------------------------------------------------------------------------
/**
 *  Class to respectively demonstrate the visual representation of
 *  grid class.
 *
 *  @author Jack Guttman
 *  @version Aug 10, 2015
 */
public class App2048Screen extends ShapeScreen
{
    private int tileSize;
    private Grid board;
    private float storeX;
    private float storeY;
    private boolean moveCheck;
    private TextView gameState;
    private Dir lastMove;


    //-----------------------------------------------------------------
    /**
     * initializes the screen with the proper grid and GUI display
     */
    public void initialize()
    {
        board = new Grid(getShapeView().getWidth(), this);
        this.add(board);
        getShapeView().setClickable(true);
        gameState.setText("");
        tileSize = getShapeView().getWidth() / 4;
        this.updateGUI();
    }

    //-----------------------------------------------------------------
    /**
     * updates the display every time by checking Grid's tile locations.
     */
    public void updateGUI()
    {
        this.clear();

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                Tile temp = board.getTile(i, j);
                if (temp != null)
                {
                    temp.setPosition(tileSize * j + tileSize / 2,
                        tileSize * i + tileSize / 2);
                    board.addOther(temp);
                }
            }
        }
    }

    //------------------------------------------------------------------
    /**
     * responds to a touch and drag event
     * @param x         x coord
     * @param y         y coord
     */
    public void onTouchMove(float x, float y)
    {
        moveCheck = true;
    }

    //------------------------------------------------------------------
    /**
     * responds to a touch down event
     * @param x     x coord
     * @param y     y coord
     */
    public void onTouchDown(float x, float y)
    {
        storeX = x;
        storeY = y;
        moveCheck = false;
    }
    //------------------------------------------------------------------
    /**
     * @param x
     * @param y
     */
    public void onTouchUp(float x, float y)
    {
        if (moveCheck) {
            float difX = Math.abs(x - storeX);
            float difY = Math.abs(y - storeY);
            if (difX > difY)
            {
                if (x < storeX)
                {
                    board.moveTiles(Dir.WEST);
                    lastMove = Dir.WEST;
                }
                else
                {
                    board.moveTiles(Dir.EAST);
                    lastMove = Dir.EAST;
                }
            }
            else if (difY > difX)
            {
                if (y < storeY)
                {
                    board.moveTiles(Dir.NORTH);
                    lastMove = Dir.NORTH;
                }
                else
                {
                    board.moveTiles(Dir.SOUTH);
                    lastMove = Dir.SOUTH;
                }
            }
            this.updateGUI();
        }

    }
    //-------------------------------------------------------------------
    /**
     *What to do when reset button is clicked
     */
    public void resetClicked()
    {
        this.initialize();
    }
    //-------------------------------------------------------------------
    /**
     *Empty's the entire board
     */
    public void clear()
    {
        Shape[] tileList =
            getShapeView().getShapes().withClass(Tile.class).all().toArray();
        for (int i = 0; i < tileList.length; i++)
        {
            tileList[i].remove();
        }
    }
    //-------------------------------------------------------------------
    /**
     *Method called when game is over
     */
    public void gameLost()
    {
        gameState.setText("You Lose, get better!");
        getShapeView().setClickable(false);
        this.clear();
    }
    //-------------------------------------------------------------------
    /**
     *Method called when game is won
     */
    public void gameWon()
    {
        gameState.setText("YOU WIN!");
        getShapeView().setClickable(false);
        this.clear();
    }

    // ----------------------------------------------------------
    /**
     * @return the lastMove
     */
    public Dir getLastMove()
    {
        return lastMove;
    }
 // ----------------------------------------------------------
    /**
     * @return the gameState
     */
    public TextView getGameState()
    {
        return gameState;
    }
}
