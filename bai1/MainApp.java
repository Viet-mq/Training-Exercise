import java.io.File;

public class MainApp {
    CommandLineProcessor commandLineProcessor;
    private final String filePath;
    private final int numOfLastLine;
    private final String option;

    MainApp(String[] args){
        commandLineProcessor = new CommandLineProcessor(args);
        filePath = commandLineProcessor.getFilePath();
        numOfLastLine = commandLineProcessor.getNumOfLastLine();
        option = commandLineProcessor.getOption();
    }

    public void run(){
        TailN tailN = TailNAlgoSelection.selectAlgo(option);
        File file = new File(filePath);
        try {
            if (tailN != null) {
                tailN.readByRandomAccessFile(file, numOfLastLine);
                //tailN.readByMappedFile(file, numOfLastLine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
