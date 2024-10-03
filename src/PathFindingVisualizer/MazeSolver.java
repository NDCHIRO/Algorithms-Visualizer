package PathFindingVisualizer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MazeSolver extends JPanel {

    private final int rows = 21;
    private final int cols = 21;
    private final int cellSize = 30;
    private final int[][] maze;
    private final Point start = new Point(0, 0);  // Start at (0,0)
    private final Point end = new Point(rows - 1, cols - 1);  // End at (rows-1, cols-1)
    private final Random random = new Random();
    private Stack<Point> pathStack;
    private boolean pathFound = false;
    private List<Point> solutionPath; // Store the final solution path

    public MazeSolver() {
        maze = new int[rows][cols];
        generateMaze();
        ensureEndPointReachable();  // Ensure that the end point is reachable
        solutionPath = new ArrayList<>();
        new Thread(this::solveMaze).start();
    }

    // Maze generation using DFS
    private void generateMaze() {
        for (int[] row : maze) {
            Arrays.fill(row, 1); // Initialize all cells as walls (1)
        }

        dfsCarvePath(0, 0); // Carve a path from (0,0)

        maze[start.x][start.y] = 0;  // Ensure start point is open
        maze[end.x][end.y] = 0;      // Ensure end point is open
    }

    // DFS to carve paths
    private void dfsCarvePath(int r, int c) {
        maze[r][c] = 0;  // Mark current cell as path
        List<Point> neighbors = getRandomNeighbors(r, c);

        for (Point neighbor : neighbors) {
            int nr = neighbor.x, nc = neighbor.y;
            if (maze[nr][nc] == 1) {
                maze[(r + nr) / 2][(c + nc) / 2] = 0;  // Remove wall between
                dfsCarvePath(nr, nc);
            }
        }
    }

    // Ensure the end point is reachable by carving a direct path to it if needed
    private void ensureEndPointReachable() {
        int r = rows - 2;
        int c = cols - 2;
        // Carve a direct path from (rows-2, cols-2) to the end point
        while (r < rows - 1 || c < cols - 1) {
            maze[r][c] = 0;
            if (r < rows - 1) r++;
            if (c < cols - 1) c++;
        }
    }

    // Get neighbors two cells away (for DFS maze generation)
    private List<Point> getRandomNeighbors(int r, int c) {
        List<Point> neighbors = new ArrayList<>();
        if (r > 1) neighbors.add(new Point(r - 2, c));  // Up
        if (r < rows - 2) neighbors.add(new Point(r + 2, c));  // Down
        if (c > 1) neighbors.add(new Point(r, c - 2));  // Left
        if (c < cols - 2) neighbors.add(new Point(r, c + 2));  // Right
        Collections.shuffle(neighbors);
        return neighbors;
    }

    // Solve the maze using DFS
    private void solveMaze() {
        pathStack = new Stack<>();
        boolean[][] visited = new boolean[rows][cols];
        pathStack.push(start);
        List<Point> currentPath = new ArrayList<>();
        Point lastValid = start; // Track the last valid path for backtracking

        while (!pathStack.isEmpty()) {
            Point current = pathStack.pop();

            // Visualize the trials (red)
            if (!current.equals(end)) {
                maze[current.x][current.y] = 2;  // Mark as trial in red
                repaint();
                sleep(100);  // Slow down visualization
            }

            if (current.equals(end)) {
                pathFound = true;
                solutionPath = new ArrayList<>(currentPath); // Store the correct path
                solutionPath.add(end);  // Add the end point to the path
                markSolutionPath();  // Mark the solution path in blue
                repaint();
                return;
            }

            visited[current.x][current.y] = true;
            currentPath.add(current);  // Add current position to the current path

            List<Point> neighbors = getValidNeighbors(current, visited);
            if (!neighbors.isEmpty()) {
                // Continue exploring if there are valid neighbors
                for (Point neighbor : neighbors) {
                    pathStack.push(neighbor);
                }
                lastValid = current;  // Update last valid point for backtracking
            } else {
                // No valid neighbors, so backtrack
                currentPath.remove(current);
            }
        }

        System.out.println("No solution found.");
    }

    // Mark the solution path (blue)
    private void markSolutionPath() {
        for (Point point : solutionPath) {
            maze[point.x][point.y] = 3;
        }
    }

    // Get valid neighboring cells for DFS solver
    private List<Point> getValidNeighbors(Point current, boolean[][] visited) {
        List<Point> neighbors = new ArrayList<>();
        int r = current.x, c = current.y;

        if (r > 0 && maze[r - 1][c] == 0 && !visited[r - 1][c]) neighbors.add(new Point(r - 1, c));  // Up
        if (r < rows - 1 && maze[r + 1][c] == 0 && !visited[r + 1][c]) neighbors.add(new Point(r + 1, c));  // Down
        if (c > 0 && maze[r][c - 1] == 0 && !visited[r][c - 1]) neighbors.add(new Point(r, c - 1));  // Left
        if (c < cols - 1 && maze[r][c + 1] == 0 && !visited[r][c + 1]) neighbors.add(new Point(r, c + 1));  // Right

        return neighbors;
    }

    // Utility to add a small delay for visualization
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Paint the maze and the solution process
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (maze[r][c] == 1) {
                    g.setColor(Color.BLACK);  // Walls
                } else if (maze[r][c] == 0) {
                    g.setColor(Color.WHITE);  // Paths
                } else if (maze[r][c] == 2) {
                    g.setColor(Color.RED);    // Trials (explored paths)
                } else if (maze[r][c] == 3) {
                    g.setColor(Color.BLUE);   // Correct path
                }
                g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
            }
        }

        // Color the start and end points
        g.setColor(Color.GREEN);
        g.fillRect(start.y * cellSize, start.x * cellSize, cellSize, cellSize);
        g.setColor(Color.YELLOW);
        g.fillRect(end.y * cellSize, end.x * cellSize, cellSize, cellSize);
    }

    // Main method to create the frame and run the app
    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Maze Solver");
        MazeSolver mazeSolver = new MazeSolver();
        frame.add(mazeSolver);
        frame.setSize(mazeSolver.cols * mazeSolver.cellSize + 20, mazeSolver.rows * mazeSolver.cellSize + 40);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
