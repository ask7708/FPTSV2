import java.util.Stack;

import model.Equity;

/**
 * 
 */

/**
 * @author Arsh
 *
 */
public class TypeOfTransactionManager {
	
	
	private Stack<TypeOfTransaction> transactionStack = new Stack();
	private Stack<TypeOfTransaction> redoStack = new Stack();
	
	public void executeTransaction(TypeOfTransaction t){
		
		t.execute();
		transactionStack.add(t);
		redoStack.add(t);
		
	}

	public Stack<TypeOfTransaction> getUndoStack(){
		
		
		return this.transactionStack;
	}
	
	public Stack<TypeOfTransaction> getRedoStack(){
		
		
		return this.redoStack;
	}
	
	public boolean isUndoAvailable(){
		
		return transactionStack.get(0) != null;
	}
	
	public void undo(){
		
		if(transactionStack.get(0) != null){
			
			
			TypeOfTransaction tot = transactionStack.pop();
			tot.undo();
			
					
		}
	
		
	
	}
	
}
