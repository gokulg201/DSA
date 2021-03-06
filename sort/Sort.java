//$Id$
package sort;

public abstract class Sort{
	public abstract void sort(Comparable[] a);	
	@SuppressWarnings("unchecked")
	static final boolean less(Comparable v,Comparable w){
		return v.compareTo( w) <0 ;
	}
	static final void exch(Comparable[] a, int i, int j){
		Comparable temp=a[i];
		a[i]=a[j];
		a[i]=temp;
	}
	void show(){
		
	}
	final boolean isSorted(Comparable[] a){
		for(int i=1;i<a.length;i++){
			if(less(a[i],a[i-1])) return false; 
		}
		return true;
	}
}
