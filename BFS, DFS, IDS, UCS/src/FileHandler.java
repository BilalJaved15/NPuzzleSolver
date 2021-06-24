import java.io.*;
import java.util.ArrayList;
import java.lang.Math;

public class FileHandler {
    ArrayList<PuzzleState> states;

    private void populateStates(){
        try {
            File file = new File("input.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                String[] state = line.split(",");
                ArrayList<Integer> stateInt = new ArrayList<>();
                for (int i = 0; i < state.length; i++){
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
            for (int i = 0; i < states.size(); i++){
                for (int j = 0; j < states.get(i).getSize(); j++){
                    for (int k = 0; k < states.get(i).getSize(); k++){
                        if (j == states.get(i).getSize() - 1 && k == states.get(i).getSize() - 1){
                            System.out.print(states.get(i).get(j, k) + ":");
                            pw.print(states.get(i).get(j, k) + ":");
                        }
                        else{
                            System.out.print(states.get(i).get(j, k) + ",");
                            pw.print(states.get(i).get(j, k) + ",");
                        }
                    }
                }
                System.out.println();
                pw.println();
                String bfs = new PuzzleSolver_BFS(states.get(i)).solvePuzzle();
                String dfs = new PuzzleSolver_DFS(states.get(i)).solvePuzzle();
                String ids = new PuzzleSolver_IDS(states.get(i)).solvePuzzle();
                String ucs = new PuzzleSolver_UCS(states.get(i)).solvePuzzle();
                System.out.println(bfs);
                System.out.println(dfs);
                System.out.println(ids);
                System.out.println(ucs);
                pw.println(bfs);
                pw.println(dfs);
                pw.println(ids);
                pw.println(ucs);
            }
            pw.close();
            fw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public FileHandler(String filename){
        states = new ArrayList<>();
        populateStates();
        writeSolutionsToOutput();
    }
}
