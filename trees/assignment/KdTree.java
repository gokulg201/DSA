package trees.assignment;
import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    private class Node {
        private Point2D point;
        private boolean divideBy;
        private final char ch;
        private int size;
        private Node left;
        private Node right;

        public Node(Point2D point, boolean divideBy,char ch) {
            this.point = point;
            this.divideBy = divideBy;
            this.ch = ch;
            size = 1;
            left = null;
            right = null;
        }
    }

    private Node root;
    private Point2D nearestPoint;
    private double minDistance;
    private char ch = 'A';

    public KdTree() {
        // construct an empty set of points
        root = null;
    }

    public boolean isEmpty() {
        // is the set empty?
        return root == null;
    }

    public int size() {
        // number of points in the set
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException();
        root = insert(root, p, VERTICAL);
    }

    private Node insert(Node x, Point2D p, boolean divideBy) {
        if (x == null) return new Node(p, divideBy,ch++);
        if (p.compareTo(x.point) == 0) return x;

        double cmp;
        if (divideBy == VERTICAL)
            cmp = p.x() - x.point.x();
        else
            cmp = p.y() - x.point.y();

        if (cmp < 0)
            x.left = insert(x.left, p, !divideBy);
        else
            x.right = insert(x.right, p, !divideBy);

        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) throw new NullPointerException();
        Node x = contains(root, p);
        return x != null;
    }

    private Node contains(Node x, Point2D p) {
        if (x == null) return null;

        if (p.compareTo(x.point) == 0) return x;

        double cmp;
        if (x.divideBy == VERTICAL)
            cmp = p.x() - x.point.x();
        else
            cmp = p.y() - x.point.y();

        if (cmp < 0)
            return contains(x.left, p);
        else
            return contains(x.right, p);
    }

    public void draw() {
        // draw all points to standard draw
        draw(root);
    }

    private void draw(Node nodeToDraw) {
    	 if (nodeToDraw == null) return;
         if(nodeToDraw.divideBy == VERTICAL){
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

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException();

        ArrayList<Point2D> points = new ArrayList<>();
        range(root, rect, points);
        return points;
    }

    private void range(Node x, RectHV rect, ArrayList<Point2D> points) {
        if (x == null) return;
    	System.out.println(x.ch);
        if (rect.contains(x.point)){
        	points.add(x.point);
        }
        if (x.divideBy == VERTICAL) {
            if (rect.xmax() < x.point.x())
                range(x.left, rect, points);
            else if (rect.xmin() >= x.point.x())
                range(x.right, rect, points);
            else {
                range(x.left, rect, points);
                range(x.right, rect, points);
            }
        } else {
            if (rect.ymax() < x.point.y())
                range(x.left, rect, points);
            else if (rect.ymin() >= x.point.y())
                range(x.right, rect, points);
            else {
                range(x.left, rect, points);
                range(x.right, rect, points);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new NullPointerException();

        nearestPoint = null;
        minDistance = Double.POSITIVE_INFINITY;
        nearest(root, p);
        return nearestPoint;
    }

    private void nearest(Node x, Point2D p) {
        if (x == null) return;

        double distance = p.distanceTo(x.point);
        if (distance < minDistance) {
            minDistance = distance;
            nearestPoint = x.point;
        }

        if (x.divideBy == VERTICAL) {
            if (p.x() < x.point.x()) {
                nearest(x.left, p);
                if (minDistance >= x.point.x() - p.x())
                    nearest(x.right, p);
            } else {
                nearest(x.right, p);
                if (minDistance >= p.x() - x.point.x())
                    nearest(x.left, p);
            }
        } else {
            if (p.y() < x.point.y()) {
                nearest(x.left, p);
                if (minDistance >= x.point.y() - p.y())
                    nearest(x.right, p);
            } else {
                nearest(x.right, p);
                if (minDistance >= p.y() - x.point.y())
                    nearest(x.left, p);
            }
        }
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}