import java.sql.Connection;
import java.sql.PreparedStatement;

public class DatabaseConsumerThread extends Thread {
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/sys?"
            + "user=root&password=eagles123";

    private MySQLJava db = new MySQLJava(MYSQL_DRIVER, MYSQL_URL);

    public void run() {

        while(true){
            QueueNotify actionFileContent = null;
            try {
                actionFileContent=Queue.sharedQueue.take();
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
            }finally {

                db.close(con, ps);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
