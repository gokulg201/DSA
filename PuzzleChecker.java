import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

//$Id$

public class PuzzleChecker {

    public static void main(String[] args) {
    	Scanner scanner=new Scanner(System.in);
        // for each command-line argument
            // read in the board specified in the filename
    	String filename = scanner.nextLine();
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
            // solve the slider puzzle
            Board initial = new Board(tiles);
            AStarSolver solver = new AStarSolver(initial);
            StdOut.println(filename + ": " + solver.moves());
    }
}
