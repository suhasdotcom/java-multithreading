import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher extends Thread {
    private static int sushiOnPlate = 10_000;
    private final Lock firstLock;
    private final Lock secondLock;

    public Philosopher(String name, Lock firstLock, Lock secondLock) {
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

            sushiOnPlate--;
            System.out.println("\tPhilosopher.run " + getName() + " acquired lock and is eating sushi now, sushi left: " + sushiOnPlate);

            secondLock.unlock();
            firstLock.unlock();
        }
        System.out.println("Philosopher.run " + getName() + " ended");
    }
}
public class DeadLockExample {
    public static void main(String[] args) {
        System.out.println("DeadLockExample.main starts");
        Lock chopstickA = new ReentrantLock();
        Lock chopstickB = new ReentrantLock();
        Lock chopstickC = new ReentrantLock();

        new Philosopher("Suhas", chopstickA, chopstickB).start();
        new Philosopher("Plato", chopstickB, chopstickC).start();
//        new Philosopher("Aristotle", chopstickC, chopstickA).start();   // DeadLock here as monitor C has lower priority than A
        new Philosopher("Aristotle", chopstickA, chopstickC).start();
        System.out.println("DeadLockExample.main ends");
    }
}
