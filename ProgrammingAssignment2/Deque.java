//$Id$
package ProgrammingAssignment2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{
   private class Node{
	   Item item;
	   Node next;
	   Node previous;
   }
   private int size;
   private Node first;
   private Node last;
   public Deque() {
	   size=0;
	   first=null;
	   last=null;
   }
   public boolean isEmpty() {
	   return size==0;
   }
   public int size() {
	   return size;
   }
   public void addFirst(Item item) {
	   if (item == null) {
           throw new NullPointerException();
       }
	   
	   Node oldFirst=first;
	   first=new Node();
	   first.item=item;
	   first.next=oldFirst;
	   if(oldFirst!=null) oldFirst.previous=first;
	   if(isEmpty()) last=first;
	   size++;
   }
   public void addLast(Item item) {
	   if (item == null) {
           throw new NullPointerException();
       }
	   Node oldLast=last;
	   last=new Node();
	   last.item=item;
	   if(isEmpty()) first=last;
	   else { oldLast.next=last; last.previous=oldLast; } 
	   size++;
   }
   public Item removeFirst() {
	   // remove and return the item from the front
	   if(isEmpty()) throw new NoSuchElementException();
	   Item item=first.item;
	   first=first.next;
	   if (first == null) {
           last = null;
       } else {
           first.previous = null;
       }
       size--;
	   return item;
   }
   public Item removeLast() {
	   // remove and return the item from the end
	   if(isEmpty()) throw new NoSuchElementException();
	   Item item=last.item;
	   last=last.previous;
	   if (last == null) first=last;
	   else last.next=null;
	   return item;
	   
   }
   public Iterator<Item> iterator() {
	   // return an iterator over items in order from front to end
	   return new DequeueIterator();
   }
   private class DequeueIterator implements Iterator<Item>{
		private Node current;
		public boolean hasNext(){
			return current!=null;
		}
		public Item next(){
			if (!hasNext()) {
                throw new NoSuchElementException();
            }
			Item item=current.item;
			current=current.next;
			return item;
		}
		public void remove(){
			throw new UnsupportedOperationException();
		};
	}
   public static void main(String[] args) {
	   
   }
}
