public class Point {
    private int x;
    private int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Integer getManhattanDistance(Point p1, Point p2) {
        return Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y);
    }
}
