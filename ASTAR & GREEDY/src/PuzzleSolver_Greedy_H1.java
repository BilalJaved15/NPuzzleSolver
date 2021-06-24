import java.util.*;

public class PuzzleSolver_Greedy_H1 {
    private PriorityQueue<PuzzleState> agenda;
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

    PuzzleSolver_Greedy_H1(PuzzleState initialState) throws Exception {
        this.initialState = initialState;
        generateFinalStates();
        agenda = new PriorityQueue<>(new GREEDYPuzzleStateComparator());
        expanded = new ArrayList<>();
    }

    public String solvePuzzle() throws Exception {
        try {
            initialState.setHOfN(initialState.getMisplacedTileCount(finalState1));
            agenda.add(initialState);
            PuzzleState state;
            do {
                state = agenda.remove();
                if (PuzzleState.isEqual(finalState1, state)) {
                    return ("GREEDY_H1_FINALSTATE1_PATH: " + state.getPath() + "\nGREEDY_H1_FINALSTATE1_COST: "
                            + state.getGOfN());
                }
                expanded.add(state);
                ArrayList<PuzzleState> transferredStates = new ArrayList<>();
                PuzzleState temp = PuzzleState.performAction(state, StateAction.MOVE_UP);
                temp.appendPath("UP");
                temp.setHOfN(temp.getMisplacedTileCount(finalState1));
                transferredStates.add(temp);
                temp = (PuzzleState.performAction(state, StateAction.MOVE_RIGHT));
                temp.appendPath("RIGHT");
                temp.setHOfN(temp.getMisplacedTileCount(finalState1));
                transferredStates.add(temp);
                temp = (PuzzleState.performAction(state, StateAction.MOVE_DOWN));
                temp.appendPath("DOWN");
                temp.setHOfN(temp.getMisplacedTileCount(finalState1));
                transferredStates.add(temp);
                temp = (PuzzleState.performAction(state, StateAction.MOVE_LEFT));
                temp.appendPath("LEFT");
                temp.setHOfN(temp.getMisplacedTileCount(finalState1));
                transferredStates.add(temp);
                for (int i = 0; i < transferredStates.size(); i++) {
                    boolean add = true;
                    for (int j = 0; j < expanded.size(); j++) {
                        if (PuzzleState.isEqual(transferredStates.get(i), expanded.get(j)) == true) {
                            add = false;
                        }
                    }
                    if (add == true) {
                        agenda.add(transferredStates.get(i));
                    }
                }
            } while (agenda.isEmpty() != true);
        } catch (OutOfMemoryError e1) {
            // If this exception is caught, it means no solution exists for first goal
            // state, therefore the whole
            // procedure is started again for
            // second goal state
            System.out.println("GREEDY_H1_FINALSTATE1_PATH: Solution not found.");
            try {
                initialState.setHOfN(initialState.getMisplacedTileCount(finalState2));
                agenda.add(initialState);
                PuzzleState state;
                do {
                    state = agenda.remove();
                    if (PuzzleState.isEqual(finalState2, state)) {
                        return ("GREEDY_H1_FINALSTATE2_PATH: " + state.getPath() + "\nGREEDY_H1_FINALSTATE2_COST: "
                                + state.getGOfN());
                    }
                    expanded.add(state);
                    ArrayList<PuzzleState> transferredStates = new ArrayList<>();
                    PuzzleState temp = PuzzleState.performAction(state, StateAction.MOVE_UP);
                    temp.appendPath("UP");
                    temp.setHOfN(temp.getMisplacedTileCount(finalState2));
                    transferredStates.add(temp);
                    temp = (PuzzleState.performAction(state, StateAction.MOVE_RIGHT));
                    temp.appendPath("RIGHT");
                    temp.setHOfN(temp.getMisplacedTileCount(finalState2));
                    transferredStates.add(temp);
                    temp = (PuzzleState.performAction(state, StateAction.MOVE_DOWN));
                    temp.appendPath("DOWN");
                    temp.setHOfN(temp.getMisplacedTileCount(finalState2));
                    transferredStates.add(temp);
                    temp = (PuzzleState.performAction(state, StateAction.MOVE_LEFT));
                    temp.appendPath("LEFT");
                    temp.setHOfN(temp.getMisplacedTileCount(finalState2));
                    transferredStates.add(temp);
                    for (int i = 0; i < transferredStates.size(); i++) {
                        boolean add = true;
                        for (int j = 0; j < expanded.size(); j++) {
                            if (PuzzleState.isEqual(transferredStates.get(i), expanded.get(j)) == true) {
                                add = false;
                            }
                        }
                        if (add == true) {
                            agenda.add(transferredStates.get(i));
                        }
                    }
                } while (agenda.isEmpty() != true);
            } catch (OutOfMemoryError e2) {
                return "GREEDY_H1_FINALSTATE2_PATH: Solution not found.";
            }
        }
        return "Solution not found.";
    }
}
