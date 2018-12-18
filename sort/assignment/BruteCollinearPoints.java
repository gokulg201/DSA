//$Id$
package sort.assignment;

import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {
	private LineSegment[] segments;
	public BruteCollinearPoints(final Point[] points){
		if(points == null) throw new IllegalArgumentException();
		if(checkForNullPoints(points)) throw new IllegalArgumentException();
		checkForDuplicates(points);
		Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        ArrayList<LineSegment> segs = new ArrayList<>();
		for(int p = 0;p < points.length - 3;p++){
			for(int q = p + 1;q < points.length - 2;q++){
				for(int r = q + 1;r < points.length -1;r++){
					for(int s = r + 1;s < points.length;s++){
						if(pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[r]) && pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[s])){
							segs.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
						}
					}
				}
			}
		}	
		segments = segs.toArray(new LineSegment[segs.size()]);
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
}
