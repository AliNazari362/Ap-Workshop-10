import java.io.Serializable;

public class Message implements Alert, Serializable {

    private final String user;
    private final String text;
    private final boolean isAlert;

    public Message(String user, String text) {
        this.user = user;
        this.isAlert = false;
        this.text = text;
    }

    public Message(String user, String text, boolean isAlert) {
        this.user = user;
        this.isAlert = isAlert;
        this.text = text;
    }

    @Override
    public String toString() {
        return isAlert ? text : user + ": " + text;
    }
}
