import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TailN {

    public void readByRandomAccessFile(File filePath, int numOfLastLine) {
        int readLines = 0;
        StringBuilder builder = new StringBuilder();
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r")){
            long fileLength = filePath.length() - 1;
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

    public void readByMappedFile(File filePath, int numOfLastLine) throws IOException{
        FileInputStream fileInputStream = new FileInputStream(filePath);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        buffer.position((int)channel.size());
        int count = 0;
        StringBuilder builder = new StringBuilder();
        for(long i = channel.size()-1;i >= 0; i--){
            char c = (char) buffer.get((int) i);
            builder.append(c);
            if(c=='\n'){
                if(count==numOfLastLine)break;
                count++;
                builder.reverse();
                System.out.print(builder);
                builder=new StringBuilder();
            }
        }
        channel.close();
    }
}

