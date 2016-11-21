package cs2114.mazesolver;

import android.widget.Button;
import android.widget.TextView;
import sofia.graphics.ShapeView;

//-------------------------------------------------------------------------
/**
*  Tests the maze solver screen class and all its proper methods.
*  looks for correctness in its GUI
*
*  @author  jackrg94
*  @version 2015.07.27
*/
public class MazeSolverScreenTests
    extends student.AndroidTestCase<MazeSolverScreen>
{
    //~ Fields ................................................................

    private ShapeView shapeView;
    private TextView infoLabel;
    // This field will store the pixel width/height of a cell in the maze.
    private int cellSize;
    private Maze maze1;
    private Button solve;
    private Button setGoal;
    private Button setStart;
    private Button drawWalls;
    private Button eraseWalls;


    //~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public MazeSolverScreenTests()
    {
        super(MazeSolverScreen.class);
    }


    //~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Initializes the text fixtures.
     */
    public void setUp()
    {
        float viewSize =
            Math.min(shapeView.getWidth(), shapeView.getHeight());

        cellSize = (int)viewSize / 7;
        maze1 = getScreen().getMaze();
    }
    //~ Private methods .......................................................

    // ----------------------------------------------------------
    /**
     * Simulates touching down on the middle of the specified cell in the maze.
     */
    private void touchDownCell(int x, int y)
    {
        touchDown(shapeView, (x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }


    // ----------------------------------------------------------
    /**
     * Simulates moving the finger instantaneously to the middle of the
     * specified cell in the maze.
     */
    private void touchMoveCell(int x, int y)
    {
        touchMove((x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }
    // ----------------------------------------------------------
    /**
     * Simulates clicking the middle of the specified cell in the maze. This is
     * equivalent to calling: touchDownCell(x, y); touchUp();
     */
    private void clickCell(int x, int y)
    {
        touchDownCell(x, y);
        touchUp();
    }
    //-------------------------------------------------------------
    /**
     * tests that when in draw wall mode, walls are placed properly
     */
    public void testDrawWall()
    {
        this.clickCell(1, 4);
        assertEquals(MazeCell.UNEXPLORED, maze1.getCell(new Location(1, 4)));
        click(drawWalls);
        this.clickCell(1, 4);
        assertEquals(MazeCell.WALL, maze1.getCell(new Location(1, 4)));

        //tests when out of bounds
        click(drawWalls);
        this.touchDownCell(2, 0);
        this.touchMoveCell(2,  -1);
        this.touchUp();
        assertEquals(MazeCell.WALL, maze1.getCell(new Location(2, 0)));

        //tests when clicked on start or goal
        this.clickCell(0, 0);
        assertEquals(MazeCell.UNEXPLORED, maze1.getCell(new Location(0, 0)));
        click(drawWalls);
        this.clickCell(6, 6);
        assertEquals(MazeCell.UNEXPLORED, maze1.getCell(new Location(6, 6)));

    }
    //-------------------------------------------------------------
    /**
     * tests that when in erase mode, walls are removed properly
     */
    public void testEraseWall()
    {
        this.clickCell( 2, 2);
        assertEquals(MazeCell.UNEXPLORED, maze1.getCell(new Location(2, 2)));
        click(drawWalls);
        this.clickCell( 2, 2);
        assertEquals(MazeCell.WALL, maze1.getCell(new Location(2, 2)));
        click(eraseWalls);
        this.clickCell(2, 2);
        assertEquals(MazeCell.UNEXPLORED, maze1.getCell(new Location(2, 2)));

        //tests erase on start and goal
        click(eraseWalls);
        clickCell(0, 0);
        clickCell(6, 6);
        assertEquals(MazeCell.UNEXPLORED, maze1.getCell(new Location(0, 0)));
        assertEquals(MazeCell.UNEXPLORED, maze1.getCell(new Location(6, 6)));

        //tests out of bounds erasing
        click(drawWalls);
        clickCell(2, 0);
        click(eraseWalls);
        touchDownCell(2, 0);
        touchMove(3, 0);
        touchMove(3, -1);
        touchUp();
        assertEquals(MazeCell.UNEXPLORED, maze1.getCell(new Location(2, 0)));
    }
    //----------------------------------------------------------
    /**
     * tests that when start button is clicked, relocates start properly
     */
    public void testStartAndGoal()
    {
        assertEquals(maze1.getStartLocation(), new Location(0, 0));
        assertEquals(maze1.getGoalLocation(), new Location(6, 6));
        click(setStart);
        this.clickCell(1, 1);
        assertEquals(maze1.getStartLocation(), new Location(1, 1));
        click(setGoal);
        this.clickCell(2, 2);
        assertEquals(maze1.getGoalLocation(), new Location(2, 2));

        //tests both start and goal when on wall
        click(drawWalls);
        this.clickCell(1, 1);
        click(setStart);
        this.clickCell(1, 1);
        assertEquals(MazeCell.UNEXPLORED, maze1.getCell(new Location(1, 1)));

        click(drawWalls);
        this.clickCell(1, 1);
        click(setGoal);
        this.clickCell(1, 1);
        assertEquals(MazeCell.UNEXPLORED, maze1.getCell(new Location(1, 1)));
    }
    //------------------------------------------------------------
    /**
     * tests that solve clicked displays the proper solution if there is one
     * and the proper message if there is no solution
     */
    public void testSolve()
    {
        assertEquals(infoLabel.getText(), "TextView");
        click(setGoal);
        this.clickCell(0, 1);
        click(solve);
        assertEquals(infoLabel.getText(), "(0, 0)(0, 1)");

        click(setGoal);
        this.clickCell(6, 6);
        click(drawWalls);
        this.clickCell(5, 6);
        this.clickCell(6, 5);
        this.clickCell(5, 5);
        click(solve);
        assertEquals(infoLabel.getText().toString(),
            "No solution was possible.");
    }
}
