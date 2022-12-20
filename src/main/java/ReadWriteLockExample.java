import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class CalendarUser extends Thread {
    public static String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static int today = 0;
    private static final ReadWriteLock marker = new ReentrantReadWriteLock();
    private static final Lock readerLock = marker.readLock();
    private static final Lock writerLock = marker.writeLock();

    public CalendarUser(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("\tCalendarUser.run " + getName() + " start");
        while(today<days.length-1) {
            if(this.getName().contains("Writer")) {
                writerLock.lock();
                today = (today+1)%7;
                System.out.println("\tCalendarUser.run " + this.getName() + " updated today to "+days[today]);
                writerLock.unlock();
            }
            else {
                readerLock.lock();
                System.out.println("\tCalendarUser.run " + getName() + " reads today as " + days[today]);
                readerLock.unlock();
            }
        }
        System.out.println("\tCalendarUser.run " + getName() + " end");
    }
}

public class ReadWriteLockExample {
    public static void main(String[] args) {
        System.out.println("ReadWriteLockExample.main started");

        for(int i=0; i<10; i++)
            new CalendarUser("Reader-"+i).start();
        System.out.println("ReadWriteLockExample.main started 10 reader threads");

        for(int i=0; i<2; i++)
            new CalendarUser("Writer-"+i).start();
        System.out.println("ReadWriteLockExample.main started 2 writer Threads");

        System.out.println("ReadWriteLockExample.main done");
    }
}
