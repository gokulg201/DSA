//$Id$
package trees;

import java.util.PriorityQueue;
import java.util.Queue;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SymbolTableBSTImpl<Key extends Comparable<Key>,Value> {
	Node root;
	class Node<Key extends Comparable<Key>,Value>{
		Node<Key,Value> left;
		Node<Key,Value> right;
		Key key;
		Value value;
		int count;
		public Node(Key key, Value value){
			this.left = null;
			this.right = null;
			this.key = key;
			this.value = value;
		}
	}
	
	public void put(Key key, Value value){
		root = put(root, key, value);
	}
	private Node put(Node x,Key key, Value value){
		if(x == null) return new Node<Key, Value>(key, value);
		int cmp = x.key.compareTo(key);
		if(cmp < 0) 
			x.left = put(x.left, key, value);
		else if (cmp > 0)
			x.right = put(x.right, key, value);
		else
			x.value = value;
		x.count = 1 + size(x.left) + size(x.right);
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
	public Node delete(Key key){
		return delete(root,key);
	}
	public boolean contains(Key key){
		return get(key) != null;
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
	public Key select(int k){
		return (Key) select(root, k).key;
	}
	private Node select(Node node, int k){
		if(node == null) return null;
		int t = size(node.left);
		if(t > k) return select(node.left, k);
		else if(t < k) return select(node.right,k-t-1);
		else return node;
	}
	public int rank(Key key){
		return rank(key, root);
	}
	private int rank(Key key, Node node){
		if (node == null) return 0;
		int cmp = key.compareTo((Key) node.key);
		if(cmp < 0) return rank(key,node.left);
		else if(cmp > 0) return 1 + size(node.left) + rank(key,node.right);
		else return size(node.left);
	}
	public Iterable<Key> keys(){
		Queue<Key> keys = new PriorityQueue<Key>();
		inOrder(root, keys);
		return keys;	
	}
	private void inOrder(Node node, Queue<Key> q){
		if(node == null) return;
		inOrder(node.left, q);
		q.add((Key) node.key);
		inOrder(node.right, q);
	}
	public Key min(){
		return (Key) min(root).key;
	}
	private Node min(Node node){
		if(node.left == null) return node;
		return min(node.left);
	}
	public Key max(){
		return (Key) max(root).key;
	}
	private Node max(Node node){
		if(node.right == null) return node;
		return max(node.right);
	}
	public Key floor(Key key){
		return (Key) floor(root, key).key;
	}
	private Node floor(Node node, Key key){
		if(node == null) return null;
		int cmp = key.compareTo((Key) node.key);
		if (cmp == 0) return node;
		else if (cmp < 0) return floor(node.left, key);
		else {
			Node temp = floor(node.right, key);
			if(temp != null) return temp;
			else return node;
		}
	}
	public Key ceil(Key key){
		return (Key) ceil(root, key).key;
	}
	private Node ceil(Node node, Key key){
		if(node == null) return null;
		int cmp = key.compareTo((Key) node.key);
		if(cmp == 0) return node;
		else if(cmp > 0) return ceil(node.right, key);
		else {
			Node temp = ceil(node.left,key);
			if(temp != null) return temp;
			else return node;
		}
	}
	public void deleteMin(){
		root = deleteMin(root);
	}
	public void deleteMax(){
		root = deleteMax(root);
	}
	private Node delete(Node node, Key key){
		if(node == null) return null;
		int cmp = key.compareTo((Key) node.key);
		if(cmp < 0) node.left = delete(node.left, key);
		else if (cmp > 0) node.right = delete(node.right,key);
		else{
			if(node.left == null) return node.right;
			else if(node.right == null) return node.left;
			
			Node t = node;
			node =  min(node.right);
			node.right = deleteMin(node.right);
			node.left = t.left;
		}
		node.count = 1 + size(node.left) + size(node.right);
		return node;
	}
	private Node deleteMin(Node node){
		if(node.left == null) return node.right;
		node.left = deleteMin(node.left);
		node.count = 1 + size(node.left) + size(node.right);
		return node;
	}
	private Node deleteMax(Node node){
		if(node.right == null) return node.left;
		node.right = deleteMax(node.right);
		node.count = 1 + size(node.left) + size(node.right);
		return node;
	}
}
