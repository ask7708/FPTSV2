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

	/**
	 * The stack that holds actions to be undone
	 */
	private Stack<TypeOfTransaction> undoStack = new Stack<TypeOfTransaction>();

	/**
	 * The stack that holds actions to be redone
	 */
	private Stack<TypeOfTransaction> redoStack = new Stack<TypeOfTransaction>();

	/**
	 * The type of transaction being used
	 */
	private TypeOfTransaction t;

	/**
	 * Executes the respective transaction to the type being passed in
	 * 
	 * @param t
	 */
	public void executeTransaction(TypeOfTransaction t) {

		this.t = t;
		t.execute();
		undoStack.add(t);

	}

	/**
	 * Returns the undo stack
	 * 
	 * @return
	 */
	public Stack<TypeOfTransaction> getUndoStack() {

		return this.undoStack;
	}

	/**
	 * Returns the redo stack
	 * 
	 * @return
	 */
	public Stack<TypeOfTransaction> getRedoStack() {

		return this.redoStack;
	}

	/**
	 * Returns whether the undo stack is empty or not
	 * 
	 * @return
	 */
	public boolean isUndoAvailable() {

		return undoStack.size() != 0;
	}

	/**
	 * Returns whether the redo stack is available
	 * 
	 * @return
	 */
	public boolean isRedoAvailable() {

		return redoStack.size() != 0;
	}

	/**
	 * Undoes whatever action is within the undo stack
	 */
	public void undo() {

		if (undoStack.get(0) != null) {

			TypeOfTransaction tot = undoStack.pop();
			redoStack.add(tot);
			tot.undo();

		}

	}

	/**
	 * Redoes whatever action is within the redo stack
	 */
	public void redo() {

		if (redoStack.get(0) != null) {

			TypeOfTransaction tot = redoStack.pop();
			undoStack.add(tot);
			tot.redo();

		}

	}

}
