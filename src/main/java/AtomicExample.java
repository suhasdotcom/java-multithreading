import java.util.concurrent.atomic.AtomicInteger;

class ResourceContainer2 extends Thread {
    public static AtomicInteger resourceCount = new AtomicInteger(0);
    private final int incrementResource;

    public ResourceContainer2(int incrementResource) {
        this.incrementResource = incrementResource;
    }

    @Override
    public void run() {
        System.out.println("\tResourceContainer.run start");
        for(int i=0; i<incrementResource; i++){
            resourceCount.incrementAndGet();
        }
        System.out.println("\tResourceContainer.run end");
    }
}

public class AtomicExample {
    public static final int INCREMENT_RESOURCE = 10_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("DataRaceExample.main start");

        Thread firstUpdater = new ResourceContainer2(INCREMENT_RESOURCE);
        System.out.println("DataRaceExample.main created first updater");
        Thread secondUpdater = new ResourceContainer2(INCREMENT_RESOURCE);
        System.out.println("DataRaceExample.main created second updater");

        firstUpdater.start();
        secondUpdater.start();
        firstUpdater.join();
        secondUpdater.join();
        System.out.println("DataRaceExample.main final resourceCount = " + ResourceContainer2.resourceCount);  // should output INCREMENT_RESOURCE*2
    }
}
