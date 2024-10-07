package PathFindingVisualizer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;  // Correct generic List from java.util
import java.util.ArrayList;  // For using ArrayList
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeUI extends JFrame {
    private int rows = 20;
    private int cols = 20;
    private int cellSize = 25;
    private Maze maze;
    private MazePanel mazePanel;
    private Timer generationTimer;
    private Timer solveTimerDFS;
    private Timer solveTimerBFS;
    private int stepIndex = 0;

    public MazeUI() {
        setTitle("Maze Generator and Solver");
        setSize(cols * cellSize + 50, rows * cellSize + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        maze = new Maze(rows, cols);
        mazePanel = new MazePanel();

        JButton regenerateButton = new JButton("Regenerate Maze (Step-by-Step)");
        regenerateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maze.resetMaze();
                mazePanel.repaint();
                if (solveTimerDFS.isRunning() || solveTimerBFS.isRunning()) {
                    solveTimerDFS.stop();
                    solveTimerBFS.stop();
                }
                if (generationTimer.isRunning()) {
                    generationTimer.stop();
                }
                generationTimer.start();  // Start step-by-step maze generation
            }
        });

        JButton solveDFSButton = new JButton("Solve with DFS (Step-by-Step)");
        solveDFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeSolver.initializeDFS(maze.getMaze(), maze.getStart());
                solveTimerDFS.start();  // Start step-by-step DFS solving
            }
        });

        JButton solveBFSButton = new JButton("Solve with BFS (Step-by-Step)");
        solveBFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeSolver.initializeBFS(maze.getMaze(), maze.getStart());
                solveTimerBFS.start();  // Start step-by-step BFS solving
            }
        });

        // Timer for step-by-step maze generation
        generationTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!maze.isDone()) {
                    maze.step();  // Perform one step of maze generation
                    mazePanel.repaint();  // Update UI after each step
                } else {
                    generationTimer.stop();  // Stop timer once maze is fully generated
                }
            }
        });

        // Timer for step-by-step DFS solving
        solveTimerDFS = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!MazeSolver.stepDFS(maze.getMaze(), maze.getEnd())) {
                    solveTimerDFS.stop();  // Stop DFS timer when maze is solved
                }
                mazePanel.repaint();  // Repaint to show each step
            }
        });

        // Timer for step-by-step BFS solving
        solveTimerBFS = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!MazeSolver.stepBFS(maze.getMaze(), maze.getEnd())) {
                    solveTimerBFS.stop();  // Stop BFS timer when maze is solved
                }
                mazePanel.repaint();  // Repaint to show each step
            }
        });

        setLayout(new BorderLayout());

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));  // Center buttons with horizontal/vertical gaps

        // Set a preferred size for the buttons (Optional, based on how large/small you want them to be)
        Dimension buttonSize = new Dimension(150, 30);
        regenerateButton.setPreferredSize(buttonSize);
        solveDFSButton.setPreferredSize(buttonSize);
        solveBFSButton.setPreferredSize(buttonSize);

        // Add buttons to the panel
        buttonPanel.add(regenerateButton);
        buttonPanel.add(solveDFSButton);  // Add DFS button
        buttonPanel.add(solveBFSButton);  // Add BFS button

        // Add the button panel to the bottom of the UI
        add(mazePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Panel to display the maze and the solution path
    private class MazePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int[][] mazeData = maze.getMaze();
            Point start = maze.getStart();
            Point end = maze.getEnd();

            // Draw the maze (walls and paths)
            for (int i = 0; i < rows; i++){
                for (int j = 0; j < cols; j++) {
                    if (mazeData[i][j] == 1) {
                        g.setColor(Color.WHITE);  // Path
                    } else {
                        g.setColor(Color.BLACK);  // Wall
                    }
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
        }

        // Draw DFS/BFS exploration path
            g.setColor(Color.GRAY);  // Wrong paths (explored but not solution)
            for (Point p : MazeSolver.getPath()) {
            g.fillRect(p.y * cellSize, p.x * cellSize, cellSize, cellSize);
        }

        // Draw the final solution path
            g.setColor(Color.RED);  // Correct path (solution)
            for (Point p : MazeSolver.getSolutionPath()) {
            g.fillRect(p.y * cellSize, p.x * cellSize, cellSize, cellSize);
        }

        // Draw the start and end points
            g.setColor(Color.GREEN);  // Start point
            g.fillRect(start.y * cellSize, start.x * cellSize, cellSize, cellSize);

            g.setColor(Color.YELLOW);  // End point
            g.fillRect(end.y * cellSize, end.x * cellSize, cellSize, cellSize);
    }
}

public static void main(String[] args) {
    new MazeUI();
}
}
