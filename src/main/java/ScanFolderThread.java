import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import static com.sun.activation.registries.LogSupport.log;
import static java.nio.file.StandardWatchEventKinds.*;

public class ScanFolderThread extends Thread {
    private static Logger LOGGER = Logger.getLogger("InfoLogging");
    BlockingQueue<QueueNotify> sharedQueue;
    String dirNotify;
    private ContentReader contentChanges = new ContentReader();

    public ScanFolderThread(BlockingQueue<QueueNotify> sharedQueue, String dirNotify) {
        this.sharedQueue = sharedQueue;
        this.dirNotify = dirNotify;

    }
    public void run() {
        try {
            WatchService watcher = getWatchService();

            while (true) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    return;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    switch (kind.name()) {//event.kind().name())
                        case "OVERFLOW":
                            reportOverflow();

                            break;
                        case "ENTRY_MODIFY":
                            modify(fileName);
                            break;

                        case "ENTRY_CREATE":
                            create(fileName);
                            break;

                        case "ENTRY_DELETE":
                            delete(fileName);
                    }
                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private WatchService getWatchService() throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        Path dir = Paths.get(dirNotify);
        dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        return watcher;
    }
    private void reportOverflow() {
        LOGGER.info("WARNING SYSTEM OVERFLOWED");
    }

    private void modify(Path fileName) throws IOException, InterruptedException {
        String content = contentChanges.readContent(dirNotify + fileName.toString());
        QueueNotify actionFileContentModify = new ActionNameContent("ENTRY_MODIFY", fileName.toString(), content);
        sharedQueue.put(actionFileContentModify);
        LOGGER.info("file: " + dirNotify + " modified");
    }
    private void create(Path fileName) throws IOException, InterruptedException {
        String contentCreate = contentChanges.readContent(dirNotify + fileName.toString());
        QueueNotify actionFileContentCreate = new ActionNameContent("ENTRY_CREATE", fileName.toString(), contentCreate);
        LOGGER.info("file: " + dirNotify + " created");
        sharedQueue.put(actionFileContentCreate);

    }
    private void delete(Path fileName) throws InterruptedException {
        QueueNotify actionFileContentDelete = new ActionNameContent("ENTRY_MODIFY", fileName.toString(), null);
        sharedQueue.put(actionFileContentDelete);
        LOGGER.info("file: " + dirNotify + " deleted");

    }
}
