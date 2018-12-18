//$Id$
package sort;

public class InsertionSort extends Sort{

	@Override
	public void sort(Comparable[] a) {
		int N=a.length;
		for(int i=1;i<N;i++){
			for(int j=i;j>0;j--){
				if(less(a[j], a[j-1])){
					exch(a, j, j-1);
				}else{
					break;
				}
			}
		}
	}

}
