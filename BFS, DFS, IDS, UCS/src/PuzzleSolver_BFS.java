import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PuzzleSolver_BFS {
    private ArrayList<PuzzleState> agenda; //ArrayList is used for iteration purposes, otherwise the function is same
    private ArrayList<PuzzleState> expanded;
    private PuzzleState initialState;
    private PuzzleState finalState1;
    private PuzzleState finalState2;

    private void generateFinalStates() throws Exception {
        ArrayList<Integer> input1 = new ArrayList<>();
        ArrayList<Integer> input2 = new ArrayList<>();
        for (int i = 0; i < initialState.getSize() * initialState.getSize(); i++) {
            input1.add(i);
            input2.add(i + 1);
        }
        input2.set(initialState.getSize() * initialState.getSize() - 1, 0);
        finalState1 = new PuzzleState(initialState.getSize(), input1);
        finalState2 = new PuzzleState(initialState.getSize(), input2);
    }

    PuzzleSolver_BFS(PuzzleState initialState) throws Exception {
        this.initialState = initialState;
        generateFinalStates();
        agenda = new ArrayList<>();
        expanded = new ArrayList<>();
    }

    public String solvePuzzle() throws Exception {
        try{
            agenda.add(initialState);
            PuzzleState state;
            do {
                state = agenda.remove(0);
                expanded.add(state);
                ArrayList<PuzzleState> transferredStates = new ArrayList<>();
                transferredStates.add(PuzzleState.performAction(state, StateAction.MOVE_UP));
                transferredStates.get(0).appendPath("UP");
                transferredStates.add(PuzzleState.performAction(state, StateAction.MOVE_RIGHT));
                transferredStates.get(1).appendPath("RIGHT");
                transferredStates.add(PuzzleState.performAction(state, StateAction.MOVE_DOWN));
                transferredStates.get(2).appendPath("DOWN");
                transferredStates.add(PuzzleState.performAction(state, StateAction.MOVE_LEFT));
                transferredStates.get(3).appendPath("LEFT");
                for (int i = 0; i < transferredStates.size(); i++) {
                    boolean add = true;
                    for (int j = 0; j < expanded.size(); j++) {
                        if (PuzzleState.isEqual(transferredStates.get(i), expanded.get(j)) == true) {
                            add = false;
                        }
                    }
                    for (int j = 0; j < agenda.size(); j++) {
                        if (agenda.size() > 0) {
                            if (PuzzleState.isEqual(transferredStates.get(i), agenda.get(j)) == true) {
                                add = false;
                            }
                        }
                    }
                    if (add == true) {
                        agenda.add(transferredStates.get(i));
                    }
                }
                for (int i = 0; i < transferredStates.size(); i++) {
                    if (PuzzleState.isEqual(transferredStates.get(i), finalState1) ||
                            PuzzleState.isEqual(transferredStates.get(i), finalState2)) {
                        return "BFS: " + transferredStates.get(i).getPath();
                    }
                }
            } while (agenda.isEmpty() != true);
        } catch (OutOfMemoryError e) {
            return "BFS: Solution not found.";
        }
        return "BFS: Solution not found.";
    }


}

