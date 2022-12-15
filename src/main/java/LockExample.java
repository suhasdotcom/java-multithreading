import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ResourceContainer extends Thread {
    public static int resourceCount = 0;
    private final int incrementResource;
    static Lock modifier = new ReentrantLock();

    public ResourceContainer(int incrementResource) {
        this.incrementResource = incrementResource;
    }

    @Override
    public void run() {
        System.out.println("\tResourceContainer.run start");
        for(int i=0; i<incrementResource; i++){
            modifier.lock();
            resourceCount++;
            modifier.unlock();
        }
        System.out.println("\tResourceContainer.run end");
    }
}

public class LockExample {
    public static final int INCREMENT_RESOURCE = 10_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("DataRaceExample.main start");

        Thread firstUpdater = new ResourceContainer(INCREMENT_RESOURCE);
        System.out.println("DataRaceExample.main created first updater");
        Thread secondUpdater = new ResourceContainer(INCREMENT_RESOURCE);
        System.out.println("DataRaceExample.main created second updater");

        firstUpdater.start();
        secondUpdater.start();
        firstUpdater.join();
        secondUpdater.join();
        System.out.println("DataRaceExample.main final resourceCount = " + ResourceContainer.resourceCount);  // should output INCREMENT_RESOURCE*2
    }
}
