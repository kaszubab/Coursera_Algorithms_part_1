/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private searchNode solution;
    boolean solvable;

    private class searchNode implements Comparable<searchNode> {
        private Board currBoard;
        private int priorityValue
        private int movesNumber;
        private searchNode prevNode;

        public searchNode(Board Board, int movesNumber, searchNode prevNode) {
            this.currBoard = Board;
            this.movesNumber = movesNumber;
            this.prevNode = prevNode;
            this.priorityValue = movesNumber + Board.manhattan();
        }

        @Override
        public int compareTo(searchNode o) {
            return (this.priorityValue - o.priorityValue);
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        searchNode initialNode1 = new searchNode(initial, 0, null);
        searchNode initialNode2 = new searchNode(initial.twin(), 0, null);
        MinPQ<searchNode> PQ1 = new MinPQ<>();
        MinPQ<searchNode> PQ2 = new MinPQ<>();
        PQ1.insert(initialNode1);
        PQ2.insert(initialNode2);
        boolean change = true;
        while (!PQ1.isEmpty() && !PQ2.isEmpty()) {
            searchNode currNode;
            if (change) {
                currNode = PQ1.delMin();
                if (currNode.currBoard.isGoal()) {
                    solution = currNode;
                    solvable = true;
                    break;
                }
                for (Board x : currNode.currBoard.neighbors()) {
                    searchNode prevNodes = currNode.prevNode;
                    while (prevNodes != null && !prevNodes.currBoard.equals(x))prevNodes = prevNodes.prevNode;
                    if (prevNodes == null) {
                        searchNode nsNode = new searchNode(x, currNode.movesNumber, currNode);
                        PQ1.insert(nsNode);
                    }
                }
                change = false;
            }
            else {
                currNode = PQ2.delMin();
                if (currNode.currBoard.isGoal()) {
                    solution = currNode;
                    solvable = false;
                    break;
                }
                for (Board x : currNode.currBoard.neighbors()) {
                    searchNode prevNodes = currNode.prevNode;
                    while (prevNodes != null && !prevNodes.currBoard.equals(x))prevNodes = prevNodes.prevNode;
                    if (prevNodes == null) {
                        searchNode nsNode = new searchNode(x, currNode.movesNumber, currNode);
                        PQ2.insert(nsNode);
                    }
                }
                change = true;
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return solution.movesNumber;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() 

    // test client (see below)
    public static void main(String[] args)

}
