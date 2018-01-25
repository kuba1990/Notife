import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Queue {

    public static BlockingQueue<QueueNotify> sharedQueue = new ArrayBlockingQueue<>(99);

}
