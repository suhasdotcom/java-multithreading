import java.util.concurrent.locks.ReentrantLock;

class ReentrantResourceContainer extends Thread {
    public static int resourceCount = 0;
    private final int incrementResource;
    private int counter = 0;
    private static final ReentrantLock modifier = new ReentrantLock();

    public ReentrantResourceContainer(int incrementResource) {
        this.incrementResource = incrementResource;
    }

    @Override
    public void run() {
        if(counter>=incrementResource)
            return;
        System.out.println("\tReentrantResourceContainer.run start");
        modifier.lock();
        System.out.println("modifier hold-count = " + modifier.getHoldCount());
        resourceCount++;
        counter++;
        run();
        modifier.unlock();
        System.out.println("\tReentrantResourceContainer.run end");
    }
}

public class ReentrantLockExample {
    public static final int INCREMENT_RESOURCE = 1_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("ReentrantLockExample.main start");

        Thread firstUpdater = new ReentrantResourceContainer(INCREMENT_RESOURCE);
        System.out.println("ReentrantLockExample.main created first updater");
        Thread secondUpdater = new ReentrantResourceContainer(INCREMENT_RESOURCE);
        System.out.println("ReentrantLockExample.main created second updater");

        firstUpdater.start();
        secondUpdater.start();
        firstUpdater.join();
        secondUpdater.join();
        System.out.println("ReentrantLockExample.main final resourceCount = " + ReentrantResourceContainer.resourceCount);  // should output INCREMENT_RESOURCE*2
    }
}
