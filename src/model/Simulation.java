package model;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * @author dxr5716 Daniel Roach
 */
public class Simulation {

    private ObjectProperty<LocalDate> startDate;
    private ObjectProperty<LocalDate> endDate;
    private IntegerProperty amount;
    private Interval step;
    private SimulationType marketType;
    private DoubleProperty percentage;
    private StringProperty shortVersion;

    public Simulation() {

        this.marketType = null;
        this.step = null;
        this.amount = null;
        this.startDate = null;
        this.endDate = null;
        this.percentage = null;
        this.shortVersion = new SimpleStringProperty("NOTHING HERE YET");
    }

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

    /** Getter for short version of toString property (JavaFX style) **/
    public String getShortVersion() {

        return marketType.toString() + "/" +
                getTimeAmount() + " " + step.toString() + " @ " +
                String.format("%.01f", percentageProperty().getValue()*100) +
                "%" + "/" + getStartDate().toString();
    }

    public void setShortVersion() {

        this.shortVersion = toStringShortProperty();
    }

    public StringProperty shortVersionProperty() { return this.shortVersion; }

    /** Getters and Setters for amount property (JavaFX style) **/
    public int getTimeAmount() { return this.amount.get(); }

    public void setTimeAmount(int amount) {

        this.amount = new SimpleIntegerProperty(amount);
        /*this.amount.set(amount);*/ }

    public IntegerProperty amountProperty() { return this.amount; }

    /** Getters and Setters for percentage property (JavaFX style) **/
    public double getPercentage() { return this.percentage.get(); }

    public void setPercentage(double pct) {

        this.percentage = new SimpleDoubleProperty(pct);
        /*this.percentage.set(pct)*/
    }

    public DoubleProperty percentageProperty() { return this.percentage; }

    /** Getters and Setters for Interval property (JavaFX style) **/
    public Interval getStep() { return this.step; }

    public void setStep(Interval step) { this.step = step; }

    /** Getters and Setters for SimulationType property (JavaFX style) **/
    public SimulationType getMarketType() { return this.marketType; }

    public void setMarketType(SimulationType mType) { this.marketType = mType; }

//    /** Getters and Setters for old portfolio value property (JavaFX style) **/
//    public double getOldPVal() { return this.portfolioValBefore.get(); }
//
//    public DoubleProperty oldPValProperty() { return this.portfolioValBefore; }

    /** Getters and Setters for start date property (JavaFX style) **/
    public LocalDate getStartDate() { return  this.startDate.get(); }

    public void setStartDate(LocalDate date) {

        this.startDate = new SimpleObjectProperty<LocalDate>(date);
        /*this.startDate.set(date);*/ }

    public ObjectProperty<LocalDate> startDateProperty() { return this
            .startDate; }

    /** Getter and Setters for end date property (JavaFX style) **/
    public LocalDate getEndDate() { return this.endDate.get(); }

    public void setEndDate(LocalDate date) {

        this.endDate = new SimpleObjectProperty<LocalDate>(date);
        /*this.endDate.set(date);*/ }

    public ObjectProperty<LocalDate> endDateProperty() { return this.endDate; }

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

    public StringProperty toStringShortProperty() {

        System.out.println("to string called now");
        return new SimpleStringProperty(marketType.toString() + " / " +
                getTimeAmount() + " " + step.toString() + " @ " +
                String.format("%.01f", percentageProperty().getValue()*100) +
                "%" + " / " + getStartDate().toString());
    }

    public static void main(String[] args) {

        Simulation sim1 = new Simulation(SimulationType.BULL, Interval.MONTH,
                7, 3.3, LocalDate.now());

        System.out.println(sim1.toStringShortProperty().get());
    }
}
