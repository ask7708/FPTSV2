package model;

/**
 * @author dxr5716 Daniel Roach
 */
public class EncryptStrategy implements PasswordMgmtStrategy {

    @Override
    public String assessPassword(String pswd) {

        String encrypted = "";
        pswd = pswd.toUpperCase();

        for(int i = 0, j = 0; i < pswd.length(); i++) {

            char lttr = pswd.charAt(i);

            if(!Character.isAlphabetic(lttr)) {
                encrypted += lttr;
                continue;
            }

            encrypted += (char)((lttr + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }
        return encrypted;
    }
}
