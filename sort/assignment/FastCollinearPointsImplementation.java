package sort.assignment;
//$Id$


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import edu.princeton.cs.algs4.MergeX;
/**
 * This was implemeted by me
 * @author gokul-4406
 *
 */
public class FastCollinearPointsImplementation {
	private LineSegment[] segments;
	public FastCollinearPointsImplementation(final Point[] points){
		if(points == null) throw new IllegalArgumentException();
		if(checkForNullPoints(points)) throw new IllegalArgumentException();
		checkForDuplicates(points);
		
		Point[] pointsCopy_main = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy_main);
        ArrayList<LineSegment> segs = new ArrayList<>();
        
        for(int p = 0 ;p < pointsCopy_main.length;p++){
        	Point origin = pointsCopy_main[p];
        	ArrayList<Point> pts = new ArrayList<>();
        	for(int j = p;j < pointsCopy_main.length;j++){
        		pts.add(pointsCopy_main[j]);
        	}
        	Point[] pointsCopy = pts.toArray(new Point[pts.size()]);
        	MergeX.sort(pointsCopy, origin.slopeOrder());
        	int min = 1;
        	int max = min;
        	while (min < pointsCopy.length){
        		while(max< pointsCopy.length && origin.slopeTo(pointsCopy[min]) == origin.slopeTo(pointsCopy[max])){
        			max++;
        		}
        		if(max - min >=3){
        			Point pMin = pointsCopy[min].compareTo(origin) < 0 ? pointsCopy[min] : origin;
        			Point pMax = pointsCopy[max].compareTo(origin) > 0 ? pointsCopy[max] : origin;
        			if(pMin == origin){
        				segs.add(new LineSegment(pMin, pMax));
        			}
        		}
        		min = max;
        	}
        }
        segments = segs.toArray(new LineSegment[segs.size()]);;
	}
	private boolean checkForNullPoints(Point[] points){
		for(Point point:points){
			if(point ==  null) return true;
		}
		return false;
	}
	private void checkForDuplicates(Point[] points){
		for(int i = 0;i < points.length - 1;i++){
			for(int j = i + 1; j< points.length;j++){
				if(points[i].compareTo(points[j]) == 0)
					throw new IllegalArgumentException();
			}
		}
	}
	public int numberOfSegments(){
		return segments.length;
	}
	public LineSegment[] segments(){
		return segments.clone();
	}
	public static void main(String[] args){
		File file = new File("/Users/gokul-4406/Downloads/collinear/input8.txt");
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
		FastCollinearPointsImplementation collinear = new FastCollinearPointsImplementation(points);
		LineSegment[] segments = collinear.segments();
		for(int i = 0;i < segments.length;i++){
			System.out.println(segments[i]);
		}
	}
}
