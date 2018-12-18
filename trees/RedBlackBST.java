//$Id$
package trees;

public class RedBlackBST<Key extends Comparable<Key>,Value> {
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	Node root;
	class Node<Key extends Comparable<Key>,Value>{
		Node<Key,Value> left;
		Node<Key,Value> right;
		Key key;
		Value value;
		int count;
		boolean color;
		public Node(Key key, Value value,boolean color){
			this.left = null;
			this.right = null;
			this.key = key;
			this.value = value;
			this.color = color;
		}
	}
	public void put(Key key, Value value){
		root = put(root, key, value);
	}
	private Node put(Node x,Key key, Value value){
		if(x == null) return new Node<Key, Value>(key, value,RED);
		int cmp = x.key.compareTo(key);
		if(cmp < 0) 
			x.left = put(x.left, key, value);
		else if (cmp > 0)
			x.right = put(x.right, key, value);
		else
			x.value = value;
		x.count = 1 + size(x.left) + size(x.right);
		if(isRed(x.right) && !isRed(x.left)) rotateLeft(x);
		if(isRed(x.left) && isRed(x.left.left)) rotateRight(x);
		if(isRed(x.left) && isRed(x.right)) flipColors(x);
		return x;
	}
	public Value get(Key key){
		Node x = root;
		while (x != null){
			int cmp = x.key.compareTo(key);
			if(cmp < 0) 
				x = x.left;
			else if (cmp > 0)
				x = x.right;
			else
				return (Value) x.value;
		}
		return null;		
	}
	public boolean isEmpty(){
		return size() == 0;
	}
	public int size(){
		return size(root);
	}
	private int size(Node node){
		if(node == null) return 0;
		return node.count;
	}
	private boolean isRed(Node node){
		if(node == null) return false;
		return node.color == RED;
	}
	private Node rotateLeft(Node h){
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		return x;
	}
	private Node rotateRight(Node h){
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		return x;
	}
	private void flipColors(Node h){
		h.color =RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}
}
