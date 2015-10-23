package model;

/**
 * @author dxr5716 Daniel Roach
 */
public class PasswordMgmtContext {

    private PasswordMgmtStrategy strategy;

    public PasswordMgmtContext(PasswordMgmtStrategy strategy) {

        this.strategy = strategy;
    }

    public String executeStrategy(String password) {
        
        return strategy.assessPassword(password);
    }
}
