public class CPUWaster {
    public static final int THREAD_COUNT = 6;
    public static class ContinuousRunner extends Thread {
        @Override
        public void run() {
            for(;;){}   // run forever
        }
    }

    public static void main(String[] args) {
        System.out.println("CPUWaster.main started");
        Runtime runtime = Runtime.getRuntime();
        System.out.println("CPUWaster.main ProcessId: "+ProcessHandle.current().pid());
        System.out.println("CPUWaster.main THREAD_COUNT = " + THREAD_COUNT);
        System.out.println("CPUWaster.main runtime.availableProcessors() = " + runtime.availableProcessors());
        System.out.println("CPUWaster.main runtime.totalMemory() = " + runtime.totalMemory());
        System.out.println("CPUWaster.main runtime.freeMemory() = " + runtime.freeMemory());
        for(int i=0; i<THREAD_COUNT; i++) {
            System.out.println("starting thread i = " + i);
            new ContinuousRunner().start();
        }
        System.out.println("CPUWaster.main runtime.availableProcessors() = " + runtime.availableProcessors());
        System.out.println("CPUWaster.main runtime.totalMemory() = " + runtime.totalMemory());
        System.out.println("CPUWaster.main runtime.freeMemory() = " + runtime.freeMemory());
        System.out.println("CPUWaster.main done");
    }
}