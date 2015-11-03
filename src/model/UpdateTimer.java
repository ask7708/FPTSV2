package model;

import app.App;

import java.util.Timer;

/**
 * @author dxr5716 Daniel Roach
 */
public class UpdateTimer extends Timer {

    private PriceUpdate update;

    public UpdateTimer() {}

    /**
     * disables price updates
     * (cannot necessarily be undone - would have to instantiate a new object)
     */
    public void disableScheduledUpdates() { this.cancel(); }

    public void cancelScheduledUpdates() {

        this.update.cancel();
    }

    public void scheduleUpdate(int minutes, App app) {

        this.update = new PriceUpdate(app);
        int milliseconds = minutes * 60000;
        this.schedule(update, 0, milliseconds);
    }
}
