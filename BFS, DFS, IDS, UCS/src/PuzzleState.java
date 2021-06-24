import java.util.ArrayList;

public class PuzzleState {
    private int size;
    private int[][] puzzleState;
    private String path;
    private Integer cost;

    public PuzzleState(int size, ArrayList<Integer> input) {
        puzzleState = new int[size][size];
        this.size = size;
        this.path = new String();
        this.cost = 0;
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
        this.cost = state.cost;
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

    public Integer getCost() {
        return cost;
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
                newState.cost++;
            }
        } else if (action == StateAction.MOVE_DOWN) {
            int newFreeTileRow = freeTileRow + 1;
            if (newFreeTileRow < newState.size) {
                int swapElement = newState.puzzleState[newFreeTileRow][freeTileCol];
                newState.puzzleState[newFreeTileRow][freeTileCol] = newState.puzzleState[freeTileRow][freeTileCol];
                newState.puzzleState[freeTileRow][freeTileCol] = swapElement;
                newState.cost++;
            }
        } else if (action == StateAction.MOVE_RIGHT) {
            int newFreeTileCol = freeTileCol + 1;
            if (newFreeTileCol < newState.size) {
                int swapElement = newState.puzzleState[freeTileRow][newFreeTileCol];
                newState.puzzleState[freeTileRow][newFreeTileCol] = newState.puzzleState[freeTileRow][freeTileCol];
                newState.puzzleState[freeTileRow][freeTileCol] = swapElement;
                newState.cost++;
            }
        } else if (action == StateAction.MOVE_LEFT) {
            int newFreeTileCol = freeTileCol - 1;
            if (newFreeTileCol >= 0) {
                int swapElement = newState.puzzleState[freeTileRow][newFreeTileCol];
                newState.puzzleState[freeTileRow][newFreeTileCol] = newState.puzzleState[freeTileRow][freeTileCol];
                newState.puzzleState[freeTileRow][freeTileCol] = swapElement;
                newState.cost++;
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
