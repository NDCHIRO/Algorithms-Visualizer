package PathFindingVisualizer;

import java.awt.*;

// Node class for A* with fScore
class Node {
    Point point;
    double fScore;  // fScore = gScore + heuristic

    public Node(Point point, double fScore) {
        this.point = point;
        this.fScore = fScore;
    }
}
