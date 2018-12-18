//$Id$
package trees;
/**
 * Binary Search Implementation of Symbol Table using ordered array
 * @author gokul-4406
 *
 * @param <Key>
 * @param <Value>
 */
public class SymbolTableArrayImpl<Key extends Comparable<Key>,Value> {
	private Key[] keys;
	private Value[] values;
	int size;
	public void put(Key key, Value value){
		//Insertion Sort
		int i =  rank(key);
		if(i < size && key.compareTo(keys[i]) == 0)   {
			values[i] = value; return;
		}
		for (int j = size ; j >i; j++){
			keys[j] = keys[j+1];
			values[j] = values[j+1];
		}
		keys[i] = key;
		values[i] =value;
		size++;
	}
	public Value get(Key key){
		if(isEmpty()) return null;
		int i = rank(key);
		if(i < size && key.compareTo(keys[i]) == 0)   
			return values[rank(key)];
		return null;
	}
	public void delete(Key key){
		put(key, null);
	}
	public boolean contains(Key key){
		return get(key) != null;
	}
	public boolean isEmpty(){
		return size == 0;
	}
	public int size(){
		return size;
	}
	/*Binary Search for the key*/
	private int rank(Key key){
		int N = keys.length;
		int lo = 0 , hi = N-1;
		while (lo <= hi){
			int mid = lo + (hi - lo)/2;
			int cmp = key.compareTo(keys[mid]);
			if (cmp < 0) hi = mid - 1;
			else if(cmp > 0) lo = mid + 1;
			else return mid;
		}
		return lo;
	}
}
