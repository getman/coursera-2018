import edu.princeton.cs.algs4.StdRandom;
/**
 * Created by ArtemParfenov on 25.06.2018.
 */

public class Percolation2 {
    // ---------Data members----------
    /** indexes of roots of the array elements*/
    private int [] rootIndexes;
    private int [] rootIndexes2;
    /** elements amount for every root*/
    private int [] rootWeights;
    private final int n;
    private int openedSitesAmount = 0;

    // -------------Methods----------
    /** create N-by-N grid, with all sites blocked*/
    public Percolation2(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("n is not correct:" + N);
        }
        n = N;
        rootIndexes = new int[n*n + 2];
        rootIndexes2 = new int[n*n + 1];
        rootWeights = new int[n*n + 2];
        /** array content:
         * 0 - top element
         * 1..n*n - elements
         * n*n+1 - bottom element*/
        rootIndexes[0] = 0;
        rootIndexes2[0] = 0;
        rootWeights[0] = 1;
        rootIndexes[n*n+1] = n*n + 1;
        rootWeights[n*n+1] = n*n + 2;
        for (int i = 1; i <= n*n; i++) {
            rootIndexes[i] = -1;
            if (i <= n*n) {
                rootIndexes2[i] = -1;
            }
            rootWeights[i] = 0;
        }
    }
    /** open site (row i, column j) if it is not open already*/
    public void open(int i, int j) {
        if ((!isInRange(i)) || (!isInRange(j))) {
            throw new IndexOutOfBoundsException();
        }
        int elementIndex = i*n - (n-j);
        if (rootIndexes[elementIndex] == -1) {
            rootIndexes[elementIndex] = elementIndex;
            rootIndexes2[elementIndex] = elementIndex;
            rootWeights[elementIndex] = 1;
            openedSitesAmount++;
            // connecting to neighbours
            // upper neighbour
            if (i > 1) { //except the 1st row
                union(elementIndex, elementIndex - n);
            }
            // lower neighbour
            if (i < n) { //except the last row
                union(elementIndex, elementIndex + n);
            }
            // left neighbour
            if (j > 1) { //except left "wall"
                union(elementIndex, elementIndex - 1);
            }
            // right neighbour
            if (j < n) { //except to right "wall"
                union(elementIndex, elementIndex + 1);
            }
            // top row`s elements should be connected to 0 element
            if (i == 1) {
                union(elementIndex, 0);
            }
            // bottom row`s elements should be connected to n*n + 1 element
            if (i == n) {
                union(elementIndex, n*n + 1);
            }
        }
    }
    /** is site (row i, column j) open?*/
    public boolean isOpen(int i, int j) {
        if ((!isInRange(i)) || (!isInRange(j))) {
            throw new IndexOutOfBoundsException();
        }
        return rootIndexes[i*n - (n-j)] != -1;
    }
    /** is site (row i, column j) full?*/
    public boolean isFull(int i, int j) {
        if ((!isInRange(i)) || (!isInRange(j))) {
            throw new IndexOutOfBoundsException();
        }
        return connected(0, i*n - (n-j)) && connected2(0, i*n - (n-j));
    }
    /** does the system percolate?*/
    public boolean percolates() {
        return connected(0, n*n + 1);
    }

    /** checks whether p is connected to q*/
    private boolean connected(int pInd, int qInd) {
        int rootP = root(pInd);
        int rootQ = root(qInd);
        return (rootP != -1) && (rootQ != -1) && (rootP == rootQ);
    }

    private boolean connected2(int pInd2, int qInd2) {
        int rootP2 = root2(pInd2);
        int rootQ2 = root2(qInd2);
        return (rootP2 != -1) && (rootQ2 != -1) && (rootP2 == rootQ2);
    }

    /** calculates the root for the element p
     * @param pInd int - index of the element to find the root for
     * @return index of the root for the specified element or -1 if it is not open at all*/
    private int root(int pInd) {
        if (rootIndexes[pInd] != -1) {
            while (rootIndexes[pInd] != pInd) {
                pInd = rootIndexes[pInd];
            }
            return pInd;
        }
        return -1;
    }

    private int root2(int pInd2) {
        if (rootIndexes2[pInd2] != -1) {
            while (rootIndexes2[pInd2] != pInd2) {
                pInd2 = rootIndexes2[pInd2];
            }
            return pInd2;
        }
        return -1;
    }

    /** performs a union of two opened sites*/
    private void union(int pInd, int qInd) {
        if ((rootIndexes[pInd] != -1) && (rootIndexes[qInd] != -1)) {
            //compare weights of elements` roots
            int rootPInd = root(pInd);
            int rootQInd = root(qInd);
            int weightP = rootWeights[rootPInd];
            int weightQ = rootWeights[rootQInd];
            if ((weightP == weightQ) || (weightP > weightQ)) {
                rootIndexes[rootQInd] = rootIndexes[rootPInd];
                //detecting possibility to write data into supplement rootINdexes2
                /** if ((pInd <= n*n) && (qInd <= n*n)) {
                 union2(qInd, pInd);
                 }*/
                if ((rootPInd <= n*n) && (rootQInd <= n*n) && (pInd <= n*n) && (qInd <= n*n)) {
                    rootIndexes2[rootQInd] = rootIndexes2[rootPInd];
                }
                rootWeights[rootPInd] += rootWeights[rootQInd];
            } else if (weightP < weightQ) {
                rootIndexes[rootPInd] = rootIndexes[rootQInd];
                //detecting possibility to write data into supplement rootINdexes2
                /** if ((pInd <= n*n) && (qInd <= n*n)) {
                 union2(pInd, qInd);
                 }*/
                if ((rootPInd <= n*n) && (rootQInd <= n*n) && (pInd <= n*n) && (qInd <= n*n)) {
                    rootIndexes2[rootPInd] = rootIndexes2[rootQInd];
                }
                rootWeights[rootQInd] += rootWeights[rootPInd];
            }
        }
    }

    /** private void union2(int pInd2, int qInd2) {
     int rootPInd2 = root2(pInd2);
     int rootQInd2 = root2(qInd2);
     rootIndexes2[rootPInd2] = rootIndexes2[rootQInd2];
     }*/

    private boolean isInRange(int i) {
        if ((i <= 0) || (i > n)) {
            return false;
        } else {
            return true;
        }
    }

    public int numberOfOpenSites() {
        return openedSitesAmount;
    }

    /** test client (optional)*/
    public static void main(String[] args) {
        Percolation2 p = new Percolation2(5);
//        StdRandom.setSeed(System.currentTimeMillis());
        int i, j;
        i = 9 % 10;
        while (!p.percolates()) {
            i = StdRandom.uniform(1,6);
            j = StdRandom.uniform(1,6);
            p.open(i, j);
        }
    }

}
