public class DataRaceExample {
    public static int resourceCount = 0;
    public static final int INCREMENT_RESOURCE = 10_000;

    public void incrementResource() {
        System.out.println("\tDataRaceExample.incrementResource start");
        for(int i=0; i<INCREMENT_RESOURCE; i++){
            resourceCount++;
        }
        System.out.println("\tDataRaceExample.incrementResource end");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("DataRaceExample.main start");
        DataRaceExample dataRaceExample = new DataRaceExample();

        Thread firstUpdater = new Thread(dataRaceExample::incrementResource);
        System.out.println("DataRaceExample.main created first updater");
        Thread secondUpdater = new Thread(dataRaceExample::incrementResource);
        System.out.println("DataRaceExample.main created second updater");

        firstUpdater.start();
        secondUpdater.start();
        firstUpdater.join();
        secondUpdater.join();
        System.out.println("DataRaceExample.main final resourceCount = " + resourceCount);  // should output INCREMENT_RESOURCE*2
    }
}
