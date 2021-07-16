import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class Server {

    private String fileDir;
    private int port;
    private boolean stop;
    private ServerCmd serverCmd;

    Server() {
    }

    Server(String[] args) {
        serverCmd = new ServerCmd(args);
        port = Integer.parseInt(serverCmd.getPort());
        fileDir = serverCmd.getFileDir();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(args);
        server.runWithCmd();
    }

    private void runWithCmd() throws IOException {
        SocketChannel socketChannel = createServerSocketChannel(port);
        readFileFromSocketChannel(socketChannel, fileDir);
    }

    private void run() throws IOException {
        Server server = new Server();
        server.setFileDir("C:\\Users\\MAIVIET\\Downloads\\Download\\test.png");
        server.setPort(8888);
        SocketChannel socketChannel = server.createServerSocketChannel(server.getPort());
        server.readFileFromSocketChannel(socketChannel, server.getFileDir());
    }

    private void readFileFromSocketChannel(SocketChannel socketChannel, String fileDir) throws IOException {
        Path path = Paths.get(fileDir);
        FileChannel fileChannel = FileChannel.open(path,
                EnumSet.of(StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE)
        );

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }
        fileChannel.close();
        System.out.println("Receiving file successfully!");
        socketChannel.close();
    }

    private SocketChannel createServerSocketChannel(int port) throws IOException {
        ServerSocketChannel serverSocket;
        SocketChannel client;
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        client = serverSocket.accept();

        System.out.println("connection established .." + client.getRemoteAddress());
        return client;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
