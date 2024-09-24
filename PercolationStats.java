import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private int timesran = 0;
    private double[] percvals;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n < 1 || trials < 1)
        {
            throw new IllegalArgumentException();
        }
        percvals = new double[trials];
        timesran = trials;
        for (int i = 0; i < trials; i++)
        {
            Percolation p = new Percolation(n);
            while (!p.percolates())
            {
                p.open((int)(Math.random()*n+1), (int)(Math.random()*n+1));
            }
            percvals[i] = (double)p.numberOfOpenSites()/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(percvals);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(percvals);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - ((1.96*stddev()/(Math.sqrt(timesran))));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + ((1.96*stddev()/(Math.sqrt(timesran))));
    }

   // test client (see below)
   public static void main(String[] args)
   {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
   }

}


