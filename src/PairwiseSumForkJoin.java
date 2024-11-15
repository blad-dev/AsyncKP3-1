import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class PairwiseSumForkJoin {
    static class PairwiseSumTask extends RecursiveTask<Integer> {
        private final int[] array;
        private final int start, end;
        private static final int THRESHOLD = 1000;

        PairwiseSumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                int sum = 0;
                for (int i = start; i < end - 1; i++) {
                    sum += array[i] + array[i + 1];
                }
                return sum;
            } else {
                int mid = (start + end) / 2;
                PairwiseSumTask leftTask = new PairwiseSumTask(array, start, mid);
                PairwiseSumTask rightTask = new PairwiseSumTask(array, mid, end);
                leftTask.fork();
                int rightResult = rightTask.compute();
                int leftResult = leftTask.join();
                return leftResult + rightResult;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[100000]; // Large array
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        int pairwiseSum;
        try (ForkJoinPool pool = new ForkJoinPool()) {
            PairwiseSumTask task = new PairwiseSumTask(array, 0, array.length);
            pairwiseSum = pool.invoke(task);
        }

        System.out.println("Pairwise Sum (ForkJoin): " + pairwiseSum);
    }
}
