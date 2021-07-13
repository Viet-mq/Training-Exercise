import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket = null;

    public static void main(String[] args) throws IOException {

        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Server started.");
        } catch (Exception e) {
            System.err.println("Port in use.");
            System.exit(1);
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Connection Accept : " + clientSocket);
                Thread t = new Thread(new ClientConnection(clientSocket));
                t.start();

            } catch (Exception e) {
                System.err.println("Connection Error.");
            }
        }
    }
}