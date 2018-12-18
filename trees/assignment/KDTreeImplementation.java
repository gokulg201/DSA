//$Id$
package trees.assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KDTreeImplementation {
	public static final boolean HORIZONTAL = true;
	public static final boolean VERTICAL = false;
	private class Node{
		private Point2D point;
		private boolean orientation;
		private Node left;
		private Node right;
		private int size;
		
		public Node(Point2D point, boolean orientation){
			this.point = point;
			this.orientation = orientation;
			this.size = 1;
		}
		public double compare(Point2D pointToCompare){
			if(orientation == VERTICAL){
				return pointToCompare.x() - point.x();
			}else{
				return pointToCompare.y() - point.y();
			}
		}
	}
	private Node root;
	private Point2D closestPoint;
    private double closestDistance;
    
	public boolean isEmpty(){
		return size() == 0;
	}
	public int size(){
		return size(root);
	}
	private int size(Node node){
		if(node == null) return 0;
		return node.size;
	}
	public void insert(Point2D p){
		if(p == null) throw new NullPointerException();
		root = insert(root, p, VERTICAL);
	}
	private Node insert(Node node,final Point2D point,boolean orientation){
		if(node == null) return new Node(point, orientation);
		if (point.compareTo(node.point) == 0) return node;
		double cmp =  node.compare(point);
		if (cmp < 0) node.left = insert(node.left, point , !orientation);
        else if (cmp > 0) node.right = node.right = insert(node.right, point, !orientation);
        else if (!point.equals(node.point)) node.right = insert(node.right, point, !orientation);
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}
	public boolean contains(final Point2D pointToSearch){
		if(pointToSearch == null) throw new NullPointerException();
		return find(root, pointToSearch, VERTICAL) != null;
	}
	private Point2D find(Node node , Point2D pointToSearch,boolean orientation){
		if(node ==  null) return null;
		double cmp = node.compare(pointToSearch);
		if(cmp < 0){
			return find(node.left, pointToSearch,!orientation);
		}else if(cmp > 0){
			return find(node.right, pointToSearch, !orientation);
		}else{
			if(!pointToSearch.equals(node.point)){
				return find(node.right, pointToSearch, !orientation);
			}
			return node.point;
		}
	}
	public void draw() {
        draw(root);
    }

    private void draw(final Node nodeToDraw) {
        if (nodeToDraw == null) return;
        if(nodeToDraw.orientation == VERTICAL){
        	StdDraw.setPenColor(StdDraw.BLACK);
        	StdDraw.setPenRadius(0.01);
        	StdDraw.point(nodeToDraw.point.x(), nodeToDraw.point.y());
        	StdDraw.setPenRadius();
        	StdDraw.textRight(nodeToDraw.point.x(), nodeToDraw.point.y(), "("+nodeToDraw.point.x()+","+nodeToDraw.point.y()+")");
        	StdDraw.setPenColor(StdDraw.RED);
        	StdDraw.line(nodeToDraw.point.x(), 0, nodeToDraw.point.x(), 100);
        }else{
        	StdDraw.setPenColor(StdDraw.BLACK);
        	StdDraw.setPenRadius(0.01);
        	StdDraw.point(nodeToDraw.point.x(), nodeToDraw.point.y());
        	StdDraw.setPenRadius();
        	StdDraw.textRight(nodeToDraw.point.x(), nodeToDraw.point.y(), "("+nodeToDraw.point.x()+","+nodeToDraw.point.y()+")");
        	StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        	StdDraw.line(0, nodeToDraw.point.y(), 100, nodeToDraw.point.y());
        }
        draw(nodeToDraw.left);
        draw(nodeToDraw.right);
    }
    public Iterable<Point2D> range(final RectHV queryRect) {
    	if(queryRect == null) throw new NullPointerException();
    	queryRect.draw();
    	return range(queryRect, root);
    }
    private List<Point2D> range(final RectHV queryRect, final Node node) {
    	if (node == null) return Collections.emptyList();
    	List<Point2D> points = new ArrayList<>();
    	if(queryRect.contains(node.point)){
    		points.add(node.point);
    	}
    	if(node.orientation == VERTICAL){
    		if(queryRect.xmax() < node.point.x()){//left
        		points.addAll(range(queryRect, node.left));
        	}else if(queryRect.xmin() >= node.point.x()){//right
        		points.addAll(range(queryRect, node.right));
        	}else{//intersection
        		points.addAll(range(queryRect, node.left));
        		points.addAll(range(queryRect, node.right));
        	}
    	}
    	else{
    		if(queryRect.ymax() < node.point.y()){//left
    			points.addAll(range(queryRect, node.left));
        	}else if(queryRect.ymin() >= node.point.y()){//right
        		points.addAll(range(queryRect, node.right));
        	}else{//intersection
        		points.addAll(range(queryRect, node.left));
        		points.addAll(range(queryRect, node.right));
        	}
    	}
    	return points;
    }
    public Point2D nearest(Point2D pointToCompare) {
    	if(pointToCompare == null) throw new NullPointerException();
    	if(root == null ) throw new NullPointerException();
    	closestPoint = null;
    	closestDistance = Double.POSITIVE_INFINITY;
        return nearest(root, pointToCompare);
    }
    private Point2D nearest(Node node, Point2D queryPoint) {
    	if(node == null) return null;
    	
        Point2D currentPoint = node.point;
        double currentDistance = queryPoint.distanceSquaredTo(currentPoint);
        
        if(currentDistance < closestDistance){
        	closestDistance = currentDistance;
        	closestPoint = currentPoint;
        }
        
        double cmp = node.compare(queryPoint);
        if(node.orientation == VERTICAL){
        	if(cmp < 0){
        		nearest(node.left, queryPoint);
            	if(closestDistance >= node.point.x() - queryPoint.x()){
            		nearest(node.right, queryPoint);
            	}
            }else{
            	nearest(node.right, queryPoint);
            	if(closestDistance >= queryPoint.x() - node.point.x()){
            		nearest(node.left, queryPoint);
            	}
            }
        }else{
        	if(cmp < 0){
        		nearest(node.left, queryPoint);
        		if(closestDistance >= node.point.y() - queryPoint.y()){
        			nearest(node.right, queryPoint);
        		}
            }else{
            	nearest(node.right, queryPoint);
            	if(closestDistance >= queryPoint.y() - node.point.y()){
            		nearest(node.left, queryPoint);
            	}
            }
        }
        if (currentPoint != null) {
            closestPoint = currentPoint;
        }

        return closestPoint;
    }
}
