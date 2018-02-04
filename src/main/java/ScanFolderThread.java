import java.io.IOException;
import java.nio.file.*;

import static com.sun.activation.registries.LogSupport.log;
import static java.nio.file.StandardWatchEventKinds.*;

public class ScanFolderThread extends Thread {

    private static final String PATH = "/home/jwisniowski/Desktop/Notify/";
    private ContentReader contentChanges = new ContentReader();

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
                            Create(fileName);
                            break;

                        case "ENTRY_DELETE":
                            Delete(fileName);
                    }
                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        Path dir = Paths.get(PATH);

        dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        System.out.println("Watch Service registered for dir: " + dir.getFileName());
        return watcher;
    }


    private void reportOverflow() {
        log("WARNING SYSTEM OVERFLOWED");
    }

    private void modify(Path fileName) throws IOException, InterruptedException {
        String content = contentChanges.getChanges(PATH + fileName.toString());
        QueueNotify actionFileContentModify = new ActionNameContent("ENTRY_MODIFY", fileName.toString(), content);
        Queue.sharedQueue.put(actionFileContentModify);

        System.out.println(content);
    }

    private void Create(Path fileName) throws IOException, InterruptedException {
        String contentCreate = contentChanges.getChanges(PATH + fileName.toString());
        QueueNotify actionFileContentCreate = new ActionNameContent("ENTRY_CREATE", fileName.toString(), contentCreate);
        System.out.println(contentCreate);

        Queue.sharedQueue.put(actionFileContentCreate);


    }

    private void Delete(Path fileName) throws InterruptedException {
        QueueNotify actionFileContentDelete = new ActionNameContent("ENTRY_MODIFY", fileName.toString(), null);
        Queue.sharedQueue.put(actionFileContentDelete);
    }
}

