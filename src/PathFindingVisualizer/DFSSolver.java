package PathFindingVisualizer;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static PathFindingVisualizer.MazeSolverUtils.*;

public class DFSSolver {


    // Initialize the DFS and start solving
    public static void initializeDFS(int[][] maze, Point start) {
        clearMaze();
        stack.push(start);  // Start DFS from the start point
    }

    public static boolean stepDFS(int[][] maze, Point end) {
        if (stack.isEmpty()) {
            return false;  // DFS is complete
        }

        Point current = stack.peek();
        path.add(current);  // Add the current point to the visualization path

        if (current.equals(end)) {
            reconstructPath(current);  // Reconstruct the correct path once the goal is reached
            return false;  // Maze is solved
        }

        visited.add(current);
        List<Point> neighbors = getNeighbors(maze, current);

        // Shuffle the neighbors to add randomness in direction selection
        Collections.shuffle(neighbors);

        boolean moved = false;
        for (Point neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                stack.push(neighbor);
                moved = true;
                cameFrom.put(neighbor, current);  // Track the parent node for path reconstruction
                break;  // Move to the first unvisited neighbor (randomized)
            }
        }

        if (!moved) {
            stack.pop();  // Backtrack if no unvisited neighbors
        }

        return true;  // Return true to indicate DFS is still in progress
    }

}
