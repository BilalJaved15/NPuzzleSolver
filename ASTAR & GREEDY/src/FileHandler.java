import java.io.*;
import java.util.ArrayList;
import java.lang.Math;

public class FileHandler {
    ArrayList<PuzzleState> states;

    private void populateStates() {
        try {
            File file = new File("input.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                String[] state = line.split(",");
                ArrayList<Integer> stateInt = new ArrayList<>();
                for (int i = 0; i < state.length; i++) {
                    stateInt.add(Integer.parseInt(state[i].trim()));
                }
                PuzzleState puzzleState = new PuzzleState((int) Math.sqrt(stateInt.size()), stateInt);
                states.add(puzzleState);
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void writeSolutionsToOutput() {
        try {
            File file = new File("output.txt");
            FileWriter fw = new FileWriter(file, false);
            PrintWriter pw = new PrintWriter(fw);
            pw.println("H1: Number of Misplaced Tiles");
            System.out.println("H1: Number of Misplaced Tiles");
            pw.println("H2: Manhattan Distance");
            System.out.println("H2: Manhattan Distance");
            int finalState1Start = 0;
            int finalState2Start = 1;
            for (int i = 0; i < states.size(); i++) {
                for (int j = 0; j < states.get(i).getSize(); j++) {
                    for (int k = 0; k < states.get(i).getSize(); k++) {
                        if (j == states.get(i).getSize() - 1 && k == states.get(i).getSize() - 1) {
                            System.out.print(states.get(i).get(j, k) + ":");
                            pw.print(states.get(i).get(j, k) + ":");
                        } else {
                            System.out.print(states.get(i).get(j, k) + ",");
                            pw.print(states.get(i).get(j, k) + ",");
                        }
                    }
                }
                System.out.println();
                pw.println();
                pw.println("Final State 1: ");
                System.out.println("Final State 1: ");
                for (int j = 0; j < states.get(i).getSize(); j++){
                    for (int k = 0; k < states.get(i).getSize(); k++) {
                        System.out.print(finalState1Start + " ");
                        pw.print(finalState1Start + " ");
                        finalState1Start++;
                    }
                    pw.println();
                    System.out.println();
                }
                pw.println("Final State 2: ");
                System.out.println("Final State 2: ");
                for (int j = 0; j < states.get(i).getSize(); j++){
                    for (int k = 0; k < states.get(i).getSize(); k++) {
                        System.out.print(finalState2Start + " ");
                        pw.print(finalState2Start + " ");
                        finalState2Start++;
                    }
                    pw.println();
                    System.out.println();
                }
                finalState1Start = 0;
                finalState2Start = 1;
                System.out.println();
                pw.println();
                String astarh1 = new PuzzleSolver_AStar_H1(new PuzzleState((states.get(i)))).solvePuzzle();
                String astarh2 = new PuzzleSolver_AStar_H2(new PuzzleState((states.get(i)))).solvePuzzle();
                String greedyh1 = new PuzzleSolver_Greedy_H1(new PuzzleState((states.get(i)))).solvePuzzle();
                String greedyh2 = new PuzzleSolver_Greedy_H2(new PuzzleState((states.get(i)))).solvePuzzle();
                System.out.println(astarh1);
                System.out.println(astarh2);
                System.out.println(greedyh1);
                System.out.println(greedyh2);
                pw.println(astarh1);
                pw.println(astarh2);
                pw.println(greedyh1);
                pw.println(greedyh2);
                System.out.println();
                pw.println();
            }
            pw.close();
            fw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public FileHandler(String filename) {
        states = new ArrayList<>();
        populateStates();
        writeSolutionsToOutput();
    }
}
