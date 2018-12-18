//$Id$
package containerCollections;

import java.util.Iterator;

public class LinkedListStack<Item> implements Iterable<Item>{
	private class Node{
		Item item;
		Node next;
	}
	private Node first;
	private int N;
	
	public boolean isEmpty(){
		return first==null;
	}
	public int size () {
		return N;
	}
	public void push(Item item){
		Node oldFirst=first;
		first=new Node();
		first.item=item;
		first.next=oldFirst;
		N++;
	}
	public Item pop(){
		Item item=first.item;
		first=first.next;
		N--;
		return item;
	}
	public Item peek(){
		return first.item;
	}
	@Override
	public Iterator<Item> iterator() {
		return new StackIterator();
	}
	private class StackIterator implements Iterator<Item>{
		private Node current=first;
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
