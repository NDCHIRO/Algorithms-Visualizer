package knightTour;

import javax.swing.*;
import java.awt.*;

public class KnightsTourUI extends JFrame {

    private static final int N = 8; // Chessboard size
    private JButton[][] buttons = new JButton[N][N]; // Buttons for each chessboard cell
    private JSlider speedSlider; // Slider to control speed
    private int delay = 300; // Default delay in milliseconds

    public KnightsTourUI() {
        setTitle("Knight's Tour with Speed Control");
        setSize(600, 650); // Increase height for the slider
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Chessboard panel in the center
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(N, N));
        initializeBoard(boardPanel);
        add(boardPanel, BorderLayout.CENTER);

        // Speed control slider at the bottom
        speedSlider = new JSlider(JSlider.HORIZONTAL, 50, 1000, delay);
        speedSlider.setMajorTickSpacing(200);
        speedSlider.setMinorTickSpacing(50);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setBorder(BorderFactory.createTitledBorder("Speed Control (ms per move)"));
        speedSlider.addChangeListener(e -> delay = speedSlider.getValue()); // Adjust delay

        add(speedSlider, BorderLayout.SOUTH);

        // Create the KnightsTourSolver and start solving in a separate thread
        KnightsTourSolver solver = new KnightsTourSolver(N, new KnightsTourSolver.MoveListener() {
            @Override
            public void onMoveMade(int x, int y, int moveCount) {
                updateUI(x, y, moveCount); // Update UI with each move
            }

            @Override
            public void onWrongMove(int x, int y) {
                markWrongMove(x, y); // Visualize wrong moves
            }

            @Override
            public void onSolutionFound() {
                JOptionPane.showMessageDialog(null, "Solution found!");
            }

            @Override
            public void onNoSolution() {
                JOptionPane.showMessageDialog(null, "No solution found!");
            }
        });

        // Start the solver in a separate thread to avoid UI freezing
        new Thread(solver::solve).start();
    }

    private void initializeBoard(JPanel boardPanel) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                buttons[i][j].setBackground((i + j) % 2 == 0 ? Color.white : Color.gray); // Chessboard colors
                boardPanel.add(buttons[i][j]);
            }
        }
    }

    // Update the UI with the knight's current position
    private void updateUI(int x, int y, int moveCount) {
        SwingUtilities.invokeLater(() -> {
            buttons[x][y].setText(moveCount > 0 ? String.valueOf(moveCount) : ""); // Set move number
            buttons[x][y].setBackground(moveCount > 0 ? Color.green : (x + y) % 2 == 0 ? Color.white : Color.gray); // Highlight knight's position
        });

        // Add a delay to visualize the knight's movement
        try {
            Thread.sleep(delay); // Use delay set by the slider
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Mark a wrong move as red, wait for the same delay as other moves, and then revert back
    private void markWrongMove(int x, int y) {
        SwingUtilities.invokeLater(() -> buttons[x][y].setBackground(Color.red));

        // Wait for the same delay as the speed set by the slider
        try {
            Thread.sleep(delay); // Use the same delay for marking the wrong move
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Revert back to original color
        SwingUtilities.invokeLater(() -> buttons[x][y].setBackground((x + y) % 2 == 0 ? Color.white : Color.gray));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KnightsTourUI tour = new KnightsTourUI();
            tour.setVisible(true);
        });
    }
}
