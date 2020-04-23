import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MergeSort {

    public static String MODE = "EXECUTE";

    private static final Random RAND = new Random(42);

    public static void main(String[] args) throws Throwable {
        String length = args.length != 0 ? args[0] : null;
        int LENGTH = length == null ? 1000 : Integer.parseInt(length);
        System.out.println(LENGTH);
        String cores = args.length != 0 ? args[1] : null;
        int CORES = cores == null ? 8 : Integer.parseInt(cores);
        Scanner scan = new Scanner(System.in);
        System.out.println("Show or execute 1/0? ");
        int num = scan.nextInt();
        if (num == 1) {
            MODE = "SHOW";
            int[] numbers = createRandomArray(LENGTH);
            long start = System.currentTimeMillis();
            threadedMergeSort(numbers, CORES);
            long end = System.currentTimeMillis();

            if (! sorted(numbers)) {
                throw new RuntimeException("Somethings Wong");
            } else {
                print(numbers);
            }
            long difference = end - start;
            System.out.println("|LENGTH: " + LENGTH +
                    "| TIME: " + difference + "|");
        } else {
            MODE = "EXECUTE";
                int[] numbers = createRandomArray(LENGTH);

                long start = System.currentTimeMillis();
                threadedMergeSort(numbers, CORES);
                long end = System.currentTimeMillis();

                if (!sorted(numbers)) {
                    throw new RuntimeException("Somethings Wrong");
                }
                long difference = end - start;
                System.out.println("|LENGTH: " + LENGTH +
                        "| CORES: " + CORES +
                        "| TIME: " + difference + "|");
        }
    }

    private static void print(int[] numbers) {
        String array = "";
        for (int i = 0; i < numbers.length; i++) {
            array = array + " " + numbers[i];
        }
        System.out.println(array);
    }

    public static void threadedMergeSort(int[] numbers, int cores) {
        if (cores <= 1) {
            mergeSort(numbers);
        } else if (numbers.length >= 2) {
            int[] left = Arrays.copyOfRange(numbers, 0, numbers.length / 2);
            int[] right = Arrays.copyOfRange(numbers, numbers.length / 2, numbers.length);

            Thread leftSide = new Thread(new ThreadLauncher(left, cores / 2));
            Thread rightSide = new Thread(new ThreadLauncher(right, cores / 2));
            leftSide.start();
            rightSide.start();

            try {
                leftSide.join();
                rightSide.join();
            } catch (InterruptedException ie) {

            }
            merge(left, right, numbers);
        }
    }

    public static void mergeSort(int[] numbers) {
        if (numbers.length >= 2) {
            int[] left = Arrays.copyOfRange(numbers, 0, numbers.length / 2);
            int[] right = Arrays.copyOfRange(numbers, numbers.length / 2, numbers.length);

            mergeSort(left);
            mergeSort(right);

            merge(left, right, numbers);
        }
    }

    public static void merge(int[] left, int[] right, int[] numbers) {
        if (MODE.equalsIgnoreCase("SHOW")) {
            System.out.println("Merging mergeSort(int[] numbers): ");
            print(left);
            print(right);
        }
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (index2 >= right.length || (index1 < left.length && left[index1] < right[index2])) {
                numbers[i] = left[index1];
                index1++;
            } else {
                numbers[i] = right[index2];
                index2++;
            }
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
            numbers[i] = RAND.nextInt(1000000);
        }
        return numbers;
    }
}