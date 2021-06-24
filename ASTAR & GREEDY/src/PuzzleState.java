import java.util.ArrayList;

public class PuzzleState {
    private int size;
    private int[][] puzzleState;
    private String path;
    private Integer gOfN;
    private Integer hOfN;

    public PuzzleState(int size, ArrayList<Integer> input) {
        puzzleState = new int[size][size];
        this.size = size;
        this.path = new String();
        this.gOfN = 0;
        this.hOfN = 0;
        int inputIterator = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                puzzleState[i][j] = input.get(inputIterator);
                inputIterator++;
            }
        }
    }

    public PuzzleState(PuzzleState state) {
        this.size = state.size;
        this.puzzleState = new int[this.size][this.size];
        this.path = state.path;
        this.hOfN = state.hOfN;
        this.gOfN = state.gOfN;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.puzzleState[i][j] = state.puzzleState[i][j];
            }
        }
    }

    public int get(int i, int j) throws Exception {
        if (i >= 0 && i < size && j >= 0 && j < size) {
            return puzzleState[i][j];
        }
        throw new Exception("Index out of bound");
    }

    public void set(int i, int j, int key) throws Exception {
        if (i >= 0 && i < size && j >= 0 && j < size) {
            puzzleState[i][j] = key;
        }
        throw new Exception("Index out of bound");
    }

    public void appendPath(String path) {
        if (this.path.length() == 0)
            this.path = path;
        else
            this.path += "->" + path;
    }

    public String getPath() {
        return this.path;
    }

    public int getSize() {
        return size;
    }

    public void printState() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(puzzleState[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Integer getGOfN() {
        return gOfN;
    }
    public Integer getHOfN() {
        return hOfN;
    }

    public void setHOfN(int cost) {
        this.hOfN = cost;
    }

    public Integer getMisplacedTileCount(PuzzleState goalState) throws Exception {
        int misplacedTiles = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (goalState.get(i, j) != 0 && goalState.get(i, j) != puzzleState[i][j]) {
                    misplacedTiles++;
                }
            }
        }
        return misplacedTiles;
    }

    public Point getValIndex(int val) {
        int x = 0;
        int y = 0;
        boolean found = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size && !found; j++) {
                if (val == puzzleState[i][j]) {
                    x = i;
                    y = j;
                    found = true;
                }
            }
        }
        Point point = new Point(x, y);
        return point;
    }

    public Integer getManhattanDistance(PuzzleState goalState) throws Exception {
        int manhattanDistance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (puzzleState[i][j] != 0) {
                    Point p1 = new Point(i, j);
                    Point p2 = goalState.getValIndex(puzzleState[i][j]);
                    manhattanDistance += Point.getManhattanDistance(p1, p2);
                }
            }
        }
        return manhattanDistance;
    }

    public static PuzzleState performAction(PuzzleState state, StateAction action) {
        PuzzleState newState = new PuzzleState(state);
        int freeTileRow = 0;
        int freeTileCol = 0;
        for (int i = 0; i < newState.size; i++) {
            for (int j = 0; j < newState.size; j++) {
                if (newState.puzzleState[i][j] == 0) {
                    freeTileRow = i;
                    freeTileCol = j;
                }
            }
        }
        if (action == StateAction.MOVE_UP) {
            int newFreeTileRow = freeTileRow - 1;
            if (newFreeTileRow >= 0) {
                int swapElement = newState.puzzleState[newFreeTileRow][freeTileCol];
                newState.puzzleState[newFreeTileRow][freeTileCol] = newState.puzzleState[freeTileRow][freeTileCol];
                newState.puzzleState[freeTileRow][freeTileCol] = swapElement;
                    newState.gOfN++;
            }
        } else if (action == StateAction.MOVE_DOWN) {
            int newFreeTileRow = freeTileRow + 1;
            if (newFreeTileRow < newState.size) {
                int swapElement = newState.puzzleState[newFreeTileRow][freeTileCol];
                newState.puzzleState[newFreeTileRow][freeTileCol] = newState.puzzleState[freeTileRow][freeTileCol];
                newState.puzzleState[freeTileRow][freeTileCol] = swapElement;
                    newState.gOfN++;
            }
        } else if (action == StateAction.MOVE_RIGHT) {
            int newFreeTileCol = freeTileCol + 1;
            if (newFreeTileCol < newState.size) {
                int swapElement = newState.puzzleState[freeTileRow][newFreeTileCol];
                newState.puzzleState[freeTileRow][newFreeTileCol] = newState.puzzleState[freeTileRow][freeTileCol];
                newState.puzzleState[freeTileRow][freeTileCol] = swapElement;
                    newState.gOfN++;
            }
        } else if (action == StateAction.MOVE_LEFT) {
            int newFreeTileCol = freeTileCol - 1;
            if (newFreeTileCol >= 0) {
                int swapElement = newState.puzzleState[freeTileRow][newFreeTileCol];
                newState.puzzleState[freeTileRow][newFreeTileCol] = newState.puzzleState[freeTileRow][freeTileCol];
                newState.puzzleState[freeTileRow][freeTileCol] = swapElement;
                    newState.gOfN++;
            }
        }
        return newState;
    }

    public static boolean isEqual(PuzzleState a, PuzzleState b) throws Exception {
        if (a.getSize() == b.getSize()) {
            for (int i = 0; i < a.getSize(); i++) {
                for (int j = 0; j < b.getSize(); j++) {
                    if (a.get(i, j) != b.get(i, j)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
