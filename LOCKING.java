import java.util.Scanner;

class Lock {
    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}

class SharedResource {
    private int count = 0;
    private Lock lock = new Lock();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            // Introduce some delay to increase the likelihood of contention
            Thread.sleep(100);

            count++;
            System.out.println("Thread " + Thread.currentThread().getId() + " incremented the count to: " + count);
        } finally {
            lock.unlock();
        }
    }
}

public class LOCKING {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Number of threads: ");
        int numThreads = scanner.nextInt();

        System.out.print("Increment count: ");
        int incrementCount = scanner.nextInt();

        SharedResource sharedResource = new SharedResource();

        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    for (int j = 0; j < incrementCount; j++) {
                        sharedResource.increment();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            thread.start();
        }

        scanner.close();
    }
}
