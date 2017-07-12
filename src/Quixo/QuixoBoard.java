package Quixo;

public class QuixoBoard
{
    public enum CubeType{Blank, X, O}
    public enum Direction{UP, DOWN, LEFT, RIGHT}

    public CubeType[][] _Cubes;
    public Direction _Direction;
    public CubeType currentPlayer;
    public CubeType nextPlayer;
    public CubeType winningPlayer = null;

    public QuixoBoard()
    {
        _Cubes = new CubeType[5][5];
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                _Cubes[i][j] = CubeType.Blank;
            }
        }
    }

    public void updateCurrentPlayer()
    {
        CubeType temp = currentPlayer;
        currentPlayer = nextPlayer;
        nextPlayer = temp;
    }

    public void moveCube(int x, int y, Direction direction)
    {
        if(_Cubes[x][y] != currentPlayer && _Cubes[x][y] != CubeType.Blank)
        {
            throw new IllegalArgumentException("You can't select your opponents cube! Please try again!");
        }

        if (x == 1 || x == 2 || x == 3)
        {
            if (y == 1 || y == 2 || y == 3)
            {
                throw new IllegalArgumentException("That Coordinate Pair is not valid! Please try again!");
            }
        }

        if(x - 1 < 0 &&  direction == Direction.UP ||
                x + 1 > 4  && direction == Direction.DOWN ||
                y - 1 <  0 && direction == Direction.LEFT ||
                y + 1 > 4 && direction == Direction.RIGHT)
        {
            throw new IllegalArgumentException("The Cube is not allowed to be replaced at the same location! Please try again!");
        }

        switch (direction)
        {
            case RIGHT:
                for (int j = y; j < 4; j++)
                {
                    _Cubes[x][j] = _Cubes[x][j + 1];
                }
                _Cubes[x][4] = currentPlayer;
                break;

            case LEFT:
                for (int j = y; j > 0; j--)
                {
                    _Cubes[x][j] = _Cubes[x][j - 1];
                }
                _Cubes[x][0] = currentPlayer;
                break;

            case UP:
                for (int i = x; i > 0; i--)
                {
                    _Cubes[i][y] = _Cubes[i - 1][y];
                }
                _Cubes[0][y] = currentPlayer;
                break;

            case DOWN:
                for (int i = x; i < 4; i++)
                {
                    _Cubes[i][y] = _Cubes[i + 1][y];
                }
                _Cubes[4][y] = currentPlayer;
                break;
        }

        winningPlayer = getWinner();
    }

    public boolean isGameOver()
    {
        if(winningPlayer == null)
        {
            return false;
        }
        else return true;
    }

    public CubeType getWinner()
    {
        boolean hasFirstPlayerWon = false, hasSecondPlayerWon = false;

        if(checkRowForWinner(CubeType.O) == true || checkColForWinner(CubeType.O) == true || checkDiagsForWinner(CubeType.O) == true)
        {
            hasFirstPlayerWon = true;
        }

        if(checkRowForWinner(CubeType.X) == true || checkColForWinner(CubeType.X) == true || checkDiagsForWinner(CubeType.X) == true)
        {
            hasSecondPlayerWon = true;
        }

        if(hasFirstPlayerWon == true && hasSecondPlayerWon == false)
        {
            winningPlayer = CubeType.O;
        }
        else if (hasFirstPlayerWon == false && hasSecondPlayerWon == true)
        {
            winningPlayer = CubeType.X;
        }
        else if(hasFirstPlayerWon == true && hasSecondPlayerWon == true)
        {
            winningPlayer = nextPlayer;
        }
        updateCurrentPlayer();
        return winningPlayer;
    }

    private boolean checkRowForWinner(CubeType playerCube)
    {
        int cubeTypeCounter = 0;
        boolean hasPlayerWon = false;
        for(int i = 0; i <= 4; i++)
        {
            for(int j = 0; j <= 4; j++)
            {
                if (_Cubes[i][j] == playerCube)
                {
                    cubeTypeCounter++;
                }
            }
            if(cubeTypeCounter == 5){hasPlayerWon = true;}
            cubeTypeCounter = 0;
        }
        return hasPlayerWon;
    }

    private boolean checkColForWinner(CubeType playerCube)
    {
        int cubeTypeCounter = 0;
        boolean hasPlayerWon = false;
        for(int j = 0; j <= 4; j++)
        {
            for (int i = 0; i <= 4; i++)
            {
                if (_Cubes[i][j] == playerCube)
                {
                    cubeTypeCounter++;
                }
            }
            if(cubeTypeCounter == 5)
            {
                hasPlayerWon = true;
            }
            cubeTypeCounter = 0;
        }
        return hasPlayerWon;
    }

    private boolean checkDiagsForWinner(CubeType playerCube)
    {
        int cubeTypeCounter = 0;
        boolean hasPlayerWon = false;

        for(int x = 0; x <= 4; x++)
        {
            if(_Cubes[x][x] == playerCube)
            {
                cubeTypeCounter++;
            }
        }
        if(cubeTypeCounter == 5)
        {
            hasPlayerWon = true;
        }

        cubeTypeCounter = 0;
        int i = 0, j = 4;
        for(int x = 0; x <= 4; x++)
        {
            if(_Cubes[i++][j--] == playerCube)
            {
                cubeTypeCounter++;
            }
        }
        if(cubeTypeCounter == 5)
        {
            hasPlayerWon = true;
        }
        return hasPlayerWon;
    }
}
