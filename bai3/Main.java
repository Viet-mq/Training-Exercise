import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {

        /*
          The problems describes 2 threads, the Producer and Consumer,
          sharing a common, fixed-sized buffer that is used as a queue
           1. The Producer produces message, put that message into the buffer, and keep repeating this process
           2. The Consumer consumes the message from shared buffer, one message at a time
         */

        List<String> buffer = new ArrayList<>();

        Thread producerThread = new Thread(new Producer(buffer));
        producerThread.setName("producerThread");

        Thread consumerThread = new Thread(new Consumer(buffer));
        consumerThread.setName("consumerThread");

        producerThread.start();
        consumerThread.start();
    }
}
