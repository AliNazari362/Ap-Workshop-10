import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private List<ClientHandler> clientHandlers;

    public Server() {
        clientHandlers = Collections.synchronizedList(new ArrayList<>());
    }

    public void broadcast(Message msg, ClientHandler sender) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (sender == clientHandler) continue;
            clientHandler.handleMsg(msg);
        }
    }

    public void removeClient(Message msg, ClientHandler sender) {
        clientHandlers.remove(sender);
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.handleMsg(msg);
        }
    }

    public void addClient(ClientHandler newClient) {
        clientHandlers.add(newClient);
    }

    public static void main(String[] args) {
        Server server = new Server();
        ExecutorService service = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(5002)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler newClientHandler = new ClientHandler(socket, server);
                service.execute(newClientHandler);
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
