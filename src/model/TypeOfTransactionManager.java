package model;
import java.util.Stack;

/**
 * 
 */

/**
 * @author Arsh
 *
 */
public class TypeOfTransactionManager {
	
	
	private Stack<TypeOfTransaction> undoStack = new Stack();
	private Stack<TypeOfTransaction> redoStack = new Stack();
	
	public void executeTransaction(TypeOfTransaction t){
		
		t.execute();
		undoStack.add(t);
		redoStack.add(t);
		
	}

	public Stack<TypeOfTransaction> getUndoStack(){
		
		
		return this.undoStack;
	}
	
	public Stack<TypeOfTransaction> getRedoStack(){
		
		
		return this.redoStack;
	}
	
	public boolean isUndoAvailable(){
		
		return undoStack.get(0) != null;
	}
	
	public void undo(){
		
		if(undoStack.get(0) != null){
			
			
			TypeOfTransaction tot = undoStack.pop();
			tot.undo();
			
					
		}
	
		
	
	}
	
}
