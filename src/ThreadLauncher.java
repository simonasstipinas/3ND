public class ThreadLauncher implements Runnable {
    private int[] numbers;
    private int threads;

    public ThreadLauncher(int[] numbers, int threads) {
        this.numbers = numbers;
        this.threads = threads;
    }

    public void run() {
        MergeSort.threadedMergeSort(numbers, threads);
    }
}
