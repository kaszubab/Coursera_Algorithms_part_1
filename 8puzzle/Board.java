/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

public class Board {


    private int [][] board;
    private final int boardDim;
    private final int hamming;
    private final int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        boardDim = tiles.length;
        board = new int[boardDim][boardDim];
        board = dimArrayCopy(tiles);
        hamming = setHamming();
        manhattan = setManhattan();
    }

    private int[][] dimArrayCopy(int [][] arr) {
        int[][] copyArray = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                copyArray[i][j] = arr[i][j];
            }
        }
        return copyArray;
    }

    public int setHamming() {
        int hamingDist = 0;
        int item;
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                if (board[i][j] != 0 && board[i][j]-1 != i*boardDim + j) {
                    hamingDist++;
                }
            }
        }
        return hamingDist;

    }

    public int setManhattan() {
        int manhattanDist = 0;
        int item;
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                if (board[i][j] != 0) {
                    item = board[i][j]-1;
                    manhattanDist += Math.abs(i - item/boardDim) + Math.abs(j - item % boardDim);
                }
            }
        }
        return manhattanDist;
    }


    private void swap(int [][] arr, int i1, int j1, int i2, int j2) {
        int tmp = arr[i1][j1];
        arr[i1][j1] = arr[i2][j2];
        arr[i2][j2] = tmp;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBoard = new StringBuilder();
        stringBoard.append(boardDim+"\n");
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                stringBoard.append(String.format("%2d ", board[i][j]));
            }
            stringBoard.append("\n");
        }
        return stringBoard.toString();
    }

    // board dimension n
    public int dimension() {
        return boardDim;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (this.hamming() == 0) return true;
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                if (that.board[i][j] != this.board[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boardStack = new Stack<>();
        int i = 0, j = 0;
        while (board[i][j] != 0) {
            i = (i + 1) % boardDim;
            j = (j + 1) % boardDim;
        }
        if (i > 0) {
            int [][] newBoard; //= new int[boardDim][boardDim];
            newBoard = dimArrayCopy(this.board);
            swap(newBoard, i-1, j, i, j);
            Board neighbour = new Board(newBoard);
            boardStack.push(neighbour);
        }
        if (i < boardDim -1) {
            int [][] newBoard; //= new int[boardDim][boardDim];
            newBoard = dimArrayCopy(this.board);
            swap(newBoard, i+1, j, i, j);
            Board neighbour = new Board(newBoard);
            boardStack.push(neighbour);
        }
        if (j > 0) {
            int [][] newBoard; // = new int[boardDim][boardDim];
            newBoard = dimArrayCopy(this.board);
            swap(newBoard, i, j - 1, i, j);
            Board neighbour = new Board(newBoard);
            boardStack.push(neighbour);
        }
        if (j < boardDim - 1) {
            int [][] newBoard; // = new int[boardDim][boardDim];
            newBoard = dimArrayCopy(this.board);
            swap(newBoard, i, j+1, i, j);
            Board neighbour = new Board(newBoard);
            boardStack.push(neighbour);
        }
        return boardStack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (this.board[0][0] != 0) {
            if (board[0][1] != 0) {
                int [][] newBoard; // = new int[boardDim][boardDim];
                newBoard = dimArrayCopy(this.board);
                swap(newBoard, 0, 1, 0, 0);
                Board twin = new Board(newBoard);
                return twin;
            }
            int [][] newBoard; // = new int[boardDim][boardDim];
            newBoard = dimArrayCopy(this.board);
            swap(newBoard, 1, 0, 0, 0);
            Board twin = new Board(newBoard);
            return twin;
        }
        int [][] newBoard; // = new int[boardDim][boardDim];
        newBoard = dimArrayCopy(this.board);
        swap(newBoard, 0, 1, 1, 0);
        Board twin = new Board(newBoard);
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] arr = {{1, 2}, {3, 0}};
        Board myBoard = new Board(arr);
        System.out.println(myBoard);
        System.out.println(myBoard.hamming());
        System.out.println(myBoard.manhattan());
        System.out.println(myBoard.isGoal());
        System.out.println(myBoard.manhattan());
        System.out.println(myBoard.twin());
        for (Board x : myBoard.neighbors()) System.out.println(x);
    }

}
