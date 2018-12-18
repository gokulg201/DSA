//$Id$
package trees;

import java.util.Random;

public class Treap {
	public static final int MAX_PRIORITY = Integer.MAX_VALUE ;
	static Random rand = new Random();
	private static class Node{
		int key,priority,size;
		Node left,right;
		Node(int key){
			this.key = key;
			this.priority = rand.nextInt();
			this.left = null;
			this.right = null;
			this.size = 1;
		}
		public String toString() {
        	return String.valueOf(key);
        }
	}
	private static class DNode {
        Node left;
        Node right;
        
        DNode() {
            left = null;
            right = null;
        }
        
        public String toString() {
            return "L:" + left + " R:" + right;
        }
    }
	private Node root;
	private Node findParent(Node x,int key){
		if(x == null) return null;
		int cmp = x.key - key;
		if(x.left.key == key || x.right.key == key)
			return x;
		if(cmp == 0){
			return null;
		}else if(cmp < 0){
			return findParent(x.left, key);
		}else{
			return findParent(x.right, key);
		}
	}
	private Node rotateRight(Node x){
		Node y = x.left;
		Node T2 = y.right;
		x.left = T2;
		y.right = x;
		return y;
	}
	private Node rotateLeft(Node x){
		Node y = x.right;
		Node T2 = y.left;
		x.right = T2;
		y.left = x;
		return y;
	}
	public Node search(int key){
		return search(root, key);
	}
	private Node search(Node x,int key){
		if(x == null) return null;
		int cmp = x.key - key;
		if(cmp == 0){
			return x;
		}else if(cmp < 0){
			return search(x.left, key);
		}else{
			return search(x.right, key);
		}
	}
	public Node insert(int key){
		return insert(root, key);
	}
	private Node insert(Node node,int key){
		if(node == null) return new Node(key);
		if(key <= node.key){
			node.left = insert(node.left, key);
			if(node.left.priority > node.priority)
				node = rotateRight(node);
		}else{
			node.right = insert(node.right, key);
			if(node.right.priority > node.priority)
				node = rotateLeft(node);
		}
		updateSize(node);
		return node;
	}
	public Node insertWithMerge(int key){
		Node node = new Node(key);
		root = merge(root, node);
		return root;
	}
	public Node insertSplitndMerge(int key){
		Node node = new Node(key);
		DNode pair = split(root, key);
		root = merge(pair.left, merge(node, pair.right));
		return root;
	}
	public Node delete(int key){
		return delete(root, key);
	}
	private Node delete(Node node, int key){
		if(node == null) return null;
		if(key < node.key){
			node.left = delete(node.left, key);
		}else if(key > node.key){
			node.right = delete(node.right, key);
		}else{
			if(node.left == null){
				Node temp = node.right;
				node = temp;
			}else if(node.right == null){
				Node temp = node.left;
				node = temp;
			}else {
				if(node.left.priority < node.right.priority){
					node = rotateLeft(node);
					node.left = delete(node.left, key);
				}else{
					node = rotateRight(node);
					node.right = delete(node.right, key);
				}
			}
		}
		return node;
	}
	public Node deleteWithMerge(Node node,int key){
		if(node == null) return null;
		if(key < node.key){
			node.left = delete(node.left, key);
		}else if(key > node.key){
			node.right = delete(node.right, key);
		}else{
			node = merge(node.left, node.right);
		}
		return node;
	}
	public int size(Node node){
		if(node == null) return 0;
		return node.size;
	}
	public void updateSize(Node node){
		if(node != null)
			node.size = 1 + size(node.left) + size(node.right);
	}
	public DNode split(Node node,int key){
		DNode pair = new DNode();
		if(node == null) {
			return pair;
		}
		if(key < node.key){
			pair = split(node.left, key);
			node.left = pair.right;
			updateSize(node);
			pair.right = node;
		}else{
			pair = split(node.right, key);
			node.right = pair.left;
			updateSize(node);
			pair.left = node;
		}
		return pair;
	}
	public Node merge(Node leftTreap, Node rightTreap){
		if(leftTreap == null) return rightTreap;
		if(rightTreap == null) return leftTreap;
		if(leftTreap.priority > rightTreap.priority){
			leftTreap.right = merge(leftTreap.right, rightTreap);
			return leftTreap;
		}else{
			rightTreap.left = merge(leftTreap, rightTreap.left);
			return rightTreap;
		}
	}
    public void inorder() {
        inorder(root);
        System.out.println();
    }
    
    private void inorder(Node n) {
        if (n == null)
            return;
        inorder(n.left);
        System.out.print(n + " ");
        inorder(n.right);
    }
	public static void main(String[] args){
		Treap t = new Treap();
		t.root = t.insertSplitndMerge(1);
		t.root = t.insertSplitndMerge(2);
		t.root = t.insertSplitndMerge(3);
		t.root = t.insertSplitndMerge(4);
		t.root = t.insertSplitndMerge(5);
		t.root = t.insertSplitndMerge(6);
		t.root = t.insertSplitndMerge(7);
		t.root = t.insertSplitndMerge(8);
		t.root = t.delete(5);
		DNode pair = t.split(t.root, 4);
		System.out.println(pair);
		t.merge(pair.left, pair.right);
	}
}
