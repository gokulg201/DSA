//$Id$
package sort;
//N log N
public class MergeSort extends Sort{
	private Comparable[] aux;
	@Override
	public void sort(Comparable[] a) {
		aux=new Comparable[a.length];
		sort(a, 0, a.length);
	}
	private void sort(Comparable[] a,int lo, int hi){
		int mid = lo + (hi-lo)/2;
		sort(a, lo, mid);
		sort(a, mid+1, hi);
		if(less(a[mid], a[mid+1])) return; 
		merge(a,lo, mid, hi);
	}
	private void merge(Comparable[] a,int lo, int mid, int hi){
		if(hi<=lo) return ;
		int i=lo; int j=mid+1;
		for(int k=lo;k<=hi;k++){
			aux[k]=a[k];
		}
		for(int k=lo;k<=hi;k++){
			if(j > hi) a[k] = aux[i++];
			else if(i > mid) a[k] = aux[j++];
			else if(less(aux[i],aux[j])) a[k] = a[i++];
			else a[k] = a[j++];
		}
	}
	public void sort_bottomUp(Comparable[] a){
		aux=new Comparable[a.length];
		int N=a.length;
		for(int sz=1;sz<N; sz=sz+sz){
			for(int lo=0 ; lo < (N-sz); lo+= sz+sz ){
				merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
			}
		}
	}
}
