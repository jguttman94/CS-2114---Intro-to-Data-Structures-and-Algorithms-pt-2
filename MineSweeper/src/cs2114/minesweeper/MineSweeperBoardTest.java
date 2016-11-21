package cs2114.minesweeper;

import junit.framework.TestCase;
import sofia.util.Random;

// -------------------------------------------------------------------------
/**
 *  tests all codes from MineSweeperBoard's extension of
 *  MineSweeperBoardBase, including all abstract and concrete methods
 *  defined by the abstract class.
 *
 *  @author Jack G
 *  @version Jul 12, 2015
 */

public class MineSweeperBoardTest
    extends TestCase
{
    /**
     * minesweeperboard object for testing
     */
    private MineSweeperBoard board;
    /**
     * minesweeperboard object for testing
     */
    private MineSweeperBoard board2;
    // ----------------------------------------------------------
    /**
     * Initializes the setup before each testing case
     */
    public void setUp()
    {
        board = new MineSweeperBoard(4, 4, 2);
        board2 = new MineSweeperBoard(4, 4, 2);
    }

    //-----------------------------------------------------------
    /**
     * tests the constructor for the mine sweeper board
     */
    public void testConstructor()
    {
        Random.setNextInts(4, 6, 4, 6);
        MineSweeperBoard b1 = new MineSweeperBoard(20, 20, 1);

        assertEquals(b1.getCell(0, 0), MineSweeperCell.COVERED_CELL);
        assertEquals(b1.getCell(4, 6), MineSweeperCell.MINE);
    }

    /**
     * formats assertBoard to compare loadBoard scenarios
     * @param theBoard  board to compare to loaded board state
     * @param expected  strings to compare to theBoard
     */
    public void assertBoard(MineSweeperBoard theBoard, String... expected)
    {
        MineSweeperBoard expectedBoard =
            new MineSweeperBoard(expected[0].length(), expected.length, 0);
        expectedBoard.loadBoardState(expected);
        assertEquals(expectedBoard, theBoard);
        // uses equals() from MineSweeperBoardBase
    }
    //-----------------------------------------------------------------------

    /**
     * tests get Height with loadBoardState
     */
    public void testHeight()
    {
        MineSweeperBoard boardY = new MineSweeperBoard(5, 4, 2);
        boardY.loadBoardState("     ",
            "OOOOO",
            "O++OO",
            "OOOOO");
        assertEquals(boardY.height(), 4);
    }
    /**
     * tests get Width with loadBoardState
     */
    public void testWidth()
    {
        MineSweeperBoard boardX = new MineSweeperBoard(4, 5, 2);
        boardX.loadBoardState("    ",
            "OOOO",
            "O++O",
            "OOOO",
            "OOOO");
        assertEquals(boardX.width(), 4);

    }
    /**
     * tests getCell method to make sure the return values are accurate
     */
    public void testGetCell()
    {
        MineSweeperBoard boardX = new MineSweeperBoard(20, 20, 0);
        //tests if width value is outside of 0 and height is normal
        assertEquals(boardX.getCell(-1, 5), MineSweeperCell.INVALID_CELL);
        //tests if width value is outside of 0 and height is invalid
        assertEquals(boardX.getCell(-1, 19), MineSweeperCell.INVALID_CELL);
        //tests if width value is normal and height is outside of 0
        assertEquals(boardX.getCell(10, -1), MineSweeperCell.INVALID_CELL);
        //tests if width value is invalid and height is outside of 0
        assertEquals(boardX.getCell(19, -1), MineSweeperCell.INVALID_CELL);
        //tests if width value is normal and height value is normal
        assertEquals(boardX.getCell(15, 15), MineSweeperCell.COVERED_CELL);
    }

    /**
     * tests setCell method to make sure the entered values go in correctly
     * or not at all (depending on their validity)
     */
    public void testSetCell()
    {
        //no restrictions, tests normal setCell()
        board.loadBoardState("    ",
            "O++O",
            "OOOO",
            "OOOO");
        board.setCell(0, 1, MineSweeperCell.FLAG);
        assertBoard(board,  "    ",
                            "F++O",
                            "OOOO",
                            "OOOO");
        board.setCell(1, 1, MineSweeperCell.FLAGGED_MINE);
        assertBoard(board, "    ",
                           "FM+O",
                           "OOOO",
                           "OOOO");
        //tests if invalid
        board.loadBoardState("    ",
            "OOOO",
            "OO++",
            "OOOO");
        board.setCell(5, 1, MineSweeperCell.INVALID_CELL);

    }

    /**
     * tests flagCell and all of its possible outcomes
     */
    public void testFlagCell()
    {
        //loading of original board state
        board.loadBoardState("    ",
            "OOOO",
            "O++O",
            "OOOO");
        //tests flagCell on covered cell
        board.flagCell(1, 1);
        assertBoard(board,  "    ",
                            "OFOO",
                            "O++O",
                            "OOOO");

        //tests flagCell on unflagged mine
        board.flagCell(1, 2);
        assertBoard(board, "    ",
                           "OFOO",
                           "OM+O",
                           "OOOO");
        //tests flagCell on flagged cell
        board.flagCell(1, 1);
        assertBoard(board, "    ",
                           "OOOO",
                           "OM+O",
                           "OOOO");
        //tests flagCell on flagged mine
        board.flagCell(1, 2);
        assertBoard(board, "    ",
                           "OOOO",
                           "O++O",
                           "OOOO");
        //tests flagCell on invalid space and results in no change
        board.flagCell(4, 4);
        assertEquals(MineSweeperCell.INVALID_CELL, board.getCell(4, 4));
        //tests flagCell on already uncovered piece and results in no change
        board.flagCell(2, 0);
        assertBoard(board, "    ",
                           "OOOO",
                           "O++O",
                           "OOOO");
    }
    /**
     * tests the method to count all adjacent mines in vacinity
     */
    public void testNumberOfAdjacentMines()
    {
        //tests that the method detects all 3 forms of mines
        board.loadBoardState("    ",
                             "MOOO",
                             "+OOO",
                             "*OOO");
        assertEquals(board.numberOfAdjacentMines(1, 2), 3);
        //tests detection of all 8 possible mines
        board.loadBoardState("    ",
                             "+++O",
                             "+O+O",
                             "+++O");
        assertEquals(board.numberOfAdjacentMines(1, 2), 8);
    }
    /**
     * tests to see if uncoverCell() appropriately flips cells
     */
    public void testUncoverCell()
    {
        board.loadBoardState("    ",
                             "OOOO",
                             "+FFO",
                             "OOOM");
        //tests uncover on covered cell
        board.uncoverCell(0, 1);
        assertBoard(board, "    ",
                           "1OOO",
                           "+FFO",
                           "OOOM");
        //tests uncover on flagged cell (no response)
        board.uncoverCell(2, 2);
        assertBoard(board, "    ",
                           "1OOO",
                           "+FFO",
                           "OOOM");
        //tests uncover on covered mine
        board.uncoverCell(0, 2);
        assertBoard(board, "    ",
                           "1OOO",
                           "*FFO",
                           "OOOM");
        assertEquals(board.isGameLost(), true);
        //tests uncover on flagged mine ( no response)
        board.uncoverCell(3, 3);
        assertBoard(board, "    ",
                           "1OOO",
                           "*FFO",
                           "OOOM");
        //tests uncover on invalid cell (no response)
        board.uncoverCell(4, 4);
        assertBoard(board, "    ",
                           "1OOO",
                           "*FFO",
                           "OOOM");

    }
    /**
     * tests the concrete method supplied by MineSweeperBoardBase - equals()
     */
    public void testEquals()
    {
        board.loadBoardState("    ",
                             "OOOO",
                             "OOOO",
                             "++OO");
        board2.loadBoardState("    ",
                              "OOOO",
                              "OOOO",
                              "++OO");
        assertEquals(board.equals(board2), true);
        board2.loadBoardState("    ",
                              "OOOO",
                              "MMOO",
                              "OOOO");
        assertEquals(board.equals(board2), false);
    }

    /**
     * tests scenarios that should or shouldnt be a Win and looks for correct
     * response from method isGameWon()
     */
    public void testIsGameWon()
    {
        //tests for win ( should be win )
        board.loadBoardState("    ",
                             "MM  ",
                             "    ",
                             "    ");
        assertEquals(board.isGameWon(), true);
        //tests for win ( shouldn't be win )
        board.loadBoardState("F   ",
                             "    ",
                             "MM  ",
                             "    ");
        assertEquals(board.isGameWon(), false);
        //tests for loss
        board.loadBoardState("+   ",
                             "    ",
                             "    ",
                             "M   ");
        assertEquals(board.isGameWon(), false);
        //tests for loss again
        board.loadBoardState("*OOO",
                             "OOOO",
                             "OOOO",
                             "OOOO");
        assertEquals(board.isGameWon(), false);
        board.loadBoardState("OOOO",
                             "OOOO",
                             "OOOO",
                             "OOOO");
        assertEquals(board.isGameWon(), false);
    }

    /**
     * tests scenarios for a lost game using method isGameLost()
     */
    public void testIsGameLost()
    {
        //tests if not lost
        board.loadBoardState("    ",
                             "OOOO",
                             "MMOO",
                             "OOOO");
        assertEquals(board.isGameLost(), false);
        //tests if lost
        board.loadBoardState("OOOO",
                             "+OOO",
                             "OOOO",
                             "OOOO");
        board.uncoverCell(0, 1);
        assertEquals(board.isGameLost(), true);
    }

    /**
     * tests scenarios to fully uncover the board ( reveal it )
     */
    public void testRevealBoard()
    {
        board.loadBoardState("OOOO",
                             "OFOO",
                             "++++",
                             "OOOO");
        board.revealBoard();
        assertBoard(board, "    ",
                           "2332",
                           "****",
                           "2332");
        board.loadBoardState("OOOO",
                             "MMOO",
                             "++++",
                             "OOOO");
        board.revealBoard();
        assertBoard(board, "221 ",
                           "**42",
                           "****",
                           "2332");

    }
}
