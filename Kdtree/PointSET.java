/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private SET<Point2D> rBTree;
    public PointSET() {
        rBTree = new SET<>();
    }                             // construct an empty set of points
    public boolean isEmpty() {
        return rBTree.isEmpty();
    }                     // is the set empty?
    public               int size() {
        return rBTree.size();
    }                         // number of points in the set
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        rBTree.add(p);
    }              // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return rBTree.contains(p);
    }            // does the set contain point p?
    public void draw() {
        for (Point2D x : rBTree) {
            x.draw();
        }

    }                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> point2DStack = new Stack<>();
        for (Point2D x : rBTree) {
            if (rect.contains(x)) {
                point2DStack.push(x);
            }
        }
        return point2DStack;

    } // all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D nearest = null;
        double shortestDist = 3.0;
        for (Point2D x : rBTree) {
            if (x.distanceSquaredTo(p) < shortestDist) {
                shortestDist = x.distanceSquaredTo(p);
                nearest = x;
            }
        }
        return  nearest;
    }        // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args)     {

    }              // unit testing of the methods (optional)
}
