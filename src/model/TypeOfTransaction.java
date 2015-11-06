package model;
/**
 * 
 */

/**
 * @author Arsh
 *
 */
public interface TypeOfTransaction {
	
	/**
	 * Execute method for respective transaction
	 */
	public void execute();
	
	/**
	 * Undoes respective transaction
	 */
	public void undo();
	
	/**
	 * Redoes the respective action
	 */
	public void redo();
	
}
