package PathFindingVisualizer;
import java.awt.*;
import java.util.Random;
import java.util.Stack;

public class Maze {
    private int rows;
    private int cols;
    private int[][] maze;
    private Point start;
    private Point end;
    private Stack<Point> stack;
    private boolean isDone;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        resetMaze();
    }

    public void resetMaze() {
        maze = new int[rows][cols];
        stack = new Stack<>();
        start = new Point(1, 1);  // Fixed start at the top-left corner
        end = new Point(rows - 3, cols - 3);  // Fixed end point at (rows-2, cols-2)

        // Initialize the maze with walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || j == 0 || i == rows - 1 || j == cols - 1) {
                    maze[i][j] = 0;  // Border walls
                } else {
                    maze[i][j] = 0;  // Initially walls, will change to paths
                }
            }
        }

        stack.push(start);
        maze[start.x][start.y] = 1;  // Mark start as path
        isDone = false;
    }

    // Step-by-step generation of the maze using DFS
    public boolean step() {
        if (stack.isEmpty()) {
            isDone = true;
            return false;  // Maze generation is complete
        }

        Point current = stack.peek();  // Use peek() to check the current point without popping
        int x = current.x;
        int y = current.y;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};  // Down, Up, Right, Left
        shuffleArray(directions);  // Shuffle directions for randomness

        boolean foundNewPath = false;
        for (int[] dir : directions) {
            int newX = x + dir[0] * 2;
            int newY = y + dir[1] * 2;

            if (newX > 0 && newX < rows - 1 && newY > 0 && newY < cols - 1 && maze[newX][newY] == 0) {
                maze[x + dir[0]][y + dir[1]] = 1;  // Mark path between current and new point
                maze[newX][newY] = 1;  // Mark new point as path
                stack.push(new Point(newX, newY));  // Add new point to the stack
                foundNewPath = true;
                break;  // Break after finding one path to slow down generation
            }
        }

        if (!foundNewPath) {
            stack.pop();  // Backtrack if no new path is found
        }

        return true;  // Return true to indicate the generation is still in progress
    }

    // Helper function to shuffle directions
    private void shuffleArray(int[][] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int[] temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public int[][] getMaze() {
        return maze;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public boolean isDone() {
        return isDone;
    }
}
