import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
/**
 * Created by ArtemParfenov on 25.06.2018.
 */

public class PercolationStats {
    /**site amount opened to system became percolate*/
    private final double [] sites;
    private double mean;
    //    private double stddev;
    private final int t;

    /**perform T independent experiments on an N-by-N grid*/
    public PercolationStats(int N, int T) {
        if ((N <= 0) || (T <= 0)){
            throw new IllegalArgumentException();
        }
        t = T;
        sites = new double [t];
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(N);
            int ii, jj, openedSiteAmount = 0;
            while (!p.percolates()) {
                ii = StdRandom.uniform(1,N + 1);
                jj = StdRandom.uniform(1,N + 1);
                if (!p.isOpen(ii, jj)) {
                    p.open(ii, jj);
                    openedSiteAmount++;
                }
            }
            sites[i] = (double)openedSiteAmount/(N*N);
        }
    }
    /**sample mean of percolation threshold*/
    public double mean() {
        return mean = StdStats.mean(sites);
    }
    /**sample standard deviation of percolation threshold*/
    public double stddev() {
        return StdStats.stddev(sites);
    }
    /**low  endpoint of 95% confidence interval*/
    public double confidenceLo() {
        return mean - 1.96*stddev()/Math.sqrt(t);
    }
    /**high endpoint of 95% confidence interval*/
    public double confidenceHi() {
        return mean + 1.96*stddev()/Math.sqrt(t);
    }

    /**test client (described below)*/
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}

