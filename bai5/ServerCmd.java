public class ServerCmd implements CommandLineProcessor {
    private String[] args;

    ServerCmd(String[] args) {
        this.args = args;
    }

    public String getPort(){
        return args[0];
    }

    public String getFileDir(){
        return args[1];
    }
}