/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment [] segments;
    private int segmentQuantity;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Null value discovered");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Null value discovered");
            for (int j = i+1; j < points.length; j++) {
                if (points[i].equals(points[j])) throw new IllegalArgumentException("Duplicate values");
            }
        }
        Point [] tmpPoint = points.clone();
        segmentQuantity = 0;
        Bag<LineSegment> bag = new Bag<>();
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(tmpPoint);
            Arrays.sort(tmpPoint, points[i].slopeOrder());
            for (int j = 1; j < tmpPoint.length - 2 ; j++) {
                int startIndex = j;
                while (j < points.length-1 && points[i].slopeOrder().compare(tmpPoint[j], tmpPoint[j + 1]) == 0) {
                    j++;
                }

                if (j - startIndex >= 2) {
                    Arrays.sort(tmpPoint, startIndex, j);
                    if(points[i].compareTo(tmpPoint[startIndex]) < 0) {
                        bag.add(new LineSegment(points[i], tmpPoint[j]));
                    }
                }

            }
        }
        segments = new LineSegment[bag.size()];
        for (LineSegment x : bag) {
            segments[segmentQuantity++] = x;
        }
    }
     // finds all line segments containing 4 or more points
    public int numberOfSegments() { return segmentQuantity; }        // the number of line segments
    public LineSegment[] segments() {
        return segments.clone();
    }               // the line segments
}
