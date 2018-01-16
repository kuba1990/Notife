import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class NotifeFolder {

    public String content;

    DatabaseRecord databaseRecord = new DatabaseRecord();
    TrackContentChanges trackContentChanges = new TrackContentChanges();

    /*public void notifyContent(String path) {
        List<String> list = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            *//*String content = new String(Files.readAllBytes(Paths.get(paths.toString())));*//*
            paths
                    .filter(Files::isRegularFile)
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

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

                    switch (kind.name()) {//event.kind().name())
                        case "ENTRY_MODIFY":
                            String content = trackContentChanges.getChanges("/home/jwisniowski/Desktop/Notify/"+fileName.toString());

                            System.out.println("ENTRY_MODIFY" + ": " + "  WHAT FILE " + fileName + "  WHAT CONTENT " + content);
                            databaseRecord.modify("MODIFY", fileName.toString(), content);
                            break;

                        case "ENTRY_CREATE":
                            String contentCreate = trackContentChanges.getChanges("/home/jwisniowski/Desktop/Notify/"+fileName.toString());

                            System.out.println("ENTRY_CREATE" + ": " + "  WHAT FILE " + fileName + "  WHAT CONTENT " + contentCreate);
                            databaseRecord.add("CREATE", fileName.toString(), contentCreate);
                            break;

                        case "ENTRY_DELETE":
                            System.out.println("ENTRY_DELETE" + ": " + "  WHAT FILE " + fileName);
                            databaseRecord.delete("DELETE", fileName.toString());
                            break;
                        case "OVERFLOW":
                            System.out.println("ERROR SYSTEM OVERFLOWED");

                    }

                   /* if (kind == ENTRY_DELETE) {
                        String fileNameS = fileName.toString();
                        System.out.println("ENTRY_DELETE" + ": " + "  WHAT FILE " + fileName);
                        MySQLJava dao = new MySQLJava(Main.MYSQL_DRIVER, Main.MYSQL_URL);
                        dao.readData("Entry delete", fileNameS, "file delete");

                    }
                    *//* System.out.println("WHAT ACTION  "+kind.name() + ": " + "  WHAT FILE"+fileName + "  WHAT CONTENT " + content);*//*
                    if (kind == ENTRY_CREATE) {
                        ///take content
                        String fileNameString = String.valueOf(ev.context());
                        File file = new File(path + "/" + fileNameString);

                        *//* File file = new File("/home/jwisniowski/Desktop/Notify/Kuba");*//*

                        Scanner sc = new Scanner(file);

                        while (sc.hasNext()) {
                            String i = sc.next();
                            content += i.toString();
                        }

                        String fileNameS = fileName.toString();
                        System.out.println("ENTRY_CREATE" + ": " + "  WHAT FILE " + fileName + "  WHAT CONTENT " + content);
                        MySQLJava dao = new MySQLJava(Main.MYSQL_DRIVER, Main.MYSQL_URL);
                        dao.readData("Entry Create", fileNameS, content);
                    }
                    if (kind == ENTRY_MODIFY) {
                        String fileNameString = String.valueOf(ev.context());
                        File file = new File(path + "/" + fileNameString);

                        *//* File file = new File("/home/jwisniowski/Desktop/Notify/Kuba");*//*




                        Scanner sc = new Scanner(file);

                        while (sc.hasNext()) {
                            String i = sc.next();
                            content += i.toString();
                        }
                        String fileNameS = fileName.toString();
                        System.out.println("ENTRY_MODIFY" + ": " + "  WHAT FILE " + fileName + "  WHAT CONTENT " + content);
                        MySQLJava dao = new MySQLJava(Main.MYSQL_DRIVER, Main.MYSQL_URL);
                        dao.readData("Entry modify", fileNameS, content);
                    }

                }*/
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