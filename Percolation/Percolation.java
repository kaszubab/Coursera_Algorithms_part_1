/* *****************************************************************************
 *  Name:Bartosz Kaszuba
 *  Date:7.08.2019
 *  Description: Main program of percolation package. Introduces the main
 *  functionality of perculation algorithm.
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF findUnion;
    private int openNumber = 0;
    private byte [] grid;
    private int gridSize;
    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException("Illegal Argument: n may not be lower than 1");
        } else {
            gridSize = n;
            grid = new byte[gridSize*gridSize]; // boolean array to check whether a site is open
            for (int i = 1; i < gridSize*gridSize; i++) {
                grid[i] = 0;
            }
            findUnion = new WeightedQuickUnionUF(gridSize*gridSize + 1); // create only one virtual node to avoid problem of bottom sites being connected by a bottom virtual site, last array index is a top virtual node
        }
    }

    private boolean outOfBoundIndex(int row, int col) { // additional function to check a correctness of indices in the input
        if (row > gridSize || row < 1 || col  > gridSize || col  < 1) {
            throw new java.lang.IllegalArgumentException("Out of bound index:"+row+" " + col + " input must be between 1 and " + gridSize);
        }
        return false;
    }
    private int translate2DTo1D(int row, int col) { return (row-1)*gridSize+(col-1); } // simple function that translates 2d coordinates to an integer that works as an index for an array

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) { // site can have combination of basic states states : 000 closed , 001 - open, 010 - connected to top, 100 - connected to bottom
        if (!outOfBoundIndex(row, col)) {
            if (!isOpen(row, col)) {
                if (row == 1) grid[translate2DTo1D(row, col)] |= 3;
                if (row == gridSize) grid[translate2DTo1D(row, col)] |= 5;
                else grid[translate2DTo1D(row, col)] |= 1;
                openNumber++;
                int newGridValue = grid[translate2DTo1D(row, col)];
                if (col < gridSize && isOpen(row, col+1)) { // checking whether neighbouring sites are open
                    newGridValue |= grid[findUnion.find(translate2DTo1D(row, col+1))];
                    findUnion.union(translate2DTo1D(row, col), translate2DTo1D(row, col+1));
                }
                if (col > 1 && isOpen(row, col-1)) {
                    newGridValue |= grid[findUnion.find(translate2DTo1D(row, col-1))];
                    findUnion.union(translate2DTo1D(row, col), translate2DTo1D(row, col-1));
                }
                if (row < gridSize && isOpen(row + 1, col)) {
                    newGridValue |= grid[findUnion.find(translate2DTo1D(row+1, col))];
                    findUnion.union(translate2DTo1D(row, col), translate2DTo1D(row+1, col));
                }
                if (row > 1 && isOpen(row-1, col)) {
                    newGridValue |= grid[findUnion.find(translate2DTo1D(row-1, col))];
                    findUnion.union(translate2DTo1D(row, col), translate2DTo1D(row-1, col));
                }
                grid[findUnion.find(translate2DTo1D(row, col))] |= newGridValue;
                if ((grid[findUnion.find(translate2DTo1D(row, col))] & 6) == 6)
                    percolates = true;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!outOfBoundIndex(row, col))
        {
            return (grid[translate2DTo1D(row, col)] & 1) == 1;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!outOfBoundIndex(row, col)) {
            return (grid[findUnion.find(translate2DTo1D(row, col))] & 2) == 2;
        }
        return false;
    }

    // returns the number of open sitesA
    public int numberOfOpenSites() {
        return openNumber;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(2, 2);
        percolation.open(1, 2);
        percolation.open(3, 2);
        percolation.open(4, 2);
        percolation.open(5, 2);
        System.out.println(percolation.percolates());
    }
}
