
/**
 * Created by ArtemParfenov on 25.06.2018.
 */
public class Percolation {
    private int n;
    /**
     * id array
     */
    private final int[] roots;
    /**
     * weights of every root
     */
    private int[] weights;
    private int openedSitesAmount = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        this.n = n;
        roots = new int[n * n + 2];
        weights = new int[n * n + 2];
        /** initializing id array and weight array*/
        for (int i = 0; i <= n * n + 1; i++) {
            roots[i] = i;
            //0 means site is NOT open, 1 means it is open but has no connected children
            weights[i] = 0;
        }
        //opening the top and the bottom element, setting the weight n^2/2 for the top and the bottom
        weights[0] = (n * n + 2) / 2;//+2 - top and bottom elements
        weights[n * n + 1] = (n * n + 2) / 2;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            openedSitesAmount++;
            weights[(row - 1) * n + col] = 1;
            /** check the neighbours are opened too, if they are - union with them*/
            //top row
            if (row == 1) {
                union((row - 1) * n + col, 0);
            } else if ((row > 1) && isOpen(row - 1, col)) {
                //neighbour from the top
                union((row - 1) * n + col, (row - 2) * n + col);
            }
            if (row == n) {
                //bottom row
                union((row - 1) * n + col, n * n + 1);
            } else if ((row < n) && isOpen(row + 1, col)) {
                //bottom neighbour
                union((row - 1) * n + col, row * n + col);
            }
            if ((col < n) && isOpen(row, col + 1)) {
                //neighbour to the right
                union((row - 1) * n + col, (row - 1) * n + col + 1);
            }
            if ((col > 1) && isOpen(row, col - 1)) {
                //neighbour to the left
                union((row - 1) * n + col, (row - 1) * n + col - 1);
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return weights[(row - 1) * n + col] > 0;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        //the site is full when it is connected to the top element
        return isConnected(0, (row - 1) * n + col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openedSitesAmount;
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnected(0, n * n + 1);
    }

    private boolean isConnected(int p, int q) {
        return getRoot(p) == getRoot(q);
    }

    /**
     * connects two elements with indexes p and q
     */
    private void union(int p, int q) {
        int pRootInd = getRoot(p);
        int qRootInd = getRoot(q);
        if (pRootInd != qRootInd) {
            //mooving root
            if (weights[pRootInd] > weights[qRootInd]) {
                roots[qRootInd] = roots[pRootInd];
                //update root weight
                weights[pRootInd] += weights[qRootInd];
            } else {
                roots[pRootInd] = roots[qRootInd];
                weights[qRootInd] += weights[pRootInd];
            }
        }
    }

    /**
     * returns a root for the specified element
     */
    private int getRoot(int p) {
        while (roots[p] != p) {
            p = roots[p];
        }
        return p;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}

