import java.util.List;
import java.util.Random;

public class Producer implements Runnable{

    private final List<String> buffer;

    public Producer (List<String> buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        String[] messages = {"message1", "message2", "message3", "message4", "message5"};
        for (String message : messages){
            synchronized (buffer){
                buffer.add(message);
                try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e){
                    System.out.println(Thread.currentThread().getName() + " interrupted.");
                }
            }
            System.out.println(Thread.currentThread().getName()+ " added " + message);
        }
        System.out.println(Thread.currentThread().getName() + " added " + Main.EOF);
        synchronized (buffer){
            buffer.add(Main.EOF);
        }
    }
}
