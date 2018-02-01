import java.io.IOException;
import java.nio.file.*;

import static com.sun.activation.registries.LogSupport.log;
import static java.nio.file.StandardWatchEventKinds.*;

public class ScanFolderThread extends Thread {

    private static final String PATH = "/home/jwisniowski/Desktop/Notify/";
    private ContentReader contentChanges = new ContentReader();

       public void run() {

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(PATH);

            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            System.out.println("Watch Service registered for dir: " + dir.getFileName());

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
                            log("WARNING SYSTEM OVERFLOWED");
                            break;
                        case "ENTRY_MODIFY":
                            String content = contentChanges.getChanges(PATH + fileName.toString());
                            QueueNotify actionFileContentModify = new ActionNameContent("ENTRY_MODIFY", fileName.toString(), content);
                            Queue.sharedQueue.put(actionFileContentModify);
                            System.out.println(content);
                            break;

                        case "ENTRY_CREATE":
                            String contentCreate = contentChanges.getChanges(PATH + fileName.toString());
                            QueueNotify actionFileContentCreate = new ActionNameContent("ENTRY_CREATE", fileName.toString(), contentCreate);
                            System.out.println(contentCreate);

                            Queue.sharedQueue.put(actionFileContentCreate);
                            break;

                        case "ENTRY_DELETE":
                            QueueNotify actionFileContentDelete = new ActionNameContent("ENTRY_MODIFY", fileName.toString(), null);
                            Queue.sharedQueue.put(actionFileContentDelete);

                            break;
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
}

