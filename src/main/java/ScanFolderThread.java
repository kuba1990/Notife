import java.io.IOException;
import java.nio.file.*;

import static com.sun.activation.registries.LogSupport.log;
import static java.nio.file.StandardWatchEventKinds.*;

public class ScanFolderThread extends Thread {

    // To co mowilismy, fajnie by to bylo przekazac przez argumenty uruchomieniowe
    private static final String PATH = "/home/jwisniowski/Desktop/Notify/";
    private ContentReader contentChanges = new ContentReader();

       public void run() {

           //complexity tej klasy jest strasznie duze
           // powinienes troche porozbijac to co tam sie dzieje (nawet od biedy po prostu do metod prywatnych)
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
                        case "ENTRY_MODIFY": //zwroc uwage, ze kod dla ENTRY_MODIFY i ENTRY_CREATE jest taki sam, mozesz zrobic z tego jeden warunek i uzyc kind.name jako action skoro tylko tym sie to rozni
                            String content = contentChanges.getChanges(PATH + fileName.toString());
                            QueueNotify actionFileContentModify = new ActionNameContent("ENTRY_MODIFY", fileName.toString(), content);
                            Queue.sharedQueue.put(actionFileContentModify);
                            break;

                        case "ENTRY_CREATE":
                            String contentCreate = contentChanges.getChanges(PATH + fileName.toString());
                            QueueNotify actionFileContentCreate = new ActionNameContent("ENTRY_CREATE", fileName.toString(), contentCreate);
                            Queue.sharedQueue.put(actionFileContentCreate);
                            break;

                        case "ENTRY_DELETE":// tu chyba pod spodem action nie taki jak trzeba
                            QueueNotify actionFileContentDelete = new ActionNameContent("ENTRY_MODIFY", fileName.toString(), null);
                            Queue.sharedQueue.put(actionFileContentDelete);
                            break;
                    }
                    // jak tylko raz odnosisz sie do key.reset() to mozesz od razu go w tym if zawrzec
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
            // Uwagi do exceptionow takie jak wszedzie, tylko + jezeli obslugujesz wyjatki w taki sam sposob to mozesz zawerze je w jednym catch
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

