public class CommandLineProcessor {
    private final String[] args;

    CommandLineProcessor (String[] args){
        this.args = args;
    }

    public String getFileInputPath(){
        return args[0];
    }

    public String getFileSearchPath(){
        return args[1];
    }
}
