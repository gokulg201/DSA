package trees.assignment;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;


public class PointSET {
	private final SET<Point2D> points = new SET<>();
	public PointSET(){
		
	}
	public boolean isEmpty(){
		return points.isEmpty();
	}
	public int size(){
		return points.size();
	}
	public void insert(Point2D p){
		if(p == null) throw new NullPointerException();
		points.add(p);
	}
	public boolean contains(Point2D p){
		if(p == null) throw new NullPointerException();
		return points.contains(p);
	}
	public void draw(){
		for (Point2D point : points) {
            StdDraw.point(point.x(), point.y());
        }
	}
	public Iterable<Point2D> range(RectHV rect){
		if(rect == null) throw new NullPointerException();
		List<Point2D> solution = new ArrayList<Point2D>();
		for(Point2D point:points){
			if(rect.contains(point))
				solution.add(point);
		}
		return solution;
	}
	public Point2D nearest(Point2D p){
		if(p == null) throw new NullPointerException();
		Point2D nearestPoint =  null;
		for(Point2D point:points){
			if(nearestPoint == null || point.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p) )
				nearestPoint = point;
		}
		return nearestPoint;
	}
	 
}
