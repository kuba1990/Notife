import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static String passwordToDatabase = null;
    public static String dirNotify;

    public static void main(String[] args) throws Exception {

        BlockingQueue<QueueNotify> sharedQueue = new ArrayBlockingQueue<>(99);

        Thread scanFolderThread = new ScanFolderThread(sharedQueue,dirNotify);
        scanFolderThread.start();

        Thread databaseConsumer = new DatabaseConsumerThread(sharedQueue,passwordToDatabase);
        databaseConsumer.start();

        }

    private static void setupProgram(String[] args) {
        while (args.length == 0)
        {
            System.out.println("Proper Usage is: Database password and path dir");
            System.exit(0);
        }
        passwordToDatabase =  args[0];
        dirNotify=  args[1];

    }
}