//$Id$
package trees.assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

//$Id$

public class KdTree_fail {
	private Node root;
	private char ch = 'A';
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
		root = insert(root, p, new RectHV(0, 0, 1, 1), 0);
	}
	private Node insert(Node node,final Point2D point,final RectHV rect, final int level){
		if(node == null) return new Node(level, point, rect,ch++);
		double cmp = node.compare(point);
		RectHV rectLeft = null;
		RectHV rectRight = null;
		
		if (cmp < 0 && node.left == null) {
            if (level % 2 == 0) {
                rectLeft = new RectHV(node.rect.xmin(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            } else {
                rectLeft = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.point.y());
            }
        } else if (cmp >= 0 && node.right == null) {
            if (level % 2 == 0) {
                rectRight = new RectHV(node.point.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
            } else {
                rectRight = new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.rect.ymax());
            }
        }

        if (cmp < 0) node.left = insert(node.left, point, rectLeft, level + 1);
        else if (cmp > 0) node.right = node.right = insert(node.right, point, rectRight, level + 1);
        else if (!point.equals(node.point)) node.right = insert(node.right, point, rectRight, level + 1);
		node.size = 1 + size(node.left) + size(node.right);
		
		return node;
	}
	public boolean contains(final Point2D pointToSearch){
		if(pointToSearch == null) throw new NullPointerException();
		return find(root, pointToSearch, 0) != null;
	}
	private Point2D find(Node node , Point2D pointToSearch, int level){
		if(node ==  null) return null;
		double cmp = node.compare(pointToSearch);
		if(cmp < 0){
			return find(node.left, pointToSearch, level + 1);
		}else if(cmp > 0){
			return find(node.right, pointToSearch, level + 1);
		}else{
			if(!pointToSearch.equals(node.point)){
				return find(node.right, pointToSearch, level + 1);
			}
			return node.point;
		}
	}
	public void draw() {
        draw(root);
    }

    private void draw(final Node nodeToDraw) {
        if (nodeToDraw == null) return;
        if(nodeToDraw.level % 2 == 0){
        	StdDraw.setPenColor(StdDraw.BLACK);
        	StdDraw.setPenRadius(0.01);
        	StdDraw.point(nodeToDraw.point.x(), nodeToDraw.point.y());
        	StdDraw.setPenRadius();
        	StdDraw.textRight(nodeToDraw.point.x(), nodeToDraw.point.y(), (nodeToDraw.ch)+"("+nodeToDraw.point.x()+","+nodeToDraw.point.y()+")");
        	StdDraw.setPenColor(StdDraw.RED);
        	StdDraw.line(nodeToDraw.point.x(), 0, nodeToDraw.point.x(), 100);
        }else{
        	StdDraw.setPenColor(StdDraw.BLACK);
        	StdDraw.setPenRadius(0.01);
        	StdDraw.point(nodeToDraw.point.x(), nodeToDraw.point.y());
        	StdDraw.setPenRadius();
        	StdDraw.textRight(nodeToDraw.point.x(), nodeToDraw.point.y(), (nodeToDraw.ch)+"("+nodeToDraw.point.x()+","+nodeToDraw.point.y()+")");
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
    	if(node == null) return Collections.emptyList();
    	System.out.println(node.ch);
    	if(node.doesSplittingLineIntersect(queryRect)){
    		List<Point2D> points = new ArrayList<>();
    		if(queryRect.contains(node.point)){
    			points.add(node.point);
    		}
    		points.addAll(range(queryRect, node.left));
    		points.addAll(range(queryRect, node.right));
    		return points;
    	}else{
    		if(node.isRightOf(queryRect)){
    			return range(queryRect, node.left);
    		}else{
    			return range(queryRect, node.right);
    		}
    	}
    }
    // a nearest neighbor in the set to point queryPoint; null if the set is empty
    public Point2D nearest(final Point2D queryPoint) {
    	if(queryPoint == null) throw new NullPointerException();
    	if(root == null) throw new NullPointerException();
    	return nearest(queryPoint, root, root.point, queryPoint.distanceSquaredTo(root.point));
    }
    private Point2D nearest(final Point2D queryPoint, final Node node, final Point2D currentlyClosestPoint,
            final double currentlyClosestDistance) {
    	if(node == null) return null;
    	Point2D closestPoint = currentlyClosestPoint;
        double closestDistance = currentlyClosestDistance;

        Point2D currentPoint = node.point;
        double currentDistance = queryPoint.distanceSquaredTo(currentPoint);
        
        if(currentDistance < closestDistance){
        	closestDistance = currentDistance;
        	closestPoint = currentPoint;
        }
        double cmp = node.compare(queryPoint);
        if(cmp < 0){
        	currentPoint = nearest(queryPoint, node.left, currentlyClosestPoint, currentlyClosestDistance);
        }else{
        	currentPoint = nearest(queryPoint, node.right, currentlyClosestPoint, currentlyClosestDistance);
        }
        if(currentPoint != null){
        	if(currentPoint != closestPoint){
        		closestDistance = currentPoint.distanceSquaredTo(queryPoint);
        		closestPoint = currentPoint;
        	}
        }
        
        double nodeRectDistance = -1;
        if (cmp < 0 && node.right != null) {
            nodeRectDistance = node.right.rect.distanceSquaredTo(queryPoint);
        } else if (cmp >= 0 && node.left != null) {
            nodeRectDistance = node.left.rect.distanceSquaredTo(queryPoint);
        }

        if (nodeRectDistance != -1 && nodeRectDistance < closestDistance) {
            if (cmp < 0) {
                currentPoint = nearest(queryPoint, node.right, closestPoint, closestDistance);
            } else {
                currentPoint = nearest(queryPoint, node.left, closestPoint, closestDistance);
            }
        }

        if (currentPoint != null) {
            closestPoint = currentPoint;
        }

        return closestPoint;

    }
	private class Node{
		private final Point2D point;
        private final RectHV rect;
		private final int level;
		private final char ch;
		private int size;
		Node left, right;
		
		public Node(int level, Point2D point,RectHV rect,char ch){
			this.level = level;
			this.point =  point;
			this.rect = rect;
			this.size = 1;
			this.ch =ch;
		}
		public double compare(Point2D pointToCompare){
			if(level % 2 ==0){
				return pointToCompare.x() - point.x();
			}else{
				return pointToCompare.y() - point.y();
			}
		}
		public boolean doesSplittingLineIntersect(final RectHV rect){
			if (level % 2 == 0) {
                return rect.xmin() <= point.x() && point.x() <= rect.xmax();
            } else {
                return rect.ymin() <= point.y() && point.y() <= rect.ymax();
            }
		}
		public boolean isRightOf(final RectHV rect){
			if (level % 2 == 0) {
				return rect.xmin() < point.x() && rect.xmax() < point.x();
			}else{
				return rect.ymin() < point.y() && rect.ymax() < point.y();
			}
		}
	}
}

