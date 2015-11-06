package model;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * The representation a simulation made on the user's portfolio 
 * 
 * @author dxr5716 Daniel Roach
 * @author ask7708 Arshdeep Khalsa
 * @author tna3531 Talal Alsarrani
 * @author dcc7331 Daniel Cypher
 */
public class Simulation {

   /**
    * the start date of the simulation
    */
   private ObjectProperty<LocalDate> startDate;

   /**
    * the calculated end date of the simulation
    */
   private ObjectProperty<LocalDate> endDate;

   /**
    * the number of time intervals used in the simulation
    */
   private IntegerProperty amount;

   /**
    * the type of time interval used in the simulation
    * (DAY, MONTH, YEAR)
    */
   private Interval step;

   /**
    * the type of market type used in the simulation
    * (Bull Market, Bear Market, No-Growth Market)
    */
   private SimulationType marketType;

   /**
    * the per annum percentage
    */
   private DoubleProperty percentage;

   /**
    * the formatted representation of the simulation in string format
    */
   private StringProperty shortVersion;

   /**
    * A default constructor used when parameters cannot be specified
    */
   public Simulation() {

      this.marketType = null;
      this.step = null;
      this.amount = null;
      this.startDate = null;
      this.endDate = null;
      this.percentage = null;
      this.shortVersion = new SimpleStringProperty("NOTHING HERE YET");
   }

   /**
    * 
    * @param type the market type
    * @param iType the interval type
    * @param length the number of time intervals
    * @param percent the per annum percentage
    * @param start the start date
    */
   public Simulation(SimulationType type, Interval iType, int length, double
         percent, LocalDate start) {

      this.marketType = type;
      this.step = iType;
      this.amount = new SimpleIntegerProperty(length);
      this.percentage = new SimpleDoubleProperty(percent / 100.0);
      this.startDate = new SimpleObjectProperty<LocalDate>(start);

      switch(iType) {

      case DAY:
         this.endDate = new SimpleObjectProperty<LocalDate>(startDate
               .get().plusDays(length));
         break;
      case MONTH:
         this.endDate = new SimpleObjectProperty<LocalDate>(startDate
               .get().plusMonths(length));
         break;
      case YEAR:
         this.endDate = new SimpleObjectProperty<LocalDate>(startDate
               .get().plusYears(length));
         break;
      default:
         this.endDate = new SimpleObjectProperty<LocalDate>(null);
         break;
      }

      this.shortVersion = toStringShortProperty();
   }

   /**
    * 
    * @return
    */
   public String getShortVersion() {

      return marketType.toString() + "/" +
            getTimeAmount() + " " + step.toString() + " @ " +
            String.format("%.01f", percentageProperty().getValue()*100) +
            "%" + "/" + getStartDate().toString();
   }

   /**
    * 
    */
   public void setShortVersion() {

      this.shortVersion = toStringShortProperty();
   }

   /**
    * 
    * @return
    */
   public StringProperty shortVersionProperty() { return this.shortVersion; }

   /**
    * 
    * @return
    */
   public int getTimeAmount() { return this.amount.get(); }

   /**
    * 
    * @param amount
    */
   public void setTimeAmount(int amount) { this.amount = new SimpleIntegerProperty(amount); }

   /**
    * 
    * @return
    */
   public IntegerProperty amountProperty() { return this.amount; }

   /**
    * 
    * @return
    */
   public double getPercentage() { return this.percentage.get(); }

   /**
    * 
    * @param pct
    */
   public void setPercentage(double pct) { this.percentage = new SimpleDoubleProperty(pct); }

   /**
    * 
    * @return
    */
   public DoubleProperty percentageProperty() { return this.percentage; }

   /**
    * 
    * @return
    */
   public Interval getStep() { return this.step; }

   /**
    * 
    * @param step
    */
   public void setStep(Interval step) { this.step = step; }

   /**
    * 
    * @return
    */
   public SimulationType getMarketType() { return this.marketType; }

   /**
    * 
    * @param mType
    */
   public void setMarketType(SimulationType mType) { this.marketType = mType; }

   /**
    * 
    * @return
    */
   public LocalDate getStartDate() { return  this.startDate.get(); }

   /**
    * 
    * @param date
    */
   public void setStartDate(LocalDate date) { this.startDate = new SimpleObjectProperty<LocalDate>(date); }

   /**
    * 
    * @return
    */
   public ObjectProperty<LocalDate> startDateProperty() { return this.startDate; }

   /**
    * 
    * @return
    */
   public LocalDate getEndDate() { return this.endDate.get(); }

   /**
    * 
    * @param date
    */
   public void setEndDate(LocalDate date) { this.endDate = new SimpleObjectProperty<LocalDate>(date); }

   /**
    * 
    * @return
    */
   public ObjectProperty<LocalDate> endDateProperty() { return this.endDate; }

   /**
    * 
    * @param holding
    */
   public void changeHoldingPrice(Equity holding) {

      double time = 0;
      double intrst = 0;
      double price = holding.getSimPrice();
      double newPrice = price;
      int steps = getTimeAmount();

      switch(step) {

      case DAY:
         time = getTimeAmount() / 365.0;
         break;
      case MONTH:
         time = getTimeAmount() / 12.0;
         break;
      case YEAR:
         time = getTimeAmount();
         break;
      }

      switch(marketType) {


      case NONE:
         holding.addPriceChange(newPrice);
         return;

      case BEAR:
         intrst = price * getPercentage() * time;
         steps = getTimeAmount();

         while(steps != 0) {

            newPrice -= ((1.0/getTimeAmount()) * intrst);
            steps--;
         }

         holding.addPriceChange(newPrice);
         break;

      case BULL:
         intrst = price * getPercentage() * time;
         steps = getTimeAmount();

         while(steps != 0) {

            newPrice += ((1.0/getTimeAmount()) * intrst);
            steps--;
         }

         holding.addPriceChange(newPrice);
         break;
      }

   }

   /**
    * 
    * @return
    */
   public StringProperty toStringShortProperty() {

      return new SimpleStringProperty(marketType.toString() + " / " +
            getTimeAmount() + " " + step.toString() + " @ " +
            String.format("%.01f", percentageProperty().getValue()*100) +
            "%" + " / " + getStartDate().toString());
   }
}