import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TailN {

    private static final String filePath = "src/text.txt";

    public static void main(String[] args) {
        TailN tailN = new TailN();
        File file = new File(filePath);
        try {
            tailN.readFromLast(file, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromLast(File file, int numOfLastLine) {
        int readLines = 0;
        StringBuilder builder = new StringBuilder();
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")){
            long fileLength = file.length() - 1;
            //Set the pointer at the last of the line
            randomAccessFile.seek(fileLength);

            for (long pointer = fileLength; pointer >= 0; pointer--) {
                randomAccessFile.seek(pointer);
                char c;
                // read from the last, one char at the time
                c = (char) randomAccessFile.read();
                // break when end of the line
                if (c == '\n') {
                    readLines++;
                    if (readLines == numOfLastLine)
                        break;
                }
                builder.append(c);
                fileLength = fileLength - pointer;
            }
            // Since line is read from the last so it is in reverse order
            builder.reverse();
            System.out.println(builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
