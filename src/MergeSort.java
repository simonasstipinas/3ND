import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MergeSort {

    public static String MODE = "EXECUTE";

    private static final Random RAND = new Random(42);

    public static void main(String[] args) throws Throwable {
        String length = args.length != 0 ? args[0] : null;
        int LENGTH = length == null ? 100000000 : Integer.parseInt(length);
        System.out.println(LENGTH);
        String cores = args.length != 0 ? args[1] : null;
        int CORES = cores == null ? 8 : Integer.parseInt(cores);
        Scanner scan = new Scanner(System.in);
        System.out.println("Show or execute 1/0? ");
        int num = scan.nextInt();
        int[] numbers = createRandomArray(LENGTH);
        if (num == 1) {
            MODE = "SHOW";
            long start = System.currentTimeMillis();
            threadedMergeSort(numbers, CORES, 0, numbers.length - 1);
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
            for (int i = 0; i < 9; i++) {
                long start = System.currentTimeMillis();
                threadedMergeSort(numbers, i, 0, numbers.length - 1);
                long end = System.currentTimeMillis();

                if (! sorted(numbers)) {
                    throw new RuntimeException("Somethings Wrong");
                }
                long difference = end - start;
                System.out.println("|LENGTH: " + LENGTH +
                        "| CORES: " + i +
                        "| TIME: " + difference + "|");
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

    public static void threadedMergeSort(int[] numbers, int cores, int from, int to) {
        if (cores <= 1) {
            mergeSort(numbers, from, to);
        } else if (from < to) {
            int middle = (from + to) / 2;
            Thread leftSide = new Thread(new ThreadLauncher(numbers, cores / 2, from, middle));
            Thread rightSide = new Thread(new ThreadLauncher(numbers, cores / 2, middle + 1, to));
            leftSide.start();
            rightSide.start();
            try {
                leftSide.join();
                rightSide.join();
            } catch (InterruptedException ie) {

            }
            merge(middle, from, to, numbers);
        }
    }

    public static void mergeSort(int[] numbers, int from, int to) {
        if (from < to) {
            int middle = (from + to) / 2;
            mergeSort(numbers, from, middle);
            mergeSort(numbers, middle + 1, to);

            merge(middle, from, to, numbers);
        }
    }

    public static void merge(int middle, int from, int to, int[] numbers) {
        if (MODE.equalsIgnoreCase("SHOW")) {
            System.out.println("Merging: ");
            print(numbers, from, middle);
            print(numbers, middle, to);
        }

        int n1 = middle - from + 1;
        int n2 = to - middle;

        int left[] = new int[n1];
        int right[] = new int[n2];

        for (int i = 0; i < n1; ++ i)
            left[i] = numbers[from + i];
        for (int j = 0; j < n2; ++ j)
            right[j] = numbers[middle + 1 + j];


        int i = 0, j = 0;

        int k = from;
        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                numbers[k] = left[i];
                i++;
            } else {
                numbers[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            numbers[k] = left[i];
            i++;
            k++;
        }

        while (j < n2) {
            numbers[k] = right[j];
            j++;
            k++;
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

    public static int[] createRandomArray(int length) {
        int[] numbers = new int[length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = RAND.nextInt(1000);
        }
        return numbers;
    }
}