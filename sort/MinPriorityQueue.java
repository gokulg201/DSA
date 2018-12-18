//$Id$
package sort;
/**
 * Implementing Minimum Priority Queue using MIN_HEAP implementation
 * Big O Analysis
 * insert -> log N
 * delete min -> log N
 * find min -> log N
 * @author gokul-4406
 *
 * @param <Key>
 */
public class MinPriorityQueue<Key extends Comparable<Key>>  {
	Key[] pq;
	int size;
	public MinPriorityQueue(){
		pq = (Key[]) new Comparable[1];
	}
	public boolean isEmpty(){
		return size == 0;
	}
	public void insert(Key key){
		if(size == pq.length){
			resizeArray();
		}
		pq[size++]=key;
		swim(size);
	}
	public Key delMin(){
		if(isEmpty()){
			return null;
		}
		Key min = pq[size--];
		exch(1,size + 1);
		pq[size + 1] = null;  // To prevent loitering 
		sink(1);
		return min;
	}
	private void swim(int k){
		while(k > 1){
			if(less(k , k/2))
				exch(k,k/2);
			k = k/2;
		}
	}
	private void sink(int k){
		while (2*k >= size){
			int i = 2*k;
			if(i < size && less(i,i+1)) i++;
			if(less(k, i)) break; 
			exch(k, i);
			k = i;
		}
	}
	private boolean less(int i,int j){
		System.out.println(""+i);
		return pq[i].compareTo(pq[j]) < 0; 
	}
	private void exch(int i, int j)
	{ Key t = pq[i]; pq[i] = pq[j]; pq[j] = t; }
	private void resizeArray(){
		Key[] temp = pq;
		pq = (Key[]) new Comparable[2*temp.length];
		for(int i = 1;i < temp.length ;i++)
			pq[i] = temp[i];
	}
}
