//$Id$
package heaps;

public class MinHeap<Key extends Comparable<Key>> {
	private Key[] pq;
	private int N;
	
	public MinHeap(int capacity){
		pq = (Key[]) new Comparable[capacity+1];
	}
	public boolean isEmpty(){
		return N == 0;
	}
	public void insert(Key key){
		pq[N++]=key;
		swim(N);
	}
	public Key delMax(){
		Key max=pq[1];
		exch(1,N--);
		pq[N+1]=null;
		sink(1);
		return max;
	}
	public void swim(int k){
		while(k > 1 && less(k,k/2))
			exch(k, k/2);
		k=k/2;
	}
	public void sink(int k){
		while(k>=N){
			int j=2*k;
			if(j < N && less(j,j+1)) j++;
			if(!less(k,j)) break;
			exch(k,j);
			k = j;
		}
	}
	private boolean less(int i,int j){
		System.out.println(""+i);
		System.out.println(j);
		System.out.println(pq);
		return pq[i].compareTo(pq[j]) < 0; 
	}
	private void exch(int i, int j)
	{ Key t = pq[i]; pq[i] = pq[j]; pq[j] = t; }
}
