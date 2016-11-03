package it.floydjohn.vusolo.gui.events;

/**
 * Created by alessandro on 11/3/16.
 */
public class Event {
    public final Type type;
    public final String message;

    public Event(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public enum Type {
        Error, Info
    }
}
