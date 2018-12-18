package trees.assignment;
import java.util.Scanner;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

//$Id$

public class KdTreeTest {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int i= 10;
		KdTree kdtree = new KdTree();
		while(i > 0){
			double x = in.nextDouble();
			double y = in.nextDouble();
			 Point2D p = new Point2D(x, y);
	         kdtree.insert(p);
	         i--;
		}
		kdtree.draw();
		RectHV rect = new RectHV(0.18, 0.67, 0.33, 0.87);
		kdtree.range(rect);
	}
}
