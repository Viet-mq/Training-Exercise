import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.Random;

public class BenchmarkStatistic {

    private static final String FILE_CONFIG = "\\config.properties";;

    public static void main(String[] args) throws IOException {
        run();
    }

    public static void run() throws IOException {
        int SIZE = 1, ROUNDS = 1;   // initial length of array to sort
        int availableThreads = (Runtime.getRuntime().availableProcessors()) * 2;

        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            String currentDir = System.getProperty("user.dir");
            inputStream = new FileInputStream(currentDir + FILE_CONFIG);

            // load properties from file
            properties.load(inputStream);

            // get property by name
            String arraySize = properties.getProperty("ARRAYSIZE");
            String rounds = properties.getProperty("ROUNDS");
            SIZE = Integer.parseInt(arraySize);
            ROUNDS = Integer.parseInt(rounds);

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

        System.out.printf("\nMax number of threads == %d\n\n", availableThreads);
        System.out.println("Benchmark with origin array's size: " + SIZE + ", rounds: " + ROUNDS );
        for (int i = 1; i <= availableThreads; i *= 2) {
            if (i == 1) {
                System.out.printf("%d Thread:\n", i);
            } else {
                System.out.printf("%d Threads:\n", i);
            }

            for (int j = 0, k = SIZE; j < ROUNDS; ++j, k *= 2) {
                a = createRandomArray(k);
                // run the algorithm and time how long it takes to sort the elements
                long startTime = System.currentTimeMillis();
                ParallelMergeSorter.sort(a, comp, availableThreads);
                long endTime = System.currentTimeMillis();

                if (!isSorted(a, comp)) {
                    throw new RuntimeException("not sorted afterward: " + Arrays.toString(a));
                }

                System.out.printf("%10d elements  =>  %6d ms \n", k, endTime - startTime);
            }


            System.out.print("\n");
        }
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
            a[i] = rand.nextInt(1000);
        }
        return a;
    }
}