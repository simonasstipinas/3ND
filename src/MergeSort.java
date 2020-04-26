import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MergeSort {

    public static String MODE = "EXECUTE";

    private static final Random RAND = new Random(42);

    public static void main(String[] args) throws Throwable {
        String length = args.length != 0 ? args[0] : null;
        int LENGTH = length == null ? 10000000 : Integer.parseInt(length);
        System.out.println(LENGTH);
        String cores = args.length != 0 ? args[1] : null;
        int CORES = cores == null ? 8 : Integer.parseInt(cores);
        Scanner scan = new Scanner(System.in);
        System.out.println("Show or execute 1/0? ");
        int num = scan.nextInt();
        int[] numbers = createRandomArray(LENGTH, 10);
        int maxValue = Arrays.stream(numbers).max().getAsInt() + 1;
        if (num == 1) {
            MODE = "SHOW";
            long start = System.currentTimeMillis();
            threadedMergeSort(numbers, CORES, 0, numbers.length - 1, maxValue);
            long end = System.currentTimeMillis();

            if (! sorted(numbers)) {
                throw new RuntimeException("Somethings Wong");
            } else {
                print(numbers, 0, numbers.length);
            }
            long difference = end - start;
            System.out.println("|LENGTH: " + LENGTH +
                    "| TIME: " + difference + "|");
        } else {
            MODE = "EXECUTE";
int bound = 0;
            for (int k = 0; k < 3; k++) {
                if (k == 0) LENGTH = 1000000;
                if (k == 1) LENGTH = 5000000;
                if (k == 2) LENGTH = 10000000;
                numbers = createRandomArray(LENGTH, 10000);
                maxValue = Arrays.stream(numbers).max().getAsInt() + 1;

            for (int i = 0; i < 10; i++) {
                long start = System.currentTimeMillis();
                threadedMergeSort(numbers, i, 0, numbers.length - 1, maxValue);
                long end = System.currentTimeMillis();

                if (!sorted(numbers)) {
                    throw new RuntimeException("Somethings Wrong");
                }
                long difference = end - start;
                System.out.println("|LENGTH: " + LENGTH +
                        "| CORES: " + (i) +
                        "| TIME: " + difference + "|" + "BOUND |" + bound + "|");
            }
            }
        }
    }

    private static void print(int[] numbers, int from, int to) {
        String array = "";
        for (int i = from; i < to; i++) {
            array = array + " " + numbers[i];
        }
        System.out.println(array);
    }

    public static void threadedMergeSort(int[] numbers, int cores, int from, int to, int maxValue) {
        if (cores <= 1) {
            mergeSort(numbers, from, to, maxValue);
        } else if (from < to) {
            int middle = (from + to) / 2;
            Thread leftSide = new Thread(new ThreadLauncher(numbers, cores / 2, from, middle, maxValue));
            Thread rightSide = new Thread(new ThreadLauncher(numbers, cores / 2, middle + 1, to, maxValue));
            leftSide.start();
            rightSide.start();
            try {
                leftSide.join();
                rightSide.join();
            } catch (InterruptedException ie) {

            }
            merge(middle, from, to, numbers, maxValue);
        }
    }

    public static void mergeSort(int[] numbers, int from, int to, int maxValue) {
        if (from < to) {
            int middle = (from + to) / 2;
            mergeSort(numbers, from, middle, maxValue);
            mergeSort(numbers, middle + 1, to, maxValue);

            merge(middle, from, to, numbers, maxValue);
        }
    }

    public static void merge(int middle, int from, int to, int[] numbers, int maxValue) {
        if (MODE.equalsIgnoreCase("SHOW")) {
            System.out.println("Merging: ");
            print(numbers, from, middle);
            print(numbers, middle, to);
        }

        int i = from;
        int j = middle + 1;
        int k = from;
        while (i <= middle && j <= to) {
            if (numbers[i] % maxValue <=
                    numbers[j] % maxValue) {
                numbers[k] = numbers[k] + (numbers[i]
                        % maxValue) * maxValue;
                k++;
                i++;
            } else {
                numbers[k] = numbers[k] +
                        (numbers[j] % maxValue)
                                * maxValue;
                k++;
                j++;
            }
        }
        while (i <= middle) {
            numbers[k] = numbers[k] + (numbers[i]
                    % maxValue) * maxValue;
            k++;
            i++;
        }
        while (j <= to) {
            numbers[k] = numbers[k] + (numbers[j]
                    % maxValue) * maxValue;
            k++;
            j++;
        }

        for (i = from; i <= to; i++) {
            numbers[i] = numbers[i] / maxValue;
        }
    }

    public static boolean sorted(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static int[] createRandomArray(int length, int bound) {
        int[] numbers = new int[length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = RAND.nextInt(bound);
        }
        return numbers;
    }
}