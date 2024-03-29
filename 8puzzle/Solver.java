/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private searchNode solution;

    private class searchNode implements Comparable<searchNode> {
        private Board currBoard;
        private int priorityValue;
        private int movesNumber;
        private searchNode prevNode;

        public searchNode(Board board, int movesNumber, searchNode prevNode) {
            this.currBoard = board;
            this.movesNumber = movesNumber;
            this.prevNode = prevNode;
            this.priorityValue = movesNumber + board.manhattan();
        }

        @Override
        public int compareTo(searchNode other) {
            return (this.priorityValue - other.priorityValue);
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
                    break;
                }
                for (Board x : currNode.currBoard.neighbors()) {
                    if (currNode.prevNode == null) {
                        searchNode nsNode = new searchNode(x, 1 + currNode.movesNumber, currNode);
                        PQ1.insert(nsNode);
                    }
                    else if (!x.equals(currNode.prevNode.currBoard)) {
                        searchNode nsNode = new searchNode(x, 1 + currNode.movesNumber, currNode);
                        PQ1.insert(nsNode);
                    }
                }
                change = false;
            }
            else {
                currNode = PQ2.delMin();
                if (currNode.currBoard.isGoal()) {
                    solution = null;
                    break;
                }
                for (Board x : currNode.currBoard.neighbors()) {
                    if (currNode.prevNode == null) {
                        searchNode nsNode = new searchNode(x, 1 + currNode.movesNumber, currNode);
                        PQ2.insert(nsNode);
                    }
                    else if (!x.equals(currNode.prevNode.currBoard)) {
                        searchNode nsNode = new searchNode(x, 1 + currNode.movesNumber, currNode);
                        PQ2.insert(nsNode);
                    }
                }
                change = true;
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable()) return solution.movesNumber;
        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (solution == null) {
            return null;
        }
        Stack<Board> boardStack = new Stack<>();
        searchNode iterableSolution = solution;
        while (iterableSolution != null) {
            boardStack.push(iterableSolution.currBoard);
            iterableSolution = iterableSolution.prevNode;
        }
        return boardStack;
    }

    // test client (see below)
    public static void main(String[] args) {
        int [][] arr = {{1,2},{0,3}};
        Board board = new Board(arr);
       Solver solver = new Solver(board);
       System.out.println(solver.moves());
     }

}
