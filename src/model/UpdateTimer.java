package model;

import app.App;

import java.util.Timer;

/**
 * @author dxr5716 Daniel Roach
 */
public class UpdateTimer extends Timer {

    public PriceUpdate update;

    public UpdateTimer() {}

    /**
     * disables price updates
     * (cannot necessarily be undone - would have to instantiate a new object)
     */
    public void disableScheduledUpdates() { this.cancel(); }

    public void cancelScheduledUpdates() {

        this.update.cancel();
    }

    public void scheduleUpdate(int initialDelay, int minutesInterval, App app) {

        if(update != null)
           this.update.cancel();

        this.update = new PriceUpdate(app);
        int delayMilliseconds = initialDelay * 60000;
        int intervalMilliseconds = minutesInterval * 60000;
        this.schedule(update, delayMilliseconds, intervalMilliseconds);
    }
}
