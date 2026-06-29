import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private final List<String> users;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public ClientHandler(Socket socket, List<String> users) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        this.users = users;
    }

    @Override
    public void run() {
        try {
            Alert alert = (Alert) in.readObject();

            if (alert instanceof Message msg) msgHandler(msg);
            if (alert instanceof ExitAlert exAl) exitHandler(exAl);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void msgHandler(Message msg) throws IOException {
        for (String user : users) {
            if (!user.equals(msg.getUser())) {
                out.writeObject(msg);
            }
        }
    }

    private void exitHandler(ExitAlert exAL) throws IOException {
        String client = exAL.getUser();
        Message exMsg = new Message(client, exAL.toString());
        for (String user : users) {
            if (!user.equals(exAL.getUser())) {
                out.writeObject(exMsg);
            }
        }
    }
}
