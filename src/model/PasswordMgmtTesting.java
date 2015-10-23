package model;

/**
 * @author dxr5716 Daniel Roach
 */
public class PasswordMgmtTesting {

    public static void main(String[] args) {

        String pswd = args[0];
        EncryptStrategy encryptStrategy = new EncryptStrategy();
        String encrypted = encryptStrategy.assessPassword(pswd);

        System.out.println("The original password given was: " +
                pswd);
        System.out.println("The encrypted password would be: " +
                encrypted);

        DecryptStrategy decryptStrategy = new DecryptStrategy();
        String decrypted = decryptStrategy.assessPassword(encrypted);

        System.out.println();

        System.out.println("The encrypted password given was: " +
                encrypted);
        System.out.println("The decrypted password would be: " +
                decrypted);

        String normal = "gimMEthat4//!`l2";
        String uppercased = decrypted;

        System.out.println("Are these two equal?: " + normal
                .compareToIgnoreCase(uppercased));

    }
}
