import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.sun.activation.registries.LogSupport.log;
import static java.nio.file.StandardWatchEventKinds.*;

public class NotifyFolder {

    DatabaseRecord databaseRecord = new DatabaseRecord();
    TrackContentChanges trackContentChanges = new TrackContentChanges();

    //change this class to return list of elements
    public List<String> notifyContent(String path) {
        List<String> list = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void notify(String path) {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(path);
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
                    // put file content here - use path from constructor
                    //change to enums https://docs.oracle.com/javase/7/docs/api/java/nio/file/StandardWatchEventKinds.html
                    switch (kind.name()) {//event.kind().name())
                        case "OVERFLOW":
                            log("WARNING SYSTEM OVERFLOWED");
                            break;
                        case "ENTRY_MODIFY":
                            //
                            String content = trackContentChanges.getChanges("/home/jwisniowski/Desktop/Notify/" + fileName.toString());
                            System.out.println("ENTRY_MODIFY" + ": " + "  WHAT FILE " + fileName + "  WHAT CONTENT " + content);
                            databaseRecord.modify("MODIFY", fileName.toString(), content);
                            log("file was modifed" + fileName.toString());
                            break;

                        case "ENTRY_CREATE":
                            String contentCreate = trackContentChanges.getChanges("/home/jwisniowski/Desktop/Notify/" + fileName.toString());
                            System.out.println("ENTRY_CREATE" + ": " + "  WHAT FILE " + fileName + "  WHAT CONTENT " + contentCreate);
                            databaseRecord.add("CREATE", fileName.toString(), contentCreate);
                            log("file was create" + fileName.toString());
                            break;

                        case "ENTRY_DELETE":
                            System.out.println("ENTRY_DELETE" + ": " + "  WHAT FILE " + fileName);
                            databaseRecord.delete("DELETE", fileName.toString());
                            break;
                    }
                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
