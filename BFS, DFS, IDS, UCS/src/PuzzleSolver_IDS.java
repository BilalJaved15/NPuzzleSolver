import java.util.ArrayList;
import java.util.Stack;

public class PuzzleSolver_IDS {
    private Stack<PuzzleState> agenda;
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

    PuzzleSolver_IDS(PuzzleState initialState) throws Exception {
        this.initialState = initialState;
        generateFinalStates();
        agenda = new Stack<>();
        expanded = new ArrayList<>();
    }

    public String solvePuzzle() throws Exception {
        try {
            int levelCap = 0;
            PuzzleState state = initialState;
            while (!PuzzleState.isEqual(state, finalState1) || !PuzzleState.isEqual(state, finalState2)) {
                agenda.clear();
                expanded.clear();
                agenda.add(initialState);
                state = initialState;
                levelCap++;
                while (agenda.isEmpty() == false) {
                    state = agenda.pop();
                    expanded.add(state);
                    if (state.getCost() < levelCap) {
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
                                return "IDS: " + transferredStates.get(i).getPath();
                            }
                        }
                    }
                }
            }
        } catch (OutOfMemoryError e) {
            return "IDS: Solution not found.";
        }
        return "IDS: Solution not found.";
    }
}

