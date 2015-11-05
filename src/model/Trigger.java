package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Trigger {

   private StringProperty message;
   
   public Trigger(String msg) { this.message = new SimpleStringProperty(msg); }
   
   public String getMessage() { return this.message.get(); }
   
   public void setMessage(String m) { this.message.set(m); }
   
   public StringProperty messageProperty() { return this.message; }
}
