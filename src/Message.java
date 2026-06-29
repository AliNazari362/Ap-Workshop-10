public class Message {

    private final String user;
    private final String text;

    public Message(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user + ": " + text;
    }
}
