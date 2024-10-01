package PathFindingVisualizer;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class KnightsTourUI extends JFrame {

    private static final int N = 8; // Chessboard size
    private JButton[][] buttons = new JButton[N][N]; // Buttons for each chessboard cell
    private int[][] solution = new int[N][N]; // To keep track of the knight's moves
    private JSlider speedSlider; // Slider to control speed
    private int delay = 300; // Default delay in milliseconds

    // Possible moves for the knight
    private static final int[] knightMoveX = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] knightMoveY = {1, 2, 2, 1, -1, -2, -2, -1};

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
        speedSlider.addChangeListener(new SpeedChangeListener());

        add(speedSlider, BorderLayout.SOUTH);

        // Start the knight's tour in a separate thread to avoid UI freezing
        new Thread(() -> {
            if (solveKnightsTour(0, 0, 1)) {
                System.out.println("Solution found");
            } else {
                System.out.println("No solution found");
            }
        }).start();
    }

    private void initializeBoard(JPanel boardPanel) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                buttons[i][j].setBackground((i + j) % 2 == 0 ? Color.white : Color.gray); // Chessboard colors
                boardPanel.add(buttons[i][j]);
                solution[i][j] = -1; // Initialize solution matrix
            }
        }
    }

    // Solve the Knight's Tour problem using recursion and backtracking
    private boolean solveKnightsTour(int x, int y, int moveCount) {
        solution[x][y] = moveCount; // Mark the current move
        updateUI(x, y, moveCount); // Update the UI with the current move

        if (moveCount == N * N) { // Base case: all cells have been visited
            return true;
        }

        // Try all 8 possible moves for the knight
        for (int i = 0; i < 8; i++) {
            int nextX = x + knightMoveX[i];
            int nextY = y + knightMoveY[i];

            if (isSafe(nextX, nextY)) {
                if (solveKnightsTour(nextX, nextY, moveCount + 1)) {
                    return true;
                }
            }
        }

        // If no valid move was found, backtrack
        markWrongMove(x, y); // Mark the wrong move in red for the same delay as the speed

        solution[x][y] = -1; // Unmark the move (backtrack)
        updateUI(x, y, 0); // Clear the move in the UI
        return false;
    }

    // Check if the next move is safe (inside the board and not visited yet)
    private boolean isSafe(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N && solution[x][y] == -1;
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

    // Listener for the speed slider to adjust the delay
    private class SpeedChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            delay = speedSlider.getValue(); // Get the slider value and set it as the delay
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KnightsTourUI tour = new KnightsTourUI();
            tour.setVisible(true);
        });
    }
}
