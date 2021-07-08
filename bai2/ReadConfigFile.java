import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfigFile {
    public static final String FILE_CONFIG = "\\config.properties";
    private int SIZE;
    private int THREAD;
    private int ROUNDS;

    public void getProperties() {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try{
            String currentDir = System.getProperty("user.dir");
            inputStream = new FileInputStream(currentDir + FILE_CONFIG);
            properties.load(inputStream);
            String arraySize = properties.getProperty("ARRAYSIZE");
            String thread = properties.getProperty("THREAD");
            String rounds = properties.getProperty("ROUNDS");
            SIZE = Integer.parseInt(arraySize);
            THREAD = Integer.parseInt(thread);
            ROUNDS = Integer.parseInt(rounds);

        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null)
                    inputStream.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public int getSIZE() {
        return SIZE;
    }

    public int getTHREAD(){
        return THREAD;
    }

    public int getROUNDS(){
        return ROUNDS;
    }
}
