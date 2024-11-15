import java.util.concurrent.*;

public class WorkDealingSum {
    public static int sum(int[] array) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        int pairwiseSum = array.length % 2 == 0 ? 0 : array[array.length - 1];

        final int numberOfFutures = array.length / 2;
        Future<Integer>[] futures = new Future[numberOfFutures];
        for (int i = 0; i < numberOfFutures; ++i) {
            final int index = i * 2;
            futures[i] = executor.submit(() -> array[index] + array[index + 1]);
        }

        for (Future<Integer> future : futures) {
            pairwiseSum += future.get();
        }

        executor.shutdown();
        return pairwiseSum;
    }
}
