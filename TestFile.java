import heaps.MinHeap;

//$Id$

public class TestFile {
	public static void main(String[] args){
		MinHeap<Integer> minHeap= new MinHeap<Integer>(6);
		minHeap.insert(12);
		minHeap.insert(6);
		System.out.println(minHeap);
		
	}
	
}
