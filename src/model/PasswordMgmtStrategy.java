package model;

/**
 * @author dxr5716 Daniel Roach
 */
public interface PasswordMgmtStrategy {

    static String key = "SYSTEMPORTFOLIOTRACKSTHAT";

    String assessPassword(String pswd);
}

