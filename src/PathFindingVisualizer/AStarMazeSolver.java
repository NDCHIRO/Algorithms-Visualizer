package PathFindingVisualizer;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


import static PathFindingVisualizer.MazeSolverUtils.*;

public class AStarMazeSolver {


    public static void initializeAStar(int[][] maze, Point start) {
        clearMaze();
        openSet.add(new Node(start, 0));  // Add start node with fScore = 0
        visited.add(start);  // Mark start as visited
    }

    // Step-by-step A* with visualization
    public static boolean stepAStar(int[][] maze, Point end) {
        if (openSet.isEmpty()) {
            return false;  // A* is complete, no path found
        }

        Node current = openSet.poll();  // Dequeue the node with the lowest fScore
        path.add(current.point);  // Add current point to the exploration path

        if (current.point.equals(end)) {
            reconstructPath(current.point);  // Reconstruct the correct path once the goal is reached
            return false;  // Maze is solved
        }

        List<Point> neighbors = getNeighbors(maze, current.point);
        for (Point neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                double gScore = calculateGScore(current.point, neighbor);  // Distance from start
                double fScore = gScore + heuristic(neighbor, end);  // fScore = gScore + hScore
                openSet.add(new Node(neighbor, fScore));
                visited.add(neighbor);  // Mark neighbor as visited
                cameFrom.put(neighbor, current.point);  // Track the parent node for path reconstruction
            }
        }

        return true;  // Return true to indicate A* is still in progress
    }

    // Heuristic function for A* (Manhattan distance)
    private static double heuristic(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    // Calculate gScore (simple distance)
    private static double calculateGScore(Point from, Point to) {
        return 1;  // Assume uniform cost for moving between points
    }

}
