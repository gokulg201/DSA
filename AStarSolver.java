import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.MinPQ;

//$Id$

public class AStarSolver {
	private List<Board> solutionBoards = new ArrayList<Board>(); 
	private boolean solved;
	public AStarSolver(Board initial){
		MinPQ<Step> prioritizedSteps = new MinPQ<AStarSolver.Step>(new StepComparator());
		prioritizedSteps.insert(new Step(initial, 0, null));
		
		MinPQ<Step> prioritizedStepsTwin = new MinPQ<>(new StepComparator());
		prioritizedStepsTwin.insert(new Step(initial.twin(), 0, null));
        
		Step finalisedStep = null;
		while(!prioritizedSteps.min().getBoard().isGoal()){
			finalisedStep = prioritizedSteps.delMin();
			for(Board neighbour:finalisedStep.getBoard().neighbors()){
				if(!isStepAlreadyVisited(neighbour, finalisedStep)){
					prioritizedSteps.insert(new Step(neighbour, finalisedStep.getMoves() + 1, finalisedStep));
				}
			}
			Step finalisedTwinStep = prioritizedStepsTwin.delMin();
			for(Board neighbour:finalisedTwinStep.getBoard().neighbors()){
				if(!isStepAlreadyVisited(neighbour, finalisedTwinStep)){
					prioritizedStepsTwin.insert(new Step(neighbour, finalisedTwinStep.getMoves() + 1, finalisedTwinStep));
				}
			}
			
			if(prioritizedSteps.isEmpty() || prioritizedStepsTwin.isEmpty()){
            	break;
            }
		}
		 if(!prioritizedSteps.isEmpty()){
			 finalisedStep = prioritizedSteps.delMin();
        }
    	solved = finalisedStep.getBoard().isGoal();

        solutionBoards.add(finalisedStep.getBoard());
        while ((finalisedStep = finalisedStep.getPreviousStep()) != null) {
            solutionBoards.add(finalisedStep.getBoard());
        }		
	}
	public boolean isSolvable(){
    	// is the initial board solvable?
    	return solved;
    }
    public int moves(){
    	// min number of moves to solve initial board; -1 if unsolvable
    	int moves;
    	if(isSolvable()){
    		moves = solutionBoards.size() - 1;
    	}else{
    		System.out.println(solutionBoards.size());
    		moves = -1;
    	}
    	return moves;
    }
    public Iterable<Board> solution(){
    	// sequence of boards in a shortest solution; null if unsolvable
    	Iterable<Board> iterable;
    	if(isSolvable()){
    		iterable = new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {
                    return new SolutionIterator();
                }
            };
    	}else{
    		iterable = null;
    	}
    	return iterable;
    }
    private boolean isStepAlreadyVisited(Board board, Step previousStep){
    	Step previous = previousStep;
    	while(previous.getPreviousStep() != null){
    		if(previous.getBoard().equals(board)){
        		return true;
        	}
    		previous = previous.getPreviousStep();
    	}
    	return false;
    }
    
    private class Step{
    	int moves;
    	Board board;
    	Step previousStep;
    	public Step(Board board,int moves,Step previousStep){
    		this.board =  board;
    		this.moves = moves;
    		this.previousStep = previousStep;
    	}
    	public int getMoves() {
            return moves;
        }
    	public Board getBoard() {
            return board;
        }
        public Step getPreviousStep() {
            return previousStep;
        }
        public int priority(){
        	return board.manhattan() + moves;
        }
    }
    private class StepComparator implements Comparator<Step>{
		@Override
		public int compare(Step o1, Step o2) {
			return o1.priority() - o2.priority();
		}
    }
    private class SolutionIterator implements Iterator<Board> {
    	int index = solutionBoards.size();
		@Override
		public boolean hasNext() {
			return index > solutionBoards.size();
		}

		@Override
		public Board next() {
			if(hasNext()){
				return solutionBoards.get(index--);
			}else{
				throw new NoSuchElementException("There is no next neighbor.");
			}
		}
    	
    }
}
