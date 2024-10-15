package PathFindingVisualizer;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static PathFindingVisualizer.MazeSolverUtils.*;

public class BFSMazeSovler {



    // Initialize BFS for step-by-step solving
    public static void initializeBFS(int[][] maze, Point start) {
        clearMaze();
        queue.add(start);  // Start BFS from the start point
        visited.add(start);  // Mark start as visited
    }

    // Step-by-step BFS with visualization
    public static boolean stepBFS(int[][] maze, Point end) {
        if (queue.isEmpty()) {
            return false;  // BFS is complete
        }

        Point current = queue.poll();  // Dequeue the current point
        path.add(current);  // Add current point to the exploration path

        if (current.equals(end)) {
            reconstructPath(current);  // Reconstruct the correct path once the goal is reached
            return false;  // Maze is solved
        }

        List<Point> neighbors = getNeighbors(maze, current);
        for (Point neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                queue.add(neighbor);
                cameFrom.put(neighbor, current);  // Track the parent node for path reconstruction
                visited.add(neighbor);  // Mark neighbor as visited
            }
        }

        return true;  // Return true to indicate BFS is still in progress
    }



}
