import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {
    private final String filePath;
    private String host;
    private port;
    ClientCmd clientCmd;

    Client(String[] args) {
        clientCmd = new ClientCmd(args);
        host = clientCmd.getHost();
        port = clientCmd.getPort();
        filePath = clientCmd.getFilePath();
    }

    Client(){}

    private void run() {
        Client client = new Client();
        client.setHost("127.0.0.1");
        client.setPort(8888);
        client.setFilePath("D:\\Picture\\WALLPAPER\\ThemeA\\img20.jpg");
        SocketChannel socketChannel = client.CreateChannel(client.getHost(), client.getPort());
        client.sendFile(socketChannel, client.getFilePath());
    }

    private void runWithCmd(){
        SocketChannel socketChannel = CreateChannel(host, port);
        sendFile(socketChannel, filePath);
    }

    private void sendFind(SocketChannel socketChannel, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        FileChannel inChannel = FileChannel.open(path);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(inChannel.read(buffer) > 0){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.close();
    }

    private SocketChannel CreateChannel(String host, int port) throws IOException{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);

        SocketAddress sockAddr = new InetSocketAddress(host, port);
        socketChannel.connect(sockAddr);
        System.out.println("Connected... Now sending file");
        return socketChannel;
    }
    

    public String getFilePath(){
        return filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public int getPort(){
        return port;
    }

    public void setPort(int port){
        this.port = port;
    }

    public String getHost(){
        return host;
    }

    public void setHost(String host){
        this.host = host;
    }
}