public class CommandLineProcessor {

    private final String[] args;

    CommandLineProcessor (String[] args){
        this.args = args;
    }

    public String getFilePath(){
        return args[0];
    }

    public int getNumOfLastLine(){
        return Integer.parseInt(args[1]);
    }

    public String getOption(){
        return args[2];
    }
}
