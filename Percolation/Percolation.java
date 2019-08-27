/* *****************************************************************************
 *  Name:Bartosz Kaszuba
 *  Date:7.08.2019
 *  Description: Main program of percolation package. Introduces the main
 *  functionality of perculation algorithm.
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF Find_Union;
    private int open_number = 0;
    private boolean grid[];
    private int grid_size;

    private boolean Out_of_bound_index(int row, int col){
        if(row-1 > grid_size || row-1 < 0 || col - 1 > grid_size || col -1 < 0 ) {
            throw new java.lang.IllegalArgumentException("Out of bound index: "
                                                                 + "input must be between 1 and "
                                                                 + grid_size);
        }
        return false;
    }
    private int Translate_2D_to_1D(int row, int col){
        return (row-1)*grid_size+(col-1);
    }
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if( n < 1){
            throw new java.lang.IllegalArgumentException("Illegal Argument: n may not be lower than 1");
        }else{
            grid_size = n;
            grid = new boolean[grid_size*grid_size];
            for(int i = 1; i < grid_size*grid_size; i++){
                    grid[i]=false;
                }
            Find_Union = new WeightedQuickUnionUF(grid_size*grid_size+2);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(!Out_of_bound_index(row,col)) {
            if (!isOpen(row, col)) {
                grid[Translate_2D_to_1D(row, col)] = true;
                open_number++;
                if(col < grid_size && isOpen(row, col+1)){
                    Find_Union.union(Translate_2D_to_1D(row,col)+1,Translate_2D_to_1D(row,col+1)+1);
                }
                if(col > 1 && isOpen(row, col-1)){
                    Find_Union.union(Translate_2D_to_1D(row,col)+1,Translate_2D_to_1D(row,col-1)+1);
                }
                if(row < grid_size && isOpen(row + 1, col)) {
                    Find_Union.union(Translate_2D_to_1D(row, col)+1, Translate_2D_to_1D(row+1, col)+1);
                }else if(row == grid_size){
                    Find_Union.union(Translate_2D_to_1D(row, col)+1, grid_size*grid_size+1);
                }
                if(row > 1 && isOpen(row-1, col)){
                    Find_Union.union(Translate_2D_to_1D(row,col)+1,Translate_2D_to_1D(row-1,col)+1);
                }else if(row == 1){
                    Find_Union.union(Translate_2D_to_1D(row, col)+1, 0);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(!Out_of_bound_index(row,col))
        {
            return grid[Translate_2D_to_1D(row,col)];
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        return Find_Union.connected(0, Translate_2D_to_1D(row,col)+1);
    }

    // returns the number of open sitesA
    public int numberOfOpenSites(){
        return open_number;
    }

    // does the system percolate?
    public boolean percolates(){
        return Find_Union.connected(0,grid_size*grid_size+1);
    }

    // test client (optional)
    public static void main(String[] args){
        Percolation percolation = new Percolation(5);
        percolation.open(2,2);
        percolation.open(1,2);
        percolation.open(3,2);
        percolation.open(4,2);
        percolation.open(5,2);
        System.out.println(percolation.percolates());
    }
}
