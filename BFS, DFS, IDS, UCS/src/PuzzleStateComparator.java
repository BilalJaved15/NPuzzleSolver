import java.util.Comparator;

public class PuzzleStateComparator implements Comparator<PuzzleState> {
    @Override
    public int compare(PuzzleState o1, PuzzleState o2) {
        return o1.getCost() - o2.getCost();
    }
}
