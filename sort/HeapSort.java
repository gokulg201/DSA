//$Id$
package sort;

/**
 * HEAP implementation of Sort --> HeapSort
 * Big O Analysis 
 * Heap sort O(2 log N)
 * In-place sorting algorithm with log N complexity
 * @author gokul-4406
 *
 * @param <T>
 */
public class HeapSort<T extends Comparable> extends Sort{

	@Override
	public void sort(Comparable[] a) {
		int N = a.length;
		a = heapOrder(a);
		while(N > 1){
			exch(a,1,N--);
			sink(a,1,N);
		}
	}
	public static void sink(Comparable[] a, int k, int N){
		while(2*k>=N){
			int j=2*k;
			if(j < N && less(a,j,j+1)) j++;
			if(!less(a,k,j)) break;  // To break the sink operation
			exch(a,k,j);
			k = j;
		}
	}
	public static boolean less(Comparable[] a, int i, int j){
		return a[i].compareTo(a[j]) < 0; 
	}
	public static Comparable[] heapOrder(Comparable[] a){
		int N = a.length;
		for (int k = N/2 ; k > 0;k--){
			sink(a, k, N);
		}
		return a;
	}
}
