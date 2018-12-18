import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

//$Id$

public class Board {
	private Board[] neighBors;
	private int[][] tiles;
	public Board(int[][] blocks){
		// construct a board from an n-by-n array of blocks
	    // (where blocks[i][j] = block in row i, column j)
		tiles = copyTiles(blocks);
	}
	private int[][] copyTiles(int[][] arrayToCopy){
		int[][] copy = new int[arrayToCopy.length][];
        for (int r = 0; r < arrayToCopy.length; r++) {
            copy[r] = arrayToCopy[r].clone();
        }
        return copy;
	}
	private void exchangeBlocks(int[][] blocks, int iFirstBlock, int jFirstBlock, int iSecondsBlock, int jSecondBlock) {
        int firstValue = blocks[iFirstBlock][jFirstBlock];
        blocks[iFirstBlock][jFirstBlock] = blocks[iSecondsBlock][jSecondBlock];
        blocks[iSecondsBlock][jSecondBlock] = firstValue;
    }
	public int dimension(){
		return tiles.length;
	}
	public int hamming(){
		// number of blocks out of place
		 int value = -1;
	        for (int i = 0; i < tiles.length; i++) {
	            for (int j = 0; j < tiles[i].length; j++) {
	            	if(tiles[i][j] != (i * dimension() + j + 1)){
	            		value ++;
	            	}
	            }
	        }    
		return value;
	}
	public int manhattan(){
		// sum of Manhattan distances between blocks and goal
		int value = 0;
		for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
            	int expectedValue = i * dimension() + j + 1;
            	int actualValue = tiles[i][j];
            	if(actualValue != expectedValue){
            		int goalI = actualValue / dimension();
            		int goalJ = actualValue % dimension();
            		value += Math.abs(goalI -i) + Math.abs(goalJ - j); 
            	}
            }
		}   
		return value;
	}
	public boolean isGoal(){
		// is this board the goal board?
		return hamming() == 0;
	}
	public Board twin(){
		// a board that is obtained by exchanging any pair of blocks
		int[][] twinBlock = copyTiles(tiles);
		int i = 0;
		int j = 0;
		while (tiles[i][j] == 0){
			j++;
			if(j >= tiles.length - 1){
				i++;
				j=0;
			}
		}
		exchangeBlocks(twinBlock, i, j, i, j + 1);
		return new Board(twinBlock);
	}
	public boolean equals(Object y){
		// does this board equal y?
		if(y == null) return false;
		if(y.getClass() != this.getClass()) return false;
		if (this == y) return true;
		Board that = (Board)y;
		if(that.dimension() != this.dimension()) return false;
		for (int i = 0; i < tiles.length; i++) {
			if(this.tiles[i].length != that.tiles[i].length) return false;
            for (int j = 0; j < tiles[i].length; j++) {
            	if(this.tiles[i][j] != that.tiles[i][j]) return false;
            }
		}
		return true;
	}
	public Iterable<Board> neighbors(){
		// all neighboring boards
		return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                if (neighBors == null) {
                    findNeighbors();
                }
                return new NeighBorIterator();
            }
        };
	}
	private void findNeighbors(){
		List<Board> neighborsFound = new ArrayList<Board>();
		int i = 0;
		int j = 0;
		
		while (tiles[i][j] != 0) {
            j++;
            if (j >= dimension()) {
                i++;
                j = 0;
            }
        }
		if (i > 0) {
            int[][] neighborTiles = copyTiles(tiles);
            exchangeBlocks(neighborTiles, i - 1, j, i, j);
            neighborsFound.add(new Board(neighborTiles));
        }
        if (i < dimension() - 1) {
            int[][] neighborTiles = copyTiles(tiles);
            exchangeBlocks(neighborTiles, i, j, i + 1, j);
            neighborsFound.add(new Board(neighborTiles));
        }
        if (j > 0) {
            int[][] neighborTiles = copyTiles(tiles);
            exchangeBlocks(neighborTiles, i, j - 1, i, j);
            neighborsFound.add(new Board(neighborTiles));
        }
        if (j < dimension() - 1) {
            int[][] neighborTiles = copyTiles(tiles);
            exchangeBlocks(neighborTiles, i, j, i, j + 1);
            neighborsFound.add(new Board(neighborTiles));
        }

        neighBors = neighborsFound.toArray(new Board[neighborsFound.size()]);
	}
	public String toString(){
		// string representation of this board (in the output format specified below)
		StringBuilder boardStringBuilder = new StringBuilder(tiles.length + "\n");

        for (int[] row : tiles) {
            for (int block : row) {
                boardStringBuilder.append(" ");
                boardStringBuilder.append(block);
            }
            boardStringBuilder.append("\n");
        }

        return boardStringBuilder.toString();
	}
	private class NeighBorIterator implements Iterator<Board>{
		int index = 0;
		@Override
		public boolean hasNext() {
			return index < neighBors.length;
		}

		@Override
		public Board next() {
			if(hasNext()){
				return neighBors[index++];
			}else{
				throw new NoSuchElementException("There is no next neighbor.");
			}
		}
		 
	}
}
