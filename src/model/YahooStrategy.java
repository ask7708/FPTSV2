package model;

/**
 * @author dxr5716 Daniel Roach
 */
public interface YahooStrategy {

    /**
     * the base of the Yahoo URL used to retrieve the last trade price
     */
    static final String base = "http://query.yahooapis" +
            ".com/v1/public/yql?q=select%20LastTradePriceOnly%20from" +
            "%20yahoo.finance.quotes%20where%20symbol%20in%20(%22";

    /**
     * the end of the Yahoo URL used to retrieve the last trade price
     */
    static final String end = "%22)&env=store://datatables" +
            ".org/alltableswithkeys";

    /**
     * Sends a request using YQL to retrieve the price of a specific company or
     * the current Dow Jones Industrial Average
     * @param rqst the ticker symbol of the equity being requested or DJIA
     * @return the last trade value price of an equity or the current DJIA
     */
    public double request(String rqst);
}
