import java.util.concurrent.*;

public class Main {
    private static int findSumUsingWorkStealing(int[] array){
        int pairwiseSum;
        try (ForkJoinPool pool = new ForkJoinPool()) {
            WorkStealingSum task = new WorkStealingSum(array, 0, array.length);
            pairwiseSum = pool.invoke(task);
        }
        return pairwiseSum;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] array = new int[100000]; // Large array
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);
        int pairwiseSum = 0;

        Future<Integer>[] futures = new Future[array.length - 1];
        for (int i = 0; i < array.length - 1; i++) {
            final int idx = i;
            futures[i] = executor.submit(() -> array[idx] + array[idx + 1]);
        }

        for (Future<Integer> future : futures) {
            pairwiseSum += future.get();
        }

        executor.shutdown();
        System.out.println("Pairwise Sum (ExecutorService): " + pairwiseSum);
    }
}
