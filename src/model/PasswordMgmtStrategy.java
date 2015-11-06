package model;

/**
 * The interface each concrete strategy will implemented in the Strategy pattern
 * 
 * @author dxr5716 Daniel Roach
 * @author ask7708 Arshdeep Khalsa
 * @author tna3531 Talal Alsarrani
 * @author dcc7331 Daniel Cypher
 */
public interface PasswordMgmtStrategy {

   /**
    * the key the Vigenere cypher would use for decryption / encryption
    */
    static String key = "SYSTEMPORTFOLIOTRACKSTHAT";

    /**
     * Assesses the given password and returns the encrypted
     * or decrypted form of it 
     * @param pswd the password entered by the user
     * @return the encrypted or decrypted password
     */
    String assessPassword(String pswd);
}

