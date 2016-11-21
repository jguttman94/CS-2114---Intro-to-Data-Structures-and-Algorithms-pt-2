package cs2114.minesweeper;
import sofia.util.Random;

// -------------------------------------------------------------------------
/**
 *  Implements the playing board for the classic game of Mine Sweeper.
 *  the game makes use of MVC design pattern. This only controls the model
 *  by extending the abstract base class.
 *
 *  @author Jack G
 *  @version Jul 12, 2015
 */

public class MineSweeperBoard
    extends MineSweeperBoardBase
{
    //number of columns
    private int width;
    //number of rows
    private int height;
    private MineSweeperCell[][] mineBoard;

    /**
     * boolean to hold value for win/lose
     */
    boolean gameLost;

    //-----------------------------------------------------------------
    /**
     * initializes the mine sweeper board
     * @param width     number of columns
     * @param height    number of rows
     * @param numOfMines    randomly generated number of mines to place
     */
    public MineSweeperBoard(int width, int height, int numOfMines)
    {
        this.width = width;
        this.height = height;
        Random rand = new sofia.util.Random();
        mineBoard = new MineSweeperCell[this.width][this.height];

        for (int x = 0; x < this.width; x++)
        {
            for (int y = 0; y < this.height; y++)
            {
                mineBoard[x][y] = MineSweeperCell.COVERED_CELL;
            }
        }

        int count = 0;
        while (count < numOfMines)
        {
            int randW = rand.nextInt(this.width);
            int randH = rand.nextInt(this.height);
            if (this.getCell(randW, randH) != MineSweeperCell.MINE)
            {
                mineBoard[randW][randH] = MineSweeperCell.MINE;
                count++;
            }
        }
    }

    // ----------------------------------------------------------
    @Override
    /**
     * flags the cell at location arg0, arg1
     * @param x  height location
     * @param y  width location
     */
    public void flagCell(int x, int y)
    {
        if (getCell(x, y) == MineSweeperCell.FLAG)
        {
            setCell(x, y, MineSweeperCell.COVERED_CELL);
        }
        else if (getCell(x, y) == MineSweeperCell.MINE)
        {
            setCell(x, y, MineSweeperCell.FLAGGED_MINE);
        }
        else if (getCell(x, y) == MineSweeperCell.FLAGGED_MINE)
        {
            setCell(x, y, MineSweeperCell.MINE);
        }
        else if (getCell(x, y) == MineSweeperCell.COVERED_CELL)
        {
            setCell(x, y, MineSweeperCell.FLAG);
        }
    }

    // ----------------------------------------------------------
    @Override
    /**
     * retrieves information about what cell is holding what value
     * @param x  int for height
     * @param y     int for width
     * @return value held by board[arg0][arg1]
     */
    public MineSweeperCell getCell(int x, int y)
    {
        if ((x >= 0 && x < width()) && (y >= 0 && y < height()))
        {
            return mineBoard[x][y];
        }
        else {
            return MineSweeperCell.INVALID_CELL;
        }
    }

    // ----------------------------------------------------------
    @Override
    public boolean isGameLost()
    {
        return gameLost;
    }

    // ----------------------------------------------------------
    @Override
    public boolean isGameWon()
    {
        for (int i = 0; i <= this.width; i++)
        {
            for (int j = 0; j <= this.height; j++)
            {
                if (getCell(i, j) == MineSweeperCell.MINE ||
                    getCell(i, j) == MineSweeperCell.FLAG ||
                    getCell(i, j) == MineSweeperCell.UNCOVERED_MINE ||
                    getCell(i, j) == MineSweeperCell.COVERED_CELL)
                {
                    return false;
                }
            }
        }
        return true;
    }


    // ----------------------------------------------------------
    @Override
    /**
     * checks all adjacent tiles for mines and returns the number
     * @param x     int for height
     * @param y     int for width
     * @return      numb of adjacent mines
     */
    public int numberOfAdjacentMines(int x, int y)
    {
        int count = 0;
        //dont have to check for exceptions since getCell() already does that
        for (int i = y - 1; i <= y + 1; i++)
        {
            for (int j = x - 1; j <= x + 1; j++)
            {
                if (getCell(j, i) == MineSweeperCell.MINE ||
                    getCell(j, i) == MineSweeperCell.FLAGGED_MINE ||
                    getCell(j, i) == MineSweeperCell.UNCOVERED_MINE)
                {
                    count++;
                }
            }
        }
        return count;
    }

    // ----------------------------------------------------------
    @Override
    public void revealBoard()
    {
        for (int i = 0; i < this.width; i++)
        {
            for (int j = 0; j < this.height; j++)
            {
                this.uncoverCell(i, j);
                if (getCell(i, j) == MineSweeperCell.FLAG)
                {
                    mineBoard[i][j] =
                        MineSweeperCell.adjacentTo(numberOfAdjacentMines(i, j));
                }
                if (getCell(i, j) == MineSweeperCell.FLAGGED_MINE)
                {
                    mineBoard[i][j] = MineSweeperCell.UNCOVERED_MINE;
                }
            }
        }
    }

    // ----------------------------------------------------------
    @Override
    /**
     * if the specified cell (by first two arguments) is not an invalid cell
     * it will set that cell to new value
     * @param x  value of specific height index
     * @param y  value of specific width index
     * @param z  new cell value to input
     */
    protected void setCell(int x, int y, MineSweeperCell z)
    {
        if (this.getCell(x, y) != MineSweeperCell.INVALID_CELL)
        {
            mineBoard[x][y] = z;
        }
    }

    // ----------------------------------------------------------
    /**
     * uncovers specified cell, changes to number of adjacent mines
     * if cell underneath contains mine, game is over
     * @param x     value for height
     * @param y     value for width
     */
    @Override
    public void uncoverCell(int x, int y)
    {
        MineSweeperCell cell = getCell(x, y);
        if (cell != MineSweeperCell.FLAG
            && cell != MineSweeperCell.FLAGGED_MINE
            && cell != MineSweeperCell.INVALID_CELL)
        {
            if (cell != MineSweeperCell.MINE)
            {
                mineBoard[x][y] =
                    MineSweeperCell.adjacentTo(numberOfAdjacentMines(x, y));
            }
            else
            {
                mineBoard[x][y] =
                    MineSweeperCell.UNCOVERED_MINE;
                gameLost = true;
            }
        }
    }

    // ----------------------------------------------------------
    @Override
    /**
     * simple getter for width of board
     * @return  width
     */
    public int width()
    {
        return mineBoard.length;
    }

    // ----------------------------------------------------------
    @Override
    /**
     * simple getter for height value of board
     * @return  height
     */
    public int height()
    {
        return mineBoard[0].length;
    }



}
