//$Id$
package containerCollections;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ResizableArrayStack<Item> implements Iterable<Item>{
	private int N;
	private Item[] a;
	public ResizableArrayStack(){
		N=1;
		a=(Item[]) new Object[N];
	}
	public boolean isEmpty() { return N==0; }
	public int size() { return N; }
	public void push(Item item){
		if(N==a.length) resize (2*a.length) ;
		a[N++]=item;
	}
	public Item pop() throws Exception{
		if (N==0) throw new Exception();
		Item item=a[--N];
		a[N]=null;
		if(N >0 && N==a.length/4) resize(a.length/2);
		return item;
	}
	public Item peek() throws Exception{
		if (N==0) throw new Exception();
		return a[N];
		
	}
	private void resize(int max){
		Item[] temp=(Item[])new Object[max];
		for(int i=0;i<N;i++)
			temp[i]=a[i];
		a=temp;
	}
	public Iterator<Item> iterator(){
		return new ReverseArrayIterator();
	}
	private class ReverseArrayIterator implements Iterator<Item>{
		private int i=N;
		public boolean hasNext(){
			return i > 0;
		}
		public Item next(){
			return a[--i];
		}
		public void remove(){};
		
	}
}
