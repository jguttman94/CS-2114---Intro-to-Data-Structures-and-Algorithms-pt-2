package cs2114.mazesolver;

import android.widget.TextView;
import sofia.app.ShapeScreen;
import sofia.graphics.Color;
import sofia.graphics.RectangleShape;

// -------------------------------------------------------------------------
/**
 *  Creates the screen for the Maze to be displayed upon. This class
 *  is responsible for creating all the shapes correlating to the different
 *  types of cells in Maze and is also in charge reflecting any changes
 *  made in the maze on the display.
 *
 *  @author  jackrg94
 *  @version 2015.07.27
 */
public class MazeSolverScreen extends ShapeScreen
{
    //~ Fields ................................................................
    private Maze maze1;

    private boolean draw;
    private boolean erase;
    private boolean start;
    private boolean goal;

    private RectangleShape[][] shape1;
    private RectangleShape  rStart;
    private RectangleShape  rGoal;
    private int size;
    private TextView infoLabel;


    //~ Methods ...............................................................

    // ----------------------------------------------------------
    /**
     * sets up the maze into the rectangle shape and creates the
     * cells based on the size of the maze, then fills them with
     * individual rectangle shapes for each cell, then sets the
     * start and goal locations.
     */
    public void initialize()
    {
        maze1 = new Maze(7);

        draw = false;
        erase = false;
        start = false;
        goal = false;

        shape1 = new RectangleShape[7][7];
        float min = Math.min(this.getHeight(), this.getWidth());
        size = (int)min / 7;

        gridAndSetObjectives();
    }
    //-----------------------------------------------------------------------
    /**
     * this method setups the initial maze by creating appropriate
     * number of rectangle shapes and it creates the start and goal
     * of the maze
     */
    public void gridAndSetObjectives()
    {
        //creates a new rectangle shape for each cell in the array
        //assigns color values and adds the shapes to the screen
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                shape1[i][j] =
                    new RectangleShape(i * size, j * size,
                        (i + 1) * size - 1, (j + 1) * size);
                shape1[i][j].setColor(Color.black);
                shape1[i][j].setFillColor(Color.silver);
                this.add(shape1[i][j]);
            }
        }
        //creates the start and goal shapes and sets their colors, then
        //adds them to the screen.
        rStart = new RectangleShape(0, 0, size, size);
        rStart.setColor(Color.red);
        rStart.setFillColor(Color.blue);
        this.add(rStart);

        rGoal = new RectangleShape(6 * size, 6 * size, 7 * size, 7 * size);
        rGoal.setColor(Color.blue);
        rGoal.setFillColor(Color.red);
        this.add(rGoal);
    }
    //-----------------------------------------------------------------------
    /**
     * returns the maze object
     * @return the proper maze object
     */
    public Maze getMaze()
    {
        return maze1;
    }
    //-----------------------------------------------------------------------
    /**
     * responds to drawWalls button clicked
     * turns on draw mode
     */
    public void drawWallsClicked()
    {
        draw = true;
        start = false;
        goal = false;
        erase = false;
    }
    //-----------------------------------------------------------------------
    /**
     * responds to eraseWalls button clicked
     * turns on erase mode
     */
    public void eraseWallsClicked()
    {
        draw = false;
        start = false;
        goal = false;
        erase = true;
    }
    //-----------------------------------------------------------------------
    /**
     * responds to setStart button clicked
     * turns on start mode (allowing start position to be changed)
     */
    public void setStartClicked()
    {
        draw = false;
        start = true;
        goal = false;
        erase = false;
    }
    //-----------------------------------------------------------------------
    /**
     * responds to setGoal button clicked
     * turns on goal mode (allowing goal position to be changed)
     */
    public void setGoalClicked()
    {
        draw = false;
        start = false;
        goal = true;
        erase = false;
    }
    //-----------------------------------------------------------------------
    /**
     * responds to solve button clicked
     * turns on solve mode (shows the solution to the maze, if there is one)
     */
    public void solveClicked()
    {
        //resets the maze before
        //attempting to solve of failed or current attempts
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (maze1.getCell(new Location(i, j)) == MazeCell.CURRENT_PATH
                    || maze1.getCell(new Location(i, j)) ==
                    MazeCell.FAILED_PATH)
                {
                    maze1.setCell(new Location(i, j), MazeCell.UNEXPLORED);
                    shape1[i][j] = this.getShapes()
                        .locatedAt((i + 1) * size, (j + 1) * size)
                        .withClass(RectangleShape.class).back();
                    shape1[i][j].setColor(Color.black);
                    shape1[i][j].setFillColor(Color.silver);
                    this.add(shape1[i][j]);
                }
            }
        }
        //checks if a solution is even possible
        String solveStr = maze1.solve();
        if (solveStr != null)
        {
            infoLabel.setText(solveStr);
        }
        else
        {
            infoLabel.setText("No solution was possible.");
        }
        //displays the paths for failed and current attempts
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                MazeCell cellLoc = maze1.getCell(new Location(i, j));
                if (cellLoc == MazeCell.CURRENT_PATH)
                {
                    shape1[i][j] =
                        this.getShapes()
                            .locatedAt((i + 1) * size - 1, (j + 1) * size)
                            .withClass(RectangleShape.class).back();
                    shape1[i][j].setFillColor(Color.lightYellow);
                }
                else if (cellLoc == MazeCell.FAILED_PATH)
                {
                    this.getShapes()
                        .locatedAt((i + 1) * size, (j + 1) * size)
                        .withClass(RectangleShape.class).back();
                    shape1[i][j].setFillColor(Color.darkOrange);
                }
            }
        }
    }
    //-----------------------------------------------------------------------
    /**
     *called by a touch down event or a touch move event
     *handles which mode is currently active
     *@param x  the x coordinate of the touch event
     *@param y  the y coordinate of the touch event
     */
    public void processTouch(float x, float y)
    {
        //confirms that the touch is within the confines of the maze
        if (x > 0 && y > 0 && x < this.getWidth() && y < this.getHeight())
        {
            //locates which cell the touch was in
            int xCoord = (int)Math.floor(x / size);
            int yCoord = (int)Math.floor(y / size);
            Location touchLocation = new Location(xCoord, yCoord);
            shape1[xCoord][yCoord] =
                this.getShapes().locatedAt(x, y).withClass(
                    RectangleShape.class).front();
            //if draw is clicked, checks to make sure not goal or start
            //then sets the cell to a wall and changes its color
            if (draw) {
                if (shape1[xCoord][yCoord].getFillColor() != Color.blue &&
                    shape1[xCoord][yCoord].getFillColor() != Color.red)
                {
                    maze1.setCell(touchLocation, MazeCell.WALL);
                    shape1[xCoord][yCoord].setFillColor(Color.limeGreen);
                }
            }
            //if erase is clicked, checks to make sure not goal or start
            //then sets the cell to an unexplored cell and changes its color
            else if (erase)
            {
                if (shape1[xCoord][yCoord].getFillColor() != Color.blue &&
                    shape1[xCoord][yCoord].getFillColor() != Color.red)
                {
                    shape1[xCoord][yCoord].setFillColor(Color.silver);
                    maze1.setCell(touchLocation, MazeCell.UNEXPLORED);
                }
            }
            //if start is clicked, changes start location, changes the cell
            //behind it back to the color for unexplored cell
            //then moves the shape associated with start to new location
            else if (start)
            {
                maze1.setStartLocation(touchLocation);
                if (shape1[xCoord][yCoord].getFillColor() == Color.limeGreen)
                {
                    shape1[xCoord][yCoord].setFillColor(Color.silver);
                }
                rStart.setPosition((xCoord + (float)0.5) * size,
                    (yCoord + (float)0.5) * size);
            }
            //if goal is clicked, changes goal location, changes the cell
            //behind it back to the color for unexplored cell
            //then moves the shape associated with goal to new location
            else if (goal)
            {
                maze1.setGoalLocation(touchLocation);
                if (shape1[xCoord][yCoord].getFillColor() == Color.limeGreen)
                {
                    shape1[xCoord][yCoord].setFillColor(Color.silver);
                }
                rGoal.setPosition((xCoord + (float)0.5) * size,
                    (yCoord + (float)0.5) * size);
            }
        }
    }
    //-----------------------------------------------------------------------
    /**
     * handles an occurrence of a touch down event
     * @param x     the x coordinate of the down event
     * @param y     the y coordinate of the down event
     */
    public void onTouchDown(float x, float y)
    {
        this.processTouch(x, y);
    }
    //-----------------------------------------------------------------------
    /**
     * handles an occurrence of a touch move event
     * @param x     the x coordinate of the move event
     * @param y     the y coordinate of the move event
     */
    public void onTouchMove(float x, float y)
    {
        this.processTouch(x, y);
    }
}
