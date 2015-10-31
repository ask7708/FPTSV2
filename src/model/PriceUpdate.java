package model;

import java.util.TimerTask;

import app.App;

public class PriceUpdate extends TimerTask {

   private App mainApp;
   
   public PriceUpdate(App application) { this.mainApp = application; }
   
   @Override
   public void run() {
      // TODO Auto-generated method stub
      
      for(Holdings obj: mainApp.getHoldings()) {
         
         if((obj instanceof Equity) == false)
            continue;
         
         Equity o = (Equity) obj;
         String tSym = o.getTickSymbol();
         
         
      }
   }

}
