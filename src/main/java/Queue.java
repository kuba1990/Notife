import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Queue {

    public BlockingQueue<QueueNotify> sharedQueue = new ArrayBlockingQueue<>(99);

}