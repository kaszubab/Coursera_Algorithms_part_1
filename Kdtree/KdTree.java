/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            this.lb = null;
            this.rt = null;
        }
    }


    private int size;
    private Node root;
    public KdTree()  {
        root = null;
        size = 0;
    } // construct an empty set of points

    private Node insertRecur(Point2D p, Node currNode, boolean even, double xMin, double yMin, double xMax, double yMax) {
        if (currNode == null) {
            Node node = new Node(p, new RectHV(xMin, yMin, xMax, yMax));
            node.lb = null;
            node.rt = null;
            return node;
        }
        // if (currNode.p.equals(p)) return currNode;
        if (even) {
            if (currNode.p.x() <= p.x()) currNode.rt = insertRecur(p, currNode.rt, !even, currNode.p.x(), yMin, xMax, yMax);
            else currNode.lb = insertRecur(p, currNode.lb, !even, xMin, yMin, currNode.p.x(), yMax);
        }
        else {
            if (currNode.p.y() <= p.y()) currNode.rt = insertRecur(p, currNode.rt, !even, xMin, currNode.p.y(), xMax, yMax);
            else currNode.lb = insertRecur(p, currNode.lb, !even, xMin, yMin, xMax, currNode.p.y());
        }
        return currNode;
    }



    private void drawRecur(Node currNode) {
        if (currNode != null) {
            currNode.p.draw();
            drawRecur(currNode.lb);
            drawRecur(currNode.rt);
        }
    }

    public boolean isEmpty()  {
        return  root == null;
    }                     // is the set empty?
    public int size() {
        return size;
    }                         // number of points in the set
    public void insert(Point2D p) {
        if (!contains(p)) {
            root = insertRecur(p, this.root, true, 0, 0, 1, 1);
            this.size++;
        }
    }

    // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {
        Node temp = this.root;
        boolean even = true;
        while (temp != null && !temp.p.equals(p)) {
            if (even) {
                if (temp.p.x() <= p.x()) temp = temp.rt;
                else temp = temp.lb;
            }
            else {
                if (temp.p.y() <= p.y()) temp = temp.rt;
                else temp = temp.lb;
            }
            even = !even;
        }
        return !(temp == null);
    }          // does the set contain point p?



    public void draw()  {
        drawRecur(this.root);
    }                      // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> point2DStack = new Stack<>();
        rangeRecur(root, rect, point2DStack);
        return  point2DStack;
    }            // all points that are inside the rectangle (or on the boundary)

    private void rangeRecur(Node currNode, RectHV rect, Stack<Point2D> stack) {
        if (currNode == null) return;
        if (!currNode.rect.intersects(rect)) return;
        if (rect.contains(currNode.p)) stack.push(currNode.p);

        rangeRecur(currNode.lb, rect, stack);
        rangeRecur(currNode.rt, rect, stack);
    }



    public Point2D nearest(Point2D p) {
        return  new Point2D(4,5);
    }

    public static void main(String[] args)     {
        KdTree tri = new KdTree();
        // StdOut.println(tri.isEmpty());
        // StdOut.println(tri.size);
        Point2D p = new Point2D(0.4, 0.5);
        tri.insert(p);
        // StdOut.println(tri.isEmpty());
        // StdOut.println(tri.size);
        p = new Point2D(0.6, 0.8);
        tri.insert(p);
        p = new Point2D(0.2, 0.7);
        tri.insert(p);
        p = new Point2D(0.8, 0.3);
        tri.insert(p);
        // StdOut.println(tri.isEmpty());
        // StdOut.println(tri.size);
        p = new Point2D(0.2, 0.7);
         StdOut.println(tri.contains(p));
        p = new Point2D(0.6, 0.8);
        StdOut.println(tri.contains(p));
        p = new Point2D(0.8, 0.3);
        StdOut.println(tri.contains(p));
        p = new Point2D(0.4, 0.5);
        StdOut.println(tri.contains(p));
        // tri.draw();
    }

}
