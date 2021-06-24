import java.util.Comparator;

public class ASTARPuzzleStateComparator implements Comparator<PuzzleState> {
    @Override
    public int compare(PuzzleState o1, PuzzleState o2) {
        return (o1.getGOfN() + o1.getHOfN()) - (o2.getGOfN() + o2.getHOfN());
    }
}
