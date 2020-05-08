public class ThreadLauncher implements Runnable {
    private int[] numbers;
    private int threads;
    private int from;
    private int to;

    public ThreadLauncher(int[] numbers, int threads, int from, int to) {
        this.numbers = numbers;
        this.threads = threads;
        this.from = from;
        this.to = to;
    }

    public void run() {
        MergeSort.threadedMergeSort(numbers, threads, from, to);
    }
}
