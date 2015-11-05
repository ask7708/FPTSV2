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
	private TypeOfTransaction t;
	
	public void executeTransaction(TypeOfTransaction t){
		
		this.t = t;
		t.execute();
		undoStack.add(t);
		
		
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
			redoStack.add(tot);
			tot.undo();
			
					
		}
	
		
	
	}

	public void redo() {
		
		if(redoStack.get(0) != null){
			
			
			TypeOfTransaction tot = redoStack.pop();
			undoStack.add(tot);
			tot.redo();
			
					
		}
		
		
		
	}
	
}
