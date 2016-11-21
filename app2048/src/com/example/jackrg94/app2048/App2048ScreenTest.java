package com.example.jackrg94.app2048;



// -------------------------------------------------------------------------
/**
 *  Tests all methods and responses from App2048Screen class.
 *
 *  @author Jack Guttman
 *  @version Aug 10, 2015
 */
public class App2048ScreenTest
    extends student.TestCase
{
    private App2048Screen board;


    public void setUp()
    {
        board = new App2048Screen(); //shape screen doesnt initialized shapeview
        board.presentScreen(App2048Screen.class);
    }

    /**
     * tests the proper responses from App2048Screen class' methods
     */
    public void testAllMethods()
    {
        //Could not figure out why shape view was not being initialized
        //all of these tests fail because they have some interaction with
        //shape view.

        board.initialize();
        assertNotNull(board.getShapeView());
        board.updateGUI();
        assertNotNull(board.getShapeView());
        board.onTouchDown(200, 200);
        board.onTouchMove(199, 200);
        board.onTouchUp(199, 200);
        assertEquals(board.getLastMove(), Dir.WEST);

        board.onTouchDown(200, 200);
        board.onTouchMove(200, 201);
        board.onTouchUp(200, 201);
        assertEquals(board.getLastMove(), Dir.NORTH);

        board.onTouchDown(200, 200);
        board.onTouchMove(200, 199);
        board.onTouchUp(200, 199);
        assertEquals(board.getLastMove(), Dir.SOUTH);

        board.onTouchDown(200, 200);
        board.onTouchMove(201, 200);
        board.onTouchUp(201, 200);
        assertEquals(board.getLastMove(), Dir.EAST);
        board.clear();
        board.resetClicked();
        assertEquals(
            board.getShapeView().getShapes().
            withClass(Tile.class).all().toArray().length, 2);

        board.gameLost();
        assertEquals(board.getGameState().getText(), "You Lose, get better!");

        board.gameWon();
        assertEquals(board.getGameState().getText(), "YOU WIN!");

    }
}
