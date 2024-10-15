package PathFindingVisualizer;
import java.awt.Point;
import java.util.*;

public class MazeSolverUtils {

    public static List<Point> path = new ArrayList<>();  // Stores the full path explored
    public static List<Point> solutionPath = new ArrayList<>();  // Stores the final solution path
    public static Set<Point> visited = new HashSet<>();  // Tracks visited nodes
    public static Map<Point, Point> cameFrom = new HashMap<>();  // Parent tracking for backtracking the solution path
    public static Stack<Point> stack = new Stack<>();  // Stack for DFS
    public static Queue<Point> queue = new LinkedList<>();  // Queue for BFS
    public static PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));  // Priority queue for A*

    public static void clearMaze() {
        path.clear();
        solutionPath.clear();
        visited.clear();
        cameFrom.clear();
        stack.clear();
        queue.clear();
        openSet.clear();
    }

    // Reconstruct the path from the goal to the start
    public static void reconstructPath(Point current) {
        solutionPath.clear();
        while (current != null) {
            solutionPath.add(0, current);  // Add nodes to the solution path in reverse order
            current = cameFrom.get(current);  // Go to the parent node
        }
    }


    // Utility function to get valid neighbors
    public static List<Point> getNeighbors(int[][] maze, Point p) {
        List<Point> neighbors = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};  // Down, Up, Right, Left

        for (int[] dir : directions) {
            int newX = p.x + dir[0];
            int newY = p.y + dir[1];

            if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length && maze[newX][newY] == 1) {
                neighbors.add(new Point(newX, newY));
            }
        }

        return neighbors;
    }

    public static List<Point> getPath() {
        return path;
    }

    public static List<Point> getSolutionPath() {
        return solutionPath;
    }



}
