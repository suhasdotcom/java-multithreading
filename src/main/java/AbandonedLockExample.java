import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PhilosopherAbandoner extends Thread {
    private static int sushiOnPlate = 10_000;
    private final Lock firstLock;
    private final Lock secondLock;

    public PhilosopherAbandoner(String name, Lock firstLock, Lock secondLock) {
        super(name);
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        System.out.println("\tPhilosopher.run " + getName() + " started");
        while (sushiOnPlate>0) {
            firstLock.lock();
            secondLock.lock();

            try {
                sushiOnPlate--;
                System.out.println("\tPhilosopher.run " + getName() + " acquired lock and is eating sushi now, sushi left: " + sushiOnPlate);

                if (sushiOnPlate == 10)
                    System.out.println(1 / 0);
            }
            finally {
                secondLock.unlock();
                firstLock.unlock();
            }
        }
        System.out.println("Philosopher.run " + getName() + " ended");
    }
}
public class AbandonedLockExample {
    public static void main(String[] args) {
        System.out.println("DeadLockExample.main starts");
        Lock chopstickA = new ReentrantLock();
        Lock chopstickB = new ReentrantLock();
        Lock chopstickC = new ReentrantLock();

        new PhilosopherAbandoner("Suhas", chopstickA, chopstickB).start();
        new PhilosopherAbandoner("Plato", chopstickB, chopstickC).start();
//        new Philosopher("Aristotle", chopstickC, chopstickA).start();   // DeadLock here as monitor C has lower priority than A
        new PhilosopherAbandoner("Aristotle", chopstickA, chopstickC).start();
        System.out.println("DeadLockExample.main ends");
    }
}
