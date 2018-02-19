import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

public class DatabaseConsumerThread extends Thread {
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/sys?"
            + "user=root&password=eagles123";
    BlockingQueue<QueueNotify> sharedQueue;
    private DatabaseService db = new DatabaseService(MYSQL_DRIVER, MYSQL_URL);

    public DatabaseConsumerThread(BlockingQueue<QueueNotify> sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    public void run() {
        try (Connection con = db.connect()) {
            PreparedStatement preparedStatement = db.prepareStatement(con);
            while (true) {
                QueueNotify actionFileContent = sharedQueue.take();
                preparedStatement.setString(1, actionFileContent.getAction());
                preparedStatement.setString(2, actionFileContent.getName());
                preparedStatement.setString(3, actionFileContent.getContent());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException  | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}