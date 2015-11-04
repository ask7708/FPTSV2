package model;
/**
 * 
 */

/**
 * @author Arsh
 *
 */
public interface TypeOfTransaction {
	
	public void execute();
	
	public void undo();
	
	public void redo();
	
}
