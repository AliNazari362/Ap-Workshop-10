public class ExitAlert implements Alert {

    private final String user;

    public ExitAlert(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user + " left.";
    }
}
