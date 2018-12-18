//$Id$
package containerCollections;

import java.util.Iterator;


public class LinkedListQueue<Item> implements Iterable{
	private class Node{
		Item item;
		Node next;
	}
	private Node first;
	private Node last;
	private int N;
	
	public boolean isEmpty(){
		return first==null;
	}
	public int size(){
		return N;
	}
	public void enqueue(Item item){
		Node oldLast=last;
		last=new Node();
		last.item=item;
		last.next=null;
		if(isEmpty()) first=last;
		else oldLast.next=last; 
		N++;
	}
	public Item dequeue(){
		Item item=first.item;
		first=first.next;
		if(isEmpty()) last=null;
		N--;
		return item;
	}
	@Override
	public Iterator<Item> iterator() {
		return new QueueIterator();
	}
	private class QueueIterator implements Iterator<Item>{
		private Node current;
		public boolean hasNext(){
			return current!=null;
		}
		public Item next(){
			Item item=current.item;
			current=current.next;
			return item;
		}
		public void remove(){};
	}
}
