package model;

/**
 * The context for the Password Encryption / Decryption 
 * Strategy pattern
 * 
 * @author dxr5716 Daniel Roach
 * @author ask7708 Arshdeep Khalsa
 * @author tna3531 Talal Alsarrani
 * @author dcc7331 Daniel Cypher
 */
public class PasswordMgmtContext {

   /**
    * the strategy that will be used
    */
    private PasswordMgmtStrategy strategy;

    /**
     * Constructs the context with the given strategy 
     * @param strategy the strategy to use
     */
    public PasswordMgmtContext(PasswordMgmtStrategy strategy) {

        this.strategy = strategy;
    }

    /**
     * Executes the given strategy on the password
     * @param password the password to encrypt or decrypt
     * @return the encrypted or decrypted password
     */
    public String executeStrategy(String password) {
        
        return strategy.assessPassword(password);
    }
}
