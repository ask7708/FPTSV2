package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The representation of a fired trigger when a market price is above or 
 * below a certain price
 * 
 * @author dxr5726 Daniel Roach
 * @author ask7708 Arshdeep Khalsa
 * @author tna3531 Talal Alsarrani
 * @author dcc7331 Daniel Cypher
 */
public class Trigger {

   /**
    * the formatted representation of a fired trigger
    */
   private StringProperty message;
   
   /**
    * Constructs a Trigger object by initializing the message to the
    * String passed in
    * @param msg the trigger's message
    */
   public Trigger(String msg) { this.message = new SimpleStringProperty(msg); }
   
   /**
    * Returns the formatted representation of the Trigger
    * @return the formatted string
    */
   public String getMessage() { return this.message.get(); }
   
   /**
    * Sets the message of this Trigger to the parameter
    * @param m the new message
    */
   public void setMessage(String m) { this.message.set(m); }
   
   /**
    * Returns the StringProperty holding the message (for JavaFX)
    * @return the message as a StringProperty
    */
   public StringProperty messageProperty() { return this.message; }
}
