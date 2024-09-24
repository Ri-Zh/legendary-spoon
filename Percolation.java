import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.In;
public class Percolation {
    private boolean[][] perc;
    private WeightedQuickUnionUF unf;
    private WeightedQuickUnionUF unf2;
    private int n;
    private int opensites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        this.n = n;
        if (n <= 0)
        {
            throw new IllegalArgumentException("n must be larger than 0");
        }
        else
        {
            perc = new boolean[n][n];
            unf = new WeightedQuickUnionUF(n*n +2);
            unf2 = new WeightedQuickUnionUF(n*n +1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        boundcheck(row, col);
        if (!isOpen(row, col))
        {
            perc[row-1][col-1] = true;
            if(row == 1)
            {
                unf.union(cd(row, col), 0);
                unf2.union(cd(row, col), 0);
            } else if (row > 1 && isOpen(row-1,col)){
                unf.union((cd(row, col)), cd(row-1, col));
                unf2.union((cd(row, col)), cd(row-1, col));
            }
            if(row == n)
            {
                unf.union(cd(row, col), n*n+1);
            } else if (row < n && isOpen(row+1,col)){
                unf.union(cd(row, col), cd(row+1, col));
                unf2.union(cd(row, col), cd(row+1, col));
            }
            if (col > 1 && isOpen(row, col-1))
            {
                unf.union(cd(row, col), cd(row, col - 1));
                unf2.union(cd(row, col), cd(row, col - 1));
            }
            if (col < n && isOpen(row, col+1))
            {
                unf.union(cd(row, col), cd(row, col + 1));
                unf2.union(cd(row, col), cd(row, col + 1));
            }
            opensites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        boundcheck(row, col);
        return perc[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        boundcheck(row, col);
        return unf2.find(cd(row, col)) ==  unf2.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return opensites;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return unf.find(0) == unf.find(n*n +1);
    }

    private int cd(int row, int col)
    {
        int ind = (n*(row-1));
        ind += col;
        return ind;
    }
    private void boundcheck(int row, int col)
    {
        if (row < 1 || col < 1 || row > n || col > n)
        {
            throw new IllegalArgumentException();
        } 
    }

    public static void main(String[] args)
    {
        In in = new In(args[0]);
        int n = in.readInt();

        Percolation perc = new Percolation(n);
        while (!in.isEmpty())
        {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
    }
}