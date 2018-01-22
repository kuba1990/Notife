import com.oracle.jrockit.jfr.Producer;
import sun.awt.windows.ThemeReader;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class Main {

    //pass this params in String[] args
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/sys?"
            + "user=root&password=eagles123";

    //pass this param in String[] args
    private static String path = "/home/jwisniowski/Desktop/Notify";
    static String abc;

    public static void main(String[] args) throws Exception {

        ///Part I
        /*NotifyFolder notifyFolder = new NotifyFolder();
        notifyFolder.notify(path);
        MySQLJava dao = new MySQLJava(MYSQL_DRIVER, MYSQL_URL);//run database
        MySQLJava rda = dao;

        ArrayBlockingQueue queue = new ArrayBlockingQueue(10);

        queue.add(notifyFolder);
        queue.add(rda);*/

        //Part II

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }

private static void producer() throws InterruptedException {

        NotifyFolder notifyFolder = new NotifyFolder();
        notifyFolder.notify(path);//radar path
        }

private static void consumer() throws InterruptedException {
        MySQLJava dao = new MySQLJava(MYSQL_DRIVER, MYSQL_URL);//run database

        }
}

