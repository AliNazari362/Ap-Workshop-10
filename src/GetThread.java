import java.io.IOException;
import java.io.ObjectInputStream;

public class GetThread extends Thread {

    private final ObjectInputStream in;

    public GetThread(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        while (true) {
            Message message;
            try {
                message = (Message) in.readObject();
            } catch (IOException e) {
                System.out.println("Socket disconnected. Good bye!");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("ERROR: " + e.getMessage());
                continue;
            }
            System.out.println();
            System.out.println(message);
        }
    }
}
