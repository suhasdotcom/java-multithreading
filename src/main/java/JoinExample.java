public class JoinExample {
    public static boolean ENABLE_JOIN = true;
    public static void main(String[] args) throws InterruptedException {
        Thread other = new Thread(() -> {
            System.out.println("\tother thread started and waiting for 3 seconds");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\tother thread resumes");
            System.out.println("\tother thread done");
        });

        System.out.println("JoinExample.main started");
        System.out.println("JoinExample.main staring other thread and sleeping for 0.5 seconds");
        other.start();
        Thread.sleep(500);
        if (ENABLE_JOIN) {
            other.join();
            System.out.println("JoinExample.main resumed and done after 3 seconds");
        }
        else
            System.out.println("JoinExample.main resumed and done after 0.5 seconds");
    }
}
