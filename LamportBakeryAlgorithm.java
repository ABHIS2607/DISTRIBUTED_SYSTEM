import java.util.Scanner;

class AtomicInt {
    private int value;

    public AtomicInt(int initialValue) {
        this.value = initialValue;
    }

    public synchronized int fetchAdd(int increment) {
        int oldValue = value;
        value += increment;
        return oldValue;
    }

    public synchronized int load() {
        return value;
    }
}

class Mutex {
    private int state;

    public Mutex() {
        this.state = 0;
    }

    public synchronized void lock() {
        while (state != 0) {
            // Spin until the lock is acquired
        }
        state = 1;
    }

    public synchronized void unlock() {
        state = 0;
    }
}

public class LamportBakeryAlgorithm {
    private static final Scanner scanner = new Scanner(System.in);

    private static AtomicInt ticketCounter;
    private static int[] ticket;
    private static boolean[] choosing;
    private static Mutex coutMutex;

    private static void initialize(int numThreads) {
        ticketCounter = new AtomicInt(0);
        ticket = new int[numThreads];
        choosing = new boolean[numThreads];
        coutMutex = new Mutex();
    }

    private static void lock(int threadId) {
        synchronized (coutMutex) {
            choosing[threadId] = true;
            ticket[threadId] = ticketCounter.fetchAdd(1);
            choosing[threadId] = false;
        }

        for (int i = 0; i < ticket.length; ++i) {
            while (true) {
                synchronized (coutMutex) {
                    if (!choosing[i]) {
                        break;
                    }
                }
            }

            while (true) {
                synchronized (coutMutex) {
                    if (!(ticket[i] != 0
                            && (ticket[i] < ticket[threadId] || (ticket[i] == ticket[threadId] && i < threadId)))) {
                        break;
                    }
                }
            }
        }
    }

    private static void unlock(int threadId) {
        synchronized (coutMutex) {
            ticket[threadId] = 0;
        }
    }

    private static void criticalSection(int threadId) {
        synchronized (coutMutex) {
            System.out.println("Thread " + threadId + " enters the critical section.");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + threadId + " exits the critical section.");
        }

    }

    private static void threadFunction(int threadId) {
        for (int i = 0; i < 3; ++i) {
            lock(threadId);
            criticalSection(threadId);
            unlock(threadId);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.print("Number of threads: ");
        int numThreads = scanner.nextInt();

        initialize(numThreads);

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; ++i) {
            final int threadId = i;
            threads[i] = new Thread(() -> threadFunction(threadId));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}
