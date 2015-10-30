package model;

/**
 * @author dxr5716 Daniel Roach
 */
public class YahooContext {

    private YahooStrategy s;

    public YahooContext(YahooStrategy strategy) { this.s = strategy; }

    public double executeStrategy(String rqst) { return s.request(rqst);}
}
