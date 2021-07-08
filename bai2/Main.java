import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ParallelSort ps = new ParallelSort();
        BenchmarkStatistic bs = new BenchmarkStatistic();
        bs.run();
        ps.runSort();
    }
}
