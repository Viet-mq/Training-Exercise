public class ClientCmd implements CommandLineProcessor{
    private String[] args;

    ClientCmd(String[] args){
        this.args = args;
    }

    public String getHost(){
        return args[0];
    }

    public int getPort(){
        return Integer.parseInt(args[1]);
    }

    public String getFilePath(){
        return args[2];
    }

}