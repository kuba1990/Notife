import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.BlockingQueue;

public class DatabaseConsumerThread extends Thread {

    //TODO
    private final String dbPassword;
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/sys?"
            + "user=root&password=eagles123"; //dbpassword

    private MySQLJava db = new MySQLJava(MYSQL_DRIVER, MYSQL_URL);

    BlockingQueue<QueueNotify> sharedQueue;

    public  DatabaseConsumerThread(BlockingQueue<QueueNotify> sharedQueue, String dbPassword) {
        this.dbPassword = dbPassword;
        this.sharedQueue= sharedQueue;
    }

    public void run() {
        while (true) {
            //Wstrzymywanie i przerywanie wątku
            QueueNotify actionFileContent = null;
            try {
                actionFileContent = sharedQueue.take();
                //
                //if(actionFileContent.getAction().isEmpty());
                //System.exit(0);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Connection con = null;
            PreparedStatement ps = null;
            try {
                con = db.connect();
                ps = db.writeData(con, actionFileContent);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close(con, ps);
            }
        }
    }

}