package model;

/**
 * @author dxr5716 Daniel Roach
 */
public class DecryptStrategy implements PasswordMgmtStrategy {

    @Override
    // for one password
    public String assessPassword(String pswd) {

        String decrypted = "";
        pswd = pswd.toUpperCase();

        for(int i = 0, j = 0; i < pswd.length(); i++) {

            char lttr = pswd.charAt(i);

            if(!Character.isAlphabetic(lttr)) {
                decrypted += lttr;
                continue;
            }

            decrypted += (char)((lttr - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }

        return decrypted;
    }
}
