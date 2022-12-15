import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class EntryMaker extends Thread{
    public static int registeredItems = 0;
    public static Lock registrar = new ReentrantLock();
    public int itemsToAdd = 0;

    public EntryMaker(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("\tEntryMaker.run starts");
        while(registeredItems<20) {
            if(itemsToAdd>0 && registrar.tryLock()) {
                try {
                    registeredItems += itemsToAdd;
                    itemsToAdd = 0;
                    System.out.println("\tEntryMaker.run got lock!!! registeredItems = " + registeredItems + " Thread: "+Thread.currentThread().getName());
                    Thread.sleep(300);  // time spent writing
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    registrar.unlock();
                }
            }
            else {
                try {
                    itemsToAdd++;
                    System.out.println("\tEntryMaker.run Lock not found! itemsToAdd = " + itemsToAdd + " for Thread: "+Thread.currentThread().getName());
                    Thread.sleep(100);  // time spent searching
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("\tEntryMaker.run end");
    }
}
public class TryLockExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("TryLockExample.main started");
        Thread first = new EntryMaker("first");
        Thread second = new EntryMaker("second");

        first.start();
        second.start();
        first.join();
        second.join();

        System.out.println("TryLockExample.main end registeredItems = " + EntryMaker.registeredItems);
    }
}
