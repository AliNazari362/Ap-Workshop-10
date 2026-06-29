import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private final ArrayList<String> users;

    public Server() {
        users = new ArrayList<>();
    }

    @Override
    public void run() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(5002)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    threadPool.execute(new ClientHandler(socket, users));
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
