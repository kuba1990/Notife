import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Scanner;

public class Main {

    public static String passwordToDatabase;
    public static String dirNotify;

    public static void main(String[] args) throws Exception {

        //setupProgram(args);

        Thread scanFolderThread = new ScanFolderThread();
        scanFolderThread.start();

        Thread databaseConsumer = new DatabaseConsumerThread();
        databaseConsumer.start();

        }

    private static void setupProgram(String[] args) {
        System.out.println("please enter password to database");
        passwordToDatabase =  args[0];
        System.out.println("please enter direction");
        dirNotify=  args[1];
    }
}