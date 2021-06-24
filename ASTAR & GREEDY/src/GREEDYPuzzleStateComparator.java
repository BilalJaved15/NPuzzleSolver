import java.util.Comparator;

public class GREEDYPuzzleStateComparator implements Comparator<PuzzleState> {
    @Override
    public int compare(PuzzleState o1, PuzzleState o2) {
        if (o1.getHOfN() == o2.getHOfN()){
            return 0;
        } else if(o1.getHOfN() > o2.getHOfN()){
            return  1;
        } else{
            return -1;
        }
    }
}
