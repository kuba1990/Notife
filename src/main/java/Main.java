import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) throws Exception {

        Thread scanFolderThread = new ScanFolderThread();
        scanFolderThread.start();

        Thread databaseConsumer = new DatabaseConsumerThread();
        databaseConsumer.start();

        }
}