package model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dxr5716 Daniel Roach
 */
public class DJIAStrategy implements YahooStrategy {

    /**
     * a list of the ticker symbols of the 30 companies that make up the
     * Dow Jones Industrial Average
     */
    private static final List<String> tickers = Arrays.asList("AAPL",
            "AXP", "BA", "CAT", "CSCO", "CVX", "DD", "DIS", "GE", "GS", "HD",
            "IBM", "INTC", "JNJ", "JPM", "KO", "MCD", "MMM", "MRK", "MSFT",
            "NKE", "PFE", "PG", "TRV", "UNH", "UTX", "V", "VZ", "WMT", "XOM");

    /**
     * the current Dow Jones divisor used for the computation of the average
     */
    private static final double dowDivisor = 0.14967727343149;

    @Override
    /**
     *
     */
    public double request(String rqst) {

        ArrayList<String> responses = new ArrayList<String>();
        ArrayList<Double> prices = new ArrayList<Double>();

        try {

            for(String sym: tickers) {

                URL YahooURL = new URL(base + sym + end);
                HttpURLConnection con = (HttpURLConnection) YahooURL
                        .openConnection();

                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                responses.add(response.toString());

                in.close();
            }

        } catch (MalformedURLException e) {

            System.err.println("Invalid URL given");

        } catch (IOException e) {

            System.err.println(e.getMessage());
        }

        for(String response: responses) {

            Pattern p = Pattern.compile(Pattern.quote("<LastTradePriceOnly>") +
                    "(" + ".*?)" + Pattern.quote("</LastTradePriceOnly>"));

            Matcher m = p.matcher(response);

            while(m.find())
                prices.add(Double.parseDouble(m.group(1)));
        }

        double total = 0;

        for(Double obj: prices)
            total += obj;

        return total / dowDivisor;
    }

    public static void main(String[] args) {

        YahooStrategy company = new DJIAStrategy();
        YahooContext context = new YahooContext(company);

        double price = context.executeStrategy("DJIA");

        System.out.println("The current index value of the DJIA is: $" +
                String.format("%.02f", price));
    }
}
