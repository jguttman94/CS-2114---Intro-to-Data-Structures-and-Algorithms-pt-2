package cs2114.mazesolver;

import java.util.Stack;
// -------------------------------------------------------------------------
/**
 *  Implements the Maze Interface to represent
 *  the maze using a 2 dimensional array ( same width and height)
 *  Every cell is originally unexplored.
 *
 *  @author Jack G
 *  @version Jul 19, 2015
 */

public class Maze
    implements IMaze
{
    /**
     * field to represent the maze
     */
    private MazeCell[][] maze1;
    /**
     * field to represent location of goal
     */
    private Location goal;
    /**
     * field to represent location of start
     */
    private Location start;
    //----------------------------------------------------------
    /**
     * constructs a new square maze with specific Size x Size (param)
     * @param size      width and height of maze
     */
    public Maze(int size)
    {
        maze1 = new MazeCell[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                maze1[i][j] = MazeCell.UNEXPLORED;
            }
        }
        goal = new Location(size - 1, size - 1);
        start = new Location(0, 0);
    }
    // ----------------------------------------------------------
    /**
     * gets the type of MazeCell at the specified location
     * @param toFind       the location to look for a cell type
     * @return      the type of cell at toFind
     */
    @Override
    public MazeCell getCell(ILocation toFind)
    {
        if (toFind.x() >= this.size()  || toFind.y() >= this.size() ||
            toFind.x() < 0 || toFind.y() < 0)
        {
            return MazeCell.INVALID_CELL;
        }
        return maze1[toFind.x()][toFind.y()];
    }


    // ----------------------------------------------------------
    /**
     * simply returns the location of goal
     * @return  goal    location of goal point
     */
    @Override
    public ILocation getGoalLocation()
    {
        return this.goal;
    }


    // ----------------------------------------------------------
    /**
     * simply returns the location of start
     * @return start    location of starting point
     */
    @Override
    public ILocation getStartLocation()
    {
        return this.start;
    }


    // ----------------------------------------------------------
    /**
     * sets a location in the maze to be a specific cell type
     * @param loc   location in the maze
     * @param type  cell type to be
     */
    @Override
    public void setCell(ILocation loc, MazeCell type)
    {
        //loc must be valid
        //cannot change cell to Wall if loc is goal or start
        if (!(this.getCell(loc).equals(MazeCell.INVALID_CELL)) &&
            (!(loc.equals(start) || loc.equals(goal)) || type != MazeCell.WALL))
        {
            maze1[loc.x()][loc.y()] = type;
        }
    }


    // ----------------------------------------------------------
    /**
     * Sets the goal location of the maze
     * @param sGoal    the new location for goal
     */
    @Override
    public void setGoalLocation(ILocation sGoal)
    {
        if (!this.getCell(sGoal).equals(MazeCell.INVALID_CELL))
        {
            if (this.getCell(sGoal).equals(MazeCell.WALL))
            {
                this.setCell(sGoal, MazeCell.UNEXPLORED);

            }
            this.goal = new Location(sGoal.x(), sGoal.y());
        }
    }


    // ----------------------------------------------------------
    /**
     * Sets the starting location of the  maze
     * @param sStart    the new location for start
     */
    @Override
    public void setStartLocation(ILocation sStart)
    {
        if (!this.getCell(sStart).equals(MazeCell.INVALID_CELL))
        {
            if (this.getCell(sStart).equals(MazeCell.WALL))
            {
                this.setCell(sStart, MazeCell.UNEXPLORED);

            }
            this.start = new Location(sStart.x(), sStart.y());
        }
    }


    // ----------------------------------------------------------
    /**
     * gets the size of the maze, number of cells in either dimension
     * @return      the size of the square maze
     */
    @Override
    public int size()
    {
        return maze1.length;
    }


    // ----------------------------------------------------------
    /**
     * runs the algorithm to solve the maze. explores each path only once
     * before moving on.
     * @return  a string of coordinates of the solution to the maze
     */
    @Override
    public String solve()
    {
        Stack<Location> sPath = new Stack<Location>();
        String solution = "";
        sPath.push(start);
        Location current = sPath.peek();
        this.setCell(current, MazeCell.CURRENT_PATH);

        while (!current.equals(goal))
        {
            boolean fail = false;
            if (this.getCell(current.south()) == MazeCell.UNEXPLORED)
            {
                current = current.south();
            }
            else if (this.getCell(current.east()) == MazeCell.UNEXPLORED)
            {
                current = current.east();
            }
            else if (this.getCell(current.north()) == MazeCell.UNEXPLORED)
            {
                current = current.north();
            }
            else if (this.getCell(current.west()) == MazeCell.UNEXPLORED)
            {
                current = current.west();
            }
            else
            {
                fail = true;
                this.setCell(sPath.pop(), MazeCell.FAILED_PATH);
                if (sPath.size() == 0)
                {
                    return null;
                }
                current = sPath.peek();
            }
            if (!fail)
            {
                sPath.push(current);
                this.setCell(current, MazeCell.CURRENT_PATH);
            }
        }

        while (!sPath.isEmpty())
        {
            solution = sPath.pop().toString() + solution;
        }
        return solution;
    }

}
