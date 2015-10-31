package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dxr5716 Daniel Roach
 */
public class EquityStrategy implements YahooStrategy {

    @Override
    public double request(String rqst) {

        StringBuilder response = new StringBuilder();
        double price = 0.0;

        try {

            URL YahooURL = new URL(base + rqst + end);
            HttpURLConnection con = (HttpURLConnection) YahooURL
                    .openConnection();

            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(10000);


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

        } catch (MalformedURLException e) {

            System.err.println("Invalid URL given");

        } catch (IOException e) {

            System.err.println(e.getMessage());
        }

        Pattern p = Pattern.compile(Pattern.quote("<LastTradePriceOnly>") +
                "(" + ".*?)" + Pattern.quote("</LastTradePriceOnly>"));

        Matcher m = p.matcher(response);

        while(m.find())
            price = Double.parseDouble(m.group(1));

        return price;
    }

    public static void main(String[] args) {

        YahooStrategy company = new EquityStrategy();
        YahooContext context = new YahooContext(company);

        double price = context.executeStrategy("AAPL");

        System.out.println("The price of a share of Apple is: $" + price);
    }
}
