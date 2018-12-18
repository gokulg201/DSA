import edu.princeton.cs.algs4.WeightedQuickUnionUF;

//$Id$
/**
 * 
 * @author gokul
 *
 */
public class Percolation {
	private final WeightedQuickUnionUF connectedGrid;
	private boolean[] blockedGrid;
	private final int size, virtualTop, virtualBottom;
	private int openSites;
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		size = n;
		connectedGrid = new WeightedQuickUnionUF(n*n + 2);
		blockedGrid = new boolean[n*n+2];

		// Sets all the sites to be blocked
		for (int i = 0; i < blockedGrid.length; i++)
			blockedGrid[i] = false;
		
		// Sets the values for the head and foot nodes
		virtualTop = 0;
		virtualBottom = n*n + 1;
		
	}
	public void open(int row,int col) {
		if (row <= 0 || col <= 0 || row > size || col > size) {
			throw new IllegalArgumentException();
		}
		// check whether it is already open
		if (isOpen(row, col)) return;
		// Open the site
		blockedGrid[getIndex(row, col, size)]=true;
		openSites++;
		// Check for adjacent sites (row-1),(row+1),(col-1),(col+1)
		if (row>1) {
			if(isOpen(row-1, col)){
				connectedGrid.union(getIndex(row, col, size), getIndex(row-1, col, size));
			}
		}
		if (row < size) {
			if (isOpen(row+1, col)) {
				connectedGrid.union(getIndex(row, col, size), getIndex(row+1, col, size));
			}
		}
		if (col > 1) {
			if (isOpen(row, col-1)) {
				connectedGrid.union(getIndex(row, col, size), getIndex(row, col-1, size));
			}
		}
		if (col < size) {
			if (isOpen(row, col+1)) {
				connectedGrid.union(getIndex(row, col, size), getIndex(row, col+1, size));
			}
		}
		if (row == 1) {
			connectedGrid.union(getIndex(row, col, size), virtualTop);
		}
		if (row == size) {
			connectedGrid.union(getIndex(row, col, size), virtualBottom);
		}
	}
	public boolean isOpen(int row, int col) {
		if (row <= 0 || col <= 0 || row > size || col > size) {
			throw new IllegalArgumentException();
		}
		return blockedGrid[getIndex(row, col, size)];
	}
	public boolean isFull(int row, int col) {
		if (row <= 0 || col <= 0 || row > size || col > size) {
			throw new IllegalArgumentException();
		}
		// If it is connected to the top
		// Assign a virtual top point and find if it is connected to top virtual point
		return connectedGrid.connected(getIndex(row, col, size), virtualTop);
	}
	public int numberOfOpenSites() {
		return openSites;
	}
	public boolean percolates() {
		return connectedGrid.connected(virtualTop, virtualBottom);
	}
	private int getIndex(int row,int col,int n) {
		if (row <= 0 || col <= 0 || row > n || col > n) {
			throw new IllegalArgumentException();
		}
		return (row-1)*n+col;
	}
	public static void main(String[] args){
		
	}
}
