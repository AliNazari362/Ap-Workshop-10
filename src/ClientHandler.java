import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Server server;
    private ObjectOutputStream out;
    private final Socket socket;
    private ObjectInputStream in;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        this.server = server;
    }

    @Override
    public void run() {
        server.addClient(this);
        while (true) {
            try {
                Alert newAlert = (Alert) in.readObject();

                if (newAlert instanceof Message msg) server.broadcast(msg, this);
                else if (newAlert instanceof ExitAlert exAl) {
                    server.removeClient(new Message(exAl.getUser(), exAl.toString(), true), this);
                    break;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void handleMsg(Message msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
