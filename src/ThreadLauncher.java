public class ThreadLauncher implements Runnable {
    private int[] numbers;
    private int threads;
    private int from;
    private int to;
    private int maxValue;

    public ThreadLauncher(int[] numbers, int threads, int from, int to, int maxValue) {
        this.numbers = numbers;
        this.threads = threads;
        this.from = from;
        this.to = to;
        this.maxValue = maxValue;
    }

    public void run() {
        MergeSort.threadedMergeSort(numbers, threads, from, to, maxValue);
    }
}
