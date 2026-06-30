import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Your name: ");
        String user = scanner.next().trim();
        scanner.nextLine();

        System.out.println("Welcome " + user + " to chatroom.");
        try (Socket socket = new Socket("127.0.0.1", 5002)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Thread getThread = new GetThread(in);
            getThread.start();

            while (true) {
                System.out.print("Send a message: ");
                String msg = scanner.nextLine();

                Alert alert;
                if (msg.equals("#exit")) alert = new ExitAlert(user);
                else alert = new Message(user, msg);

                out.writeObject(alert);
                if (alert instanceof ExitAlert) break;
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
