import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    private static int genRandomRange(int range){
        return (int)(Math.random()*range);
    }
    private static int randomInRangeInclusive(int min, int max){
        return genRandomRange(max - min + 1) + min;
    }

    private static int[] genRandomArray(int size, int min, int max){
        int[] array = new int[size];
        for(int i = 0; i < array.length; ++i){
            array[i] = randomInRangeInclusive(min, max);
        }
        return array;
    }
    public static void main(String[] args)  {
        int size, min, max;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть розмір масиву: ");
        size = scanner.nextInt();
        if(size < 1){
            System.out.println("Розмір не може бути менше за 1!");
            return;
        }
        System.out.print("Введіть мінімальне значення масиву: ");
        min = scanner.nextInt();
        System.out.print("Введіть максимальне значення масиву: ");
        max = scanner.nextInt();
        if(min >= max){
            System.out.println("Максимальне значення повинно бути більше за мінімальне!");
            return;
        }
        int[] array = genRandomArray(size, min, max);
        System.out.println("Згенерований масив: ");
        for (int j : array) {
            System.out.print(j + " ");
        }
        System.out.println();
        Timer<Integer> timer = new Timer<>();
        try{
            System.out.println(timer.stopTimer("findSumUsingWorkDealing", findSumUsingWorkDealing(array)));
            timer.startCounting();
            System.out.println(timer.stopTimer("findSumUsingWorkStealing", findSumUsingWorkStealing(array)));
        }
        catch (Exception exception){
            System.out.println("Відбулася помилка: ");
            exception.printStackTrace();
        }
    }

    private static int findSumUsingWorkStealing(int[] array){
        int pairwiseSum;
        try (ForkJoinPool pool = new ForkJoinPool()) {
            WorkStealingSum task = new WorkStealingSum(array, 0, array.length);
            pairwiseSum = pool.invoke(task);
        }
        return pairwiseSum;
    }
    private static int findSumUsingWorkDealing(int[] array) throws ExecutionException, InterruptedException {
        return WorkDealingSum.sum(array);
    }
}
