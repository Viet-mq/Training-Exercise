import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("src/input.txt"));
            Scanner scanner1 = new Scanner(new File("src/search.txt"));
            while (scanner.hasNext()){
                a.add(scanner.nextInt());
            }

            while (scanner1.hasNext()){
                b.add(scanner1.nextInt());
            }

            System.out.println("Input array: ");
            for (int item : a){
                System.out.print(item + " ");
            }
            System.out.println();

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //set flag to determine if the element of b is in array a
        int[] flag = new int[b.size()];

        // Show result
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a) {
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
