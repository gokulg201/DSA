import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

//$Id$
/**
 * 
 * @author gokul
 *
 */
public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0){
			throw new IllegalArgumentException();
		}
		double[] percolationThresholds=new double[trials];
		 for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            int runs = 0;
            while (!percolation.percolates()) {
                int column;
                int row;

                do {
                    column = 1 + StdRandom.uniform(n);
                    row = 1 + StdRandom.uniform(n);
                } while (percolation.isOpen(row, column));

                percolation.open(row, column);
                runs++;
            }

            percolationThresholds[i] = runs / (double) (n * n);
        }
		mean=StdStats.mean(percolationThresholds);
		stddev=StdStats.stddev(percolationThresholds);
		double confidenceFraction = (1.96 * stddev()) / Math.sqrt(trials);
		confidenceLo = mean - confidenceFraction;
        confidenceHi = mean + confidenceFraction;
	}
	public double mean() {
		return mean;
	}
	public double stddev(){
		return stddev;
	}
	public double confidenceLo() {
		return confidenceLo;
	}
	public double confidenceHi() {
		return confidenceHi;
	}
	public static void main(String[] args){
		int n = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats stats = new PercolationStats(n, T);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
	}
}
