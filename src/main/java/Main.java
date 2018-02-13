import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static String passwordToDatabase = null;
    public static String dirNotify;

    public static void main(String[] args) throws Exception {

        PropertiesProgram propertiesProgram = new PropertiesProgram();

        if (args.length == 0) {
            System.out.println("Proper Usage is: path dir");
        }
       dirNotify = propertiesProgram.setPath(args[0]);


        BlockingQueue<QueueNotify> sharedQueue = new ArrayBlockingQueue<>(99);

        Thread scanFolderThread = new ScanFolderThread(sharedQueue,dirNotify);
        scanFolderThread.start();

        Thread databaseConsumer = new DatabaseConsumerThread(sharedQueue,passwordToDatabase);
        databaseConsumer.start();
        }
}