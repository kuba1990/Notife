import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static String dirNotify;
    public static void main(String[] args) throws Exception {
        PropertyProgram propertyProgram = new PropertyProgram();

        if (args.length == 0) {
            System.out.println("Proper Usage is: path dir");
        }
        dirNotify = propertyProgram.setPath(args[0]);

        BlockingQueue<QueueNotify> sharedQueue = new ArrayBlockingQueue<>(99);

        Thread scanFolderThread = new ScanFolderThread(sharedQueue, dirNotify);
        scanFolderThread.start();

        Thread databaseConsumer = new DatabaseConsumerThread(sharedQueue);
        databaseConsumer.start();
    }
}