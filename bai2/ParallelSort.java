import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.Random;

public class ParallelSort {

    public static final String FILE_CONFIG = "\\config.properties";

    public static void main(String[] args) throws IOException {
        runSort();
    }

    /**
     * Runs a nested for loop of tests that call ParallelMergeSorter and
     * then checks the array afterwards to ensure correct sorting
     */
    public static void runSort() throws IOException {
        int SIZE = 1, THREAD = 1;

        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            String currentDir = System.getProperty("user.dir");
            inputStream = new FileInputStream(currentDir + FILE_CONFIG);

            // load properties from file
            properties.load(inputStream);

            // get property by name
            String arraySize = properties.getProperty("ARRAYSIZE");
            String thread = properties.getProperty("THREAD");
            SIZE = Integer.parseInt(arraySize);
            THREAD = Integer.parseInt(thread);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Integer[] a;

        Comparator<Integer> comp = new Comparator<Integer>() {
            public int compare(Integer d1, Integer d2) {
                return d1.compareTo(d2);
            }
        };

        if (THREAD == 1) {
            System.out.printf("Using %d Thread, Array size: %d\n", THREAD, SIZE);
        } else {
            System.out.printf("Using %d Threads, Array size: %d\n", THREAD, SIZE);
        }

        a = createRandomArray(SIZE);

        ParallelMergeSorter.sort(a, comp, THREAD);
        FileWriter fileWriter = new FileWriter("src/output.txt");

        for (int j = 0; j < SIZE; j++){
            fileWriter.write(a[j] + " ");
        }

        fileWriter.write("\n");
        fileWriter.close();
        if (!isSorted(a, comp)) {
            throw new RuntimeException("not sorted afterward: " + Arrays.toString(a));
        }

        System.out.println("Sorted array is saved in file output.txt");
    }



    /**
     * Returns true if the given array is in sorted ascending order.
     *
     * @param a    the array to examine
     * @param comp the comparator to compare array elements
     * @return true if the given array is sorted, false otherwise
     */
    public static <E> boolean isSorted(E[] a, Comparator<? super E> comp) {
        for (int i = 0; i < a.length - 1; i++) {
            if (comp.compare(a[i], a[i + 1]) > 0) {
                System.out.println(a[i] + " > " + a[i + 1]);
                return false;
            }
        }
        return true;
    }

    // Randomly rearranges the elements of the given array.
    public static <E> void shuffle(E[] a) {
        for (int i = 0; i < a.length; i++) {
            // move element i to a random index in [i .. length-1]
            int randomIndex = (int) (Math.random() * a.length - i);
            swap(a, i, i + randomIndex);
        }
    }

    // Swaps the values at the two given indexes in the given array.
    public static <E> void swap(E[] a, int i, int j) {
        if (i != j) {
            E temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    // Creates an array of the given length, fills it with random non-negative integers, and returns it.
    public static Integer[] createRandomArray(int length) {
        Integer[] a = new Integer[length];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt(10000000);
        }
        return a;
    }
}