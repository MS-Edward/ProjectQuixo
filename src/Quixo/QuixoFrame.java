package Quixo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuixoFrame extends JFrame {

    private QuixoBoard board;
    public final int rows = 5;
    public final int cols = 5;
    public int x1,y1;
    public boolean gridSelection = false;
    public QuixoBoard.Direction chosenDirection;
    private JToggleButton[][] gridButtons;
    private JRadioButton[] directionButtons;
    private JButton endTurnButton;
    JLabel playerIndicator, s1, s2, s3;

    protected void frameInit()
    {
        JPanel gridPanel = new JPanel();
        JPanel auxPanel = new JPanel();
        ButtonGroup gridButtonGroup = new ButtonGroup();
        ButtonGroup directionButtonGroup = new ButtonGroup();

        playerIndicator = new JLabel("Player O Make Your Move!");
        playerIndicator.setFont(new Font("Serif", Font.BOLD, 16));
        s1 = new JLabel("Step 1: Select a cube that is blank or yours to move!         ");
        s2 = new JLabel("Step 2: Choose a direction to move your cube!            ");
        s3 = new JLabel("First to line up 5 cubes wins the game!");

        gridPanel.setLayout(new GridLayout(5, 5));
        auxPanel.add(playerIndicator);
        auxPanel.add(s1);
        auxPanel.add(s2);


        board = new QuixoBoard();
        board.currentPlayer = QuixoBoard.CubeType.O;
        board.nextPlayer = QuixoBoard.CubeType.X;

        gridButtons = new JToggleButton[rows][cols];
        directionButtons = new JRadioButton[4];

        super.frameInit();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,gridPanel,auxPanel);

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                gridButtons[i][j] = new JToggleButton();
                gridButtons[i][j].setName(i + "_" + j);
                gridButtonGroup.add(gridButtons[i][j]);
                gridPanel.add(gridButtons[i][j]);
                gridButtons[i][j].setText(board._Cubes[i][j].name());
                gridButtons[i][j].addActionListener(new gameBoardListener());
            }
        }

        directionButtons[0] = new JRadioButton();
        directionButtons[0].setText(QuixoBoard.Direction.UP.name());
        directionButtonGroup.add(directionButtons[0]);
        auxPanel.add(directionButtons[0]);
        directionButtons[0].addActionListener(new directionListener());

        directionButtons[1] = new JRadioButton();
        directionButtons[1].setText(QuixoBoard.Direction.DOWN.name());
        directionButtonGroup.add(directionButtons[1]);
        auxPanel.add(directionButtons[1]);
        directionButtons[1].addActionListener(new directionListener());

        directionButtons[2] = new JRadioButton();
        directionButtons[2].setText(QuixoBoard.Direction.LEFT.name());
        directionButtonGroup.add(directionButtons[2]);
        auxPanel.add(directionButtons[2]);
        directionButtons[2].addActionListener(new directionListener());

        directionButtons[3] = new JRadioButton();
        directionButtons[3].setText(QuixoBoard.Direction.RIGHT.name());
        directionButtonGroup.add(directionButtons[3]);
        auxPanel.add(directionButtons[3]);
        directionButtons[3].addActionListener(new directionListener());



        endTurnButton = new JButton();
        endTurnButton.setText("End Your Turn");
        endTurnButton.setFont(new Font("Serif", Font.BOLD, 16));
        auxPanel.add(endTurnButton);
        auxPanel.add(s3);
        endTurnButton.addActionListener(new endTurnListener());

        splitPane.setOneTouchExpandable(true);
        getContentPane().add(splitPane);
    }

    private class gameBoardListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (!board.isGameOver())
            {
                gridSelection = true;
                JToggleButton theButton = (JToggleButton) e.getSource();
                String name = theButton.getName();
                x1 = Integer.parseInt("" + name.charAt(0));
                y1 = Integer.parseInt("" + name.charAt(2));
            }
        }
    }

    private class directionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (!board.isGameOver())
            {
                JRadioButton theButton = (JRadioButton) e.getSource();
                String name = theButton.getText();

                if(name.equals("UP"))
                {
                    chosenDirection = QuixoBoard.Direction.UP;
                }
                else if(name.equals("DOWN"))
                {
                    chosenDirection = QuixoBoard.Direction.DOWN;
                }
                else if(name.equals("LEFT"))
                {
                    chosenDirection = QuixoBoard.Direction.LEFT;
                }

                else if(name.equals("RIGHT"))
                {
                    chosenDirection = QuixoBoard.Direction.RIGHT;
                }
            }
        }
    }

    private class endTurnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (!board.isGameOver())
            {
                try
                {
                    if(chosenDirection == null || gridSelection == false)
                    {
                        throw new IllegalArgumentException("Seems like you hit the End Turn Button without following all the steps. Please try again!");
                    }
                    board.moveCube(x1, y1, chosenDirection);

                    for(int i = 0; i < 5; i++)
                    {
                        for(int j = 0; j < 5; j++)
                        {
                            gridButtons[i][j].setText(board._Cubes[i][j].name());
                        }
                    }
                    if(board.isGameOver() == true)
                    {
                        JOptionPane.showMessageDialog(null, "Game has been won by " + board.winningPlayer.name() + " Player!");
                    }

                } catch (IllegalArgumentException ex)
                {
                    JOptionPane.showMessageDialog(null, "Illegal Action: " + ex);
                }
            }
            if(board.currentPlayer == QuixoBoard.CubeType.O)
            {
                playerIndicator.setText("Player O Make Your Move!");
            }
            else playerIndicator.setText("Player X Make Your Move!");
        }
    }

    public static void main(String[] args) {
        QuixoFrame frame = new QuixoFrame();
        frame.setSize(700, 500);
        frame.setTitle("Quixo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}