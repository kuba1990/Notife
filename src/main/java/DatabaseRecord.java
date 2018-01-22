import java.io.File;
import java.util.concurrent.BlockingQueue;

public class DatabaseRecord {

    MySQLJava dao = new MySQLJava(Main.MYSQL_DRIVER, Main.MYSQL_URL);


    public void databaseCrud(String action, String fileName, String fileContent) {
        try {
            dao.readData(action,fileName,fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
