import com.oracle.jrockit.jfr.Producer;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import static com.sun.activation.registries.LogSupport.log;

public class Main {
    
    //pass this params in String[] args
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/sys?"
            + "user=root&password=eagles123";

    //pass this param in String[] args
    private static String path = "/home/jwisniowski/Desktop/Notify";

    private BlockingQueue<String> sharedQueue = new ArrayBlockingQueue<>(3);

    public static void main(String[] args) throws Exception {

        BlockingQueue<File> queue = new ArrayBlockingQueue<>(100);

        NotifyFolder notifyFolder = new NotifyFolder();
        NotifyFolder notifyFolderBeforeStart = new NotifyFolder();

        MySQLJava dao = new MySQLJava(MYSQL_DRIVER, MYSQL_URL);//run database

        String startingContent = notifyFolderBeforeStart.notifyContent(path).toString();
        log("Starting contnet: " + startingContent);

        notifyFolder.notify(path);//radar path
    }
}
