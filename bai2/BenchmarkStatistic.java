import java.util.Arrays;
import java.util.Comparator;

public class BenchmarkStatistic {

    public void run() {
        int availableThreads = (Runtime.getRuntime().availableProcessors()) * 2;
        ArrayManager arrayManager = new ArrayManager();
        Integer[] a;
        Comparator<Integer> comp = Comparator.naturalOrder();

        ReadConfigFile readConfigFile = new ReadConfigFile();
        readConfigFile.getProperties();
        int SIZE = readConfigFile.getSIZE();
        int ROUNDS = readConfigFile.getROUNDS();

        System.out.printf("\nMax number of threads == %d\n\n", availableThreads);
        System.out.println("Benchmark with origin array's size: " + SIZE + ", rounds: " + ROUNDS);
        for (int i = 1; i <= availableThreads; i *= 2) {
            if (i == 1) {
                System.out.printf("%d Thread:\n", i);
            } else {
                System.out.printf("%d Threads:\n", i);
            }

            for (int j = 0, k = SIZE; j < ROUNDS; ++j, k *= 2) {
                a = arrayManager.createRandomArray(k);
                // run the algorithm and time how long it takes to sort the elements
                long startTime = System.currentTimeMillis();
                ParallelMergeSorter.sort(a, comp, availableThreads);
                long endTime = System.currentTimeMillis();

                if (!arrayManager.isSorted(a, comp)) {
                    throw new RuntimeException("not sorted afterward: " + Arrays.toString(a));
                }

                System.out.printf("%10d elements  =>  %6d ms \n", k, endTime - startTime);
            }


            System.out.print("\n");
        }
    }
}