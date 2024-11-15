import java.util.concurrent.RecursiveTask;

public class WorkStealingSum extends RecursiveTask<Integer> {
    private final int[] array;
    private final int startIndex, endIndex;
    private final int forkThreshold;

    WorkStealingSum(int[] array, int startIndex, int endIndex, int forkThreshold) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.forkThreshold = forkThreshold;
    }
    WorkStealingSum(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.forkThreshold = 10_000;
    }

    @Override
    protected Integer compute() {
        final int workLength = endIndex - startIndex;
        if (workLength <= forkThreshold) {
            int sum = workLength % 2 == 0 ? 0 : array[endIndex-1];
            for (int i = startIndex; i < (endIndex - 1); i += 2) {
                sum += array[i] + array[i + 1];
            }
            return sum;
        }
        else {
            int splitPoint = (startIndex + endIndex) / 2;
            WorkStealingSum leftTask = new WorkStealingSum(array, startIndex, splitPoint);
            WorkStealingSum rightTask = new WorkStealingSum(array, splitPoint, endIndex);
            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }
}