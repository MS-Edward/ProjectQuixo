package quixo;

import Quixo.QuixoBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuixoBoardTest
{
    QuixoBoard board;

    @Before
    public void setUp()
    {
        board = new QuixoBoard();
    }

    @Test
    public void testCanary()
    {
        assertTrue(true);
    }

    @Test
    public void testMoveTopLeftCubeRight()
    {
        board.moveCube(0,0,board._Direction.RIGHT);
        assertEquals(board._Cubes[0][0], QuixoBoard.CubeType.Blank);
        assertNotEquals(board._Cubes[0][4], QuixoBoard.CubeType.Blank);
    }

    @Test
    public void testMoveTopRightCubeLeft()
    {
        board.moveCube(0,4,board._Direction.LEFT);
        assertEquals(board._Cubes[0][4], QuixoBoard.CubeType.Blank);
        assertNotEquals(board._Cubes[0][0], QuixoBoard.CubeType.Blank);
    }

    @Test
    public void testMoveBottomLeftCubeRight()
    {
        board.moveCube(4,0,board._Direction.RIGHT);
        assertEquals(board._Cubes[4][0], QuixoBoard.CubeType.Blank);
        assertNotEquals(board._Cubes[4][4], QuixoBoard.CubeType.Blank);
    }

    @Test
    public void testMoveBottomRightCubeLeft()
    {
        board.moveCube(4,4,board._Direction.LEFT);
        assertEquals(board._Cubes[4][4], QuixoBoard.CubeType.Blank);
        assertNotEquals(board._Cubes[4][0], QuixoBoard.CubeType.Blank);
    }

    @Test
    public void testMoveTopEdgeCubeRight()
    {
        board.moveCube(0,3,board._Direction.RIGHT);
        assertEquals(board._Cubes[0][3], QuixoBoard.CubeType.Blank);
        assertNotEquals(board._Cubes[0][4], QuixoBoard.CubeType.Blank);
    }

    @Test
    public void testMoveTopEdgeCubeLeft()
    {
        board.moveCube(0,3,board._Direction.LEFT);
        assertEquals(board._Cubes[0][3], QuixoBoard.CubeType.Blank);
        assertNotEquals(board._Cubes[0][0], QuixoBoard.CubeType.Blank);
    }

    @Test
    public void testMoveCenterLeftCubeUp()
    {
        board.moveCube(2,0,board._Direction.UP);
        assertEquals(board._Cubes[2][0], QuixoBoard.CubeType.Blank);
        assertNotEquals(board._Cubes[0][0], QuixoBoard.CubeType.Blank);
    }

    @Test
    public void testMoveCenterLeftCubeDown()
    {
        board.moveCube(2,0,board._Direction.DOWN);
        assertEquals(board._Cubes[2][0], QuixoBoard.CubeType.Blank);
        assertNotEquals(board._Cubes[4][0], QuixoBoard.CubeType.Blank);
    }

    @Test
    public void testMoveMiddleCube()
    {
        try
        {
            board.moveCube(2, 2, board._Direction.RIGHT);
            assertEquals(board._Cubes[2][2], QuixoBoard.CubeType.Blank);
            assertEquals(board._Cubes[2][4], QuixoBoard.CubeType.Blank);
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testExceptionIllegalCubeMove()
    {
        try
        {
            board.moveCube(4, 4, board._Direction.RIGHT);
        }catch(IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testWinGameByRowMatch()
    {
        board._Cubes[4][0] = QuixoBoard.CubeType.O;
        board._Cubes[4][1] = QuixoBoard.CubeType.O;
        board._Cubes[4][2] = QuixoBoard.CubeType.O;
        board._Cubes[4][3] = QuixoBoard.CubeType.O;
        board._Cubes[4][4] = QuixoBoard.CubeType.O;
        assertEquals(QuixoBoard.CubeType.O,board.getWinner());
    }

    @Test
    public void testWinGameByColMatch()
    {
        board._Cubes[0][4] = QuixoBoard.CubeType.X;
        board._Cubes[1][4] = QuixoBoard.CubeType.X;
        board._Cubes[2][4] = QuixoBoard.CubeType.X;
        board._Cubes[3][4] = QuixoBoard.CubeType.X;
        board._Cubes[4][4] = QuixoBoard.CubeType.X;
        assertEquals(QuixoBoard.CubeType.X,board.getWinner());
    }

    @Test
    public void testWinGameByDiagMatch()
    {
        board._Cubes[0][0] = QuixoBoard.CubeType.X;
        board._Cubes[1][1] = QuixoBoard.CubeType.X;
        board._Cubes[2][2] = QuixoBoard.CubeType.X;
        board._Cubes[3][3] = QuixoBoard.CubeType.X;
        board._Cubes[4][4] = QuixoBoard.CubeType.X;
        assertEquals(QuixoBoard.CubeType.X,board.getWinner());
    }

    @Test
    public void testPlayerLastMoveWinner()
    {
        board._Cubes[0][0] = QuixoBoard.CubeType.O;
        board._Cubes[0][1] = QuixoBoard.CubeType.O;
        board._Cubes[0][2] = QuixoBoard.CubeType.O;
        board._Cubes[0][3] = QuixoBoard.CubeType.O;
        board._Cubes[0][4] = QuixoBoard.CubeType.X;

        board._Cubes[1][0] = QuixoBoard.CubeType.X;
        board._Cubes[1][1] = QuixoBoard.CubeType.X;
        board._Cubes[1][2] = QuixoBoard.CubeType.X;
        board._Cubes[1][3] = QuixoBoard.CubeType.X;

        board.moveCube(4,4,board._Direction.UP);

        assertEquals(board.getWinner(), QuixoBoard.CubeType.X);
    }
}