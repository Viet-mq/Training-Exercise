import java.util.ArrayList;
import java.util.Comparator;

public class MainApp {
    private final ArrayList<Integer> a = new ArrayList<>();
    private final ArrayList<Integer> b = new ArrayList<>();
    private final String fileInputPath;
    private final String fileSearchPath;
    CommandLineProcessor commandLineProcessor;

    MainApp(String[] args){
        commandLineProcessor = new CommandLineProcessor(args);
        fileInputPath = commandLineProcessor.getFileInputPath();
        fileSearchPath = commandLineProcessor.getFileSearchPath();
    }

    public void run() throws InterruptedException {
        ReadFile rf1 = new ReadFile(a, fileInputPath);
        ReadFile rf2 = new ReadFile(b, fileSearchPath);
        rf1.getData();
        rf2.getData();

        //set flag to determine if the element of b is in array a
        int[] flag = new int[b.size()];

        // Run 3 threads
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a) {
                    System.out.println("Search result:");
                    for (int itemb : b){
                        if (flag[b.indexOf(itemb)] == 1) {
                            System.out.println("Input array contains: " + itemb);
                        } else {
                            System.out.println("Input array doesn't contains: " + itemb);
                        }
                    }
                }
            }

        });

        // Sort array
        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a) {
                    a.sort(Comparator.naturalOrder());
                    System.out.println("Sorted array: ");
                    for (int item : a){
                        System.out.print(item + " ");
                    }
                    System.out.println();
                }
            }
        });

        //Search
        Thread C = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a) {
                    for(int itemb : b){
                        for (int itema : a){
                            if (itemb == itema) flag[b.indexOf(itemb)] = 1;
                        }
                    }
                }
            }
        });


        B.start();
        B.join();
        C.start();
        C.join();
        A.start();
        A.join();
    }
}
