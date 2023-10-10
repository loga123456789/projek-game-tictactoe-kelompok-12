import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel textField = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1Turn;
    private boolean gameOver = false;

    TicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(25, 225, 0));
        textField.setFont(new Font("Ink Free", Font.BOLD, 75));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic-Tac-Toe");
        textField.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(10, 10, 700, 100);

        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        titlePanel.add(textField);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);

        startGame();
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i] && buttons[i].getText().equals("") && !gameOver) {
                if (player1Turn) {
                    buttons[i].setForeground(new Color(255, 0, 0));
                    buttons[i].setText("X");
                } else {
                    buttons[i].setForeground(new Color(0, 0, 255));
                    buttons[i].setText("O");
                }
                player1Turn = !player1Turn;
                textField.setText(player1Turn ? "O Turn" : "X Turn");
                check();
            }
        }
    }

    public void startGame() {
        player1Turn = random.nextBoolean();
        textField.setText(player1Turn ? "X Turn" : "O Turn");
    }

    public void check() {
        for (int[] winningPosition : winningPositions) {
            if (buttons[winningPosition[0]].getText().equals(buttons[winningPosition[1]].getText()) &&
                    buttons[winningPosition[1]].getText().equals(buttons[winningPosition[2]].getText()) &&
                    !buttons[winningPosition[0]].getText().equals("")) {
                gameOver = true;
                highlightWinner(winningPosition);
                updateStatus(buttons[winningPosition[0]].getText() + " Wins!");
            }
        }

        if (!gameOver) {
            boolean allButtonsClicked = true;
            for (JButton button : buttons) {
                if (button.getText().equals("")) {
                    allButtonsClicked = false;
                    break;
                }
            }
            if (allButtonsClicked) {
                gameOver = true;
                updateStatus("It's a draw!");
            }
        }
    }

    public void highlightWinner(int[] positions) {
        for (int position : positions) {
            buttons[position].setBackground(Color.GREEN);
        }
    }

    public void updateStatus(String message) {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
        textField.setText(message);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }

    // Array berisi posisi kombinasi pemenang
    private final int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // baris
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // kolom
            {0, 4, 8}, {2, 4, 6} // diagonal
    };
}