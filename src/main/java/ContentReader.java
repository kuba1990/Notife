import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ContentReader {

    public String getChanges(String pathFile) {
        File file = new File(pathFile);
        String content = "";
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNext()) {
                String i = sc.next();
                //.toString() na stringu nie jest potrzebny
                // Konkatenacja w pętli! Używaj StringBuildera
                content += i.toString();
            }
        } catch (FileNotFoundException e) {
            // Normalnie z takim bledem raczej sie powinno cos zrobic, jezeli nie jestes w stanie go sensownie tutaj obsluzyc to rzucasz go wyzej
            // Ew. jezeli chcesz zalogowac, to uzywaj loggerow log4j, log4j2, logback lub jezeli chcesz sie uniezaleznic od wyboru loggera to najlepiej sl4j z ktoryms z nich
            e.printStackTrace();
        }
        // Jezeli blad poleci przy tworzeniu obiektu 'sc' to masz tutaj nullpointer
        // Uzywales try with resources, tutaj tez mozesz tego uzyc
        sc.close();
        return content;
    }
}