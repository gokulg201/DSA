//$Id$
package sort.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestClass {
	public static void main(String[] args){
		File file = new File("/Users/gokul-4406/Downloads/collinear/horizontal75.txt");
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int no = in.nextInt();
		Point[] points = new Point[no];
		int j = 0;
		for(int i = 0;i < no;i++){
			int x = in.nextInt();
			int y = in.nextInt();
			Point point = new Point(x, y);
			points[j++] = point;
		}
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		LineSegment[] segments = collinear.segments();
		for(int i = 0;i < segments.length;i++){
			System.out.println(segments[i]);
		}
	}
}
