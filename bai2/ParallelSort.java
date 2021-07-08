import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class ParallelSort {

    public void runSort() throws IOException {
        int SIZE, THREAD;
        ArrayManager arrayManager = new ArrayManager();
        ReadConfigFile readConfigFile = new ReadConfigFile();
        readConfigFile.getProperties();
        SIZE = readConfigFile.getSIZE();
        THREAD = readConfigFile.getTHREAD();

        Integer[] a;
        Comparator<Integer> comp = Comparator.naturalOrder();

        if (THREAD == 1) {
            System.out.printf("Using %d Thread, Array's size: %d\n", THREAD, SIZE);
        } else {
            System.out.printf("Using %d Threads, Array's size: %d\n", THREAD, SIZE);
        }

        a = arrayManager.createRandomArray(SIZE);

        ParallelMergeSorter.sort(a, comp, THREAD);
        FileWriter fileWriter = new FileWriter("src/output.txt");

        for (int j = 0; j < SIZE; j++) {
            fileWriter.write(a[j] + " ");
        }

        fileWriter.write("\n");
        fileWriter.close();
        if (!arrayManager.isSorted(a, comp)) {
            throw new RuntimeException("not sorted afterward: " + Arrays.toString(a));
        }

        System.out.println("Sorted array is saved in file output.txt");
    }
}