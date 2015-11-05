package model;

import java.util.TimerTask;

import app.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PriceUpdate extends TimerTask {

   private App mainApp;
   private Watchlist watchlist;

   public PriceUpdate(App application) { this.mainApp = application; }

   @Override
   public void run() {

      this.watchlist = mainApp.getWatchlist();

      System.out.println("Updates have started");
      YahooStrategy equityS = new EquityStrategy();
      YahooContext context = new YahooContext(equityS);

      for(Equity obj: mainApp.getMarket().getMarketEquities()) {

         System.out.print(obj.getTickSymbol() + " / " + obj.getInitPrice());
         double newPrice = context.executeStrategy(obj.getTickSymbol());
         System.out.println(" / " + newPrice);

         if(newPrice == 0.0) {
            System.out.println("newPrice is 0, skipping");
            continue;
         }

         else if(newPrice == obj.getInitPrice()) {
            System.out.println("newPrice is the same as old, skipping");
            continue;
         }

         if(newPrice != obj.getInitPrice()) {
            
            System.out.println("There is a change in the price, price being set now" );
            obj.setInitPrice(newPrice);
         }
                  
         if(watchlist.findEquity(obj.getTickSymbol()) != null) {

            if(obj.getInitPrice() >= obj.getHighTrigger()) {
               
               System.out.println("High trigger fired");
               Trigger t = new Trigger(obj.getTickSymbol() + " - High Trigger fired @ $" + String.format("%.02f", obj.getInitPrice()));
               this.watchlist.addTrigger(t);
            }
            
            if(obj.getInitPrice() <= obj.getLowTrigger()) {
               
               System.out.println("Low trigger fired");
               Trigger t = new Trigger(obj.getTickSymbol() + " - Low Trigger fired @ $" + String.format("%.02f", obj.getInitPrice()));
               this.watchlist.addTrigger(t);
            }
         }
      }

      System.out.println("Updates finished");
   }

}
