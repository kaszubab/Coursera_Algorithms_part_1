/* *****************************************************************************
 *  Name:Bartosz Kaszuba
 *  Date:7.08.2019
 *  Description: Additional benchmark to determine the average time and
 *  percolation threshold
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    // private final double [] resultTab;
    private final int trialCount;
    private final double mean;
    private final double stddev;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Neither size of grid nor trial count cannot be a value equal to or below zero");
        }
        trialCount = trials;
        double [] resultTab = new double[trials];
        for (int i = 0; i < trials; i++)
        {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
            }
            resultTab[i] = (percolation.numberOfOpenSites())/(n*n*1.0);
        }
        mean = StdStats.mean(resultTab);
        stddev = StdStats.stddev(resultTab);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (1.96*stddev)/Math.sqrt(trialCount);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (1.96*stddev)/Math.sqrt(trialCount);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats pstats = new PercolationStats(10, 20);
        System.out.println(pstats.mean());
    }

}