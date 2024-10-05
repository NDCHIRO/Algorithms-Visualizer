package knightTour;

public class KnightsTourSolver {

    private static final int[] knightMoveX = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] knightMoveY = {1, 2, 2, 1, -1, -2, -2, -1};
    private int N; // Board size
    private int[][] solution; // To store the solution moves

    // Callback interface to notify the UI
    public interface MoveListener {
        void onMoveMade(int x, int y, int moveCount); // To notify UI when a move is made
        void onWrongMove(int x, int y); // To notify UI of wrong moves (for visualization)
        void onSolutionFound(); // Notify if the solution is found
        void onNoSolution(); // Notify if no solution is found
    }

    private MoveListener listener;

    public KnightsTourSolver(int N, MoveListener listener) {
        this.N = N;
        this.solution = new int[N][N]; // Initialize solution board
        this.listener = listener;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                solution[i][j] = -1; // Mark all cells as unvisited
            }
        }
    }

    // Start solving the Knight's Tour problem
    public void solve() {
        if (solveKnightsTour(0, 0, 1)) {
            listener.onSolutionFound();
        } else {
            listener.onNoSolution();
        }
    }

    // Recursive function to solve the Knight's Tour problem
    private boolean solveKnightsTour(int x, int y, int moveCount) {
        solution[x][y] = moveCount; // Mark the current move
        listener.onMoveMade(x, y, moveCount); // Notify the listener (UI)

        if (moveCount == N * N) { // All cells visited
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
        listener.onWrongMove(x, y); // Notify the listener of the wrong move
        solution[x][y] = -1; // Unmark the move
        listener.onMoveMade(x, y, 0); // Clear the move in the UI
        return false;
    }

    // Check if the next move is safe (inside the board and not visited yet)
    private boolean isSafe(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N && solution[x][y] == -1;
    }
}
