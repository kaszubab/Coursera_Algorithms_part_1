/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;

import java.util.Arrays;

public class BruteCollinearPoints {

    private int segmentsQuantity;
    private LineSegment [] segments;

    public BruteCollinearPoints(Point[] points)  {
        if (points == null) throw new IllegalArgumentException("Null value discovered");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Null value discovered");
            for (int j = i+1; j < points.length; j++) {
                if (points[i].equals(points[j])) throw new IllegalArgumentException("Duplicate values");
            }
        }
        segmentsQuantity = 0;
        Bag<LineSegment> bag = new Bag<>();
        for (int p1 = 0; p1 < points.length - 3; p1++) {
            for (int p2 = p1+1; p2 < points.length - 2; p2++) {
                for (int p3 = p2 + 1; p3 < points.length - 1; p3++) {
                        if (points[p1].slopeOrder().compare(points[p2], points[p3]) == 0) {
                            for (int p4 = p3 +1; p4 < points.length; p4++) {
                                if (points[p1].slopeOrder().compare(points[p3], points[p4]) == 0) {
                                    Point [] pointsArr = {points[p1], points[p2], points[p3], points[p4]};
                                    Arrays.sort(pointsArr);
                                    LineSegment newSegment = new LineSegment(pointsArr[0], pointsArr[3]);
                                    bag.add(newSegment);
                                }
                            }
                        }
                }
            }
        }
        segments = new LineSegment[bag.size()];
        for (LineSegment x : bag) {
            segments[segmentsQuantity++] = x;
        }
    }   // finds all line segments containing 4 points
    public int numberOfSegments() { return segmentsQuantity; }       // the number of line segments
    public LineSegment[] segments() { return segments; }                // the line segments
}