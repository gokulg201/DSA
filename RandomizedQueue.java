//$Id$


import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] items;
	private int size;
	   public RandomizedQueue() {
			 // construct an empty randomized queue
		   size=1;
		   items=(Item[]) new Object[size];
	   }
	   public boolean isEmpty()  {
		   // is the randomized queue empty?
		   return size==0;
	   }
	   public int size() {
		   // return the number of items on the randomized queue
		   return size;
	   }
	   public void enqueue(Item item) {
		   // add the item
		   if (item == null) {
	            throw new NullPointerException();
	        }
		   if(size==items.length) resize (2*items.length) ;
			items[size++]=item;
	   }
	   public Item dequeue() {
		   // remove and return a random item
		   if (size == 0) {
	            throw new NoSuchElementException();
	       }
		   int indexOfItemToReturn = StdRandom.uniform(size);
		   Item returnValue = items[indexOfItemToReturn];
	       size--;
	       items[indexOfItemToReturn] = items[size];
	       items[size] = null;
	       if(size >0 && size==items.length/4) resize(items.length/2);
	       return returnValue;
	   }
	   public Item sample() {
		   // return a random item (but do not remove it)
		   if (size == 0) {
	            throw new NoSuchElementException();
	        }

	        int indexOfItemToReturn = StdRandom.uniform(size);
	        return items[indexOfItemToReturn];
	   }
	   public Iterator<Item> iterator() {
		   // return an independent iterator over items in random order
		   return new RandomIterator();
	   }
	   private void resize(int max){
		   Item[] temp=(Item[])new Object[max];
			for(int i=0;i<size;i++)
				temp[i]=items[i];
			items=temp;
	   }
	   private class RandomIterator implements Iterator<Item> {
		   private Item[] iteratorItems;
	       private int index;
	       public RandomIterator() {
	            iteratorItems = copyRandomQueueItems();
	            StdRandom.shuffle(iteratorItems);
	       }

	        private Item[] copyRandomQueueItems() {
	            Item[] copiedItems = (Item[]) new Object[size];
	            for (int i = 0; i < size; i++) {
	                copiedItems[i] = items[i];
	            }
	            return copiedItems;
	        }
	        @Override
	        public boolean hasNext() {
	            return index < iteratorItems.length;
	        }

	        @Override
	        public Item next() {
	            if (hasNext()) {
	                return iteratorItems[index++];
	            } else {
	                throw new NoSuchElementException();
	            }
	        }
	        @Override
	        public void remove() {
	            throw new UnsupportedOperationException();
	        }
	   }

	   public static void main(String[] args) {
		   // unit testing (optional)
	   }
}
