//$Id$
package sort;

public class ShellSort extends Sort{
	@Override
	public void sort(Comparable[] a) {
		int N=a.length;
		int h=1;
		while (h < N/3) h= 3*h + 1;
		while (h > 0){
			//h-sort the array
			for(int i = h;i < N;i++){
				for(int j=i; j>0 ;j--){
					if(less(a[j],a[j-h])) exch(a, j, j-h);
					else break;
				}
			}
			h = h/3;
		}
	}
}
