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
                content += i.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.close();
        return content;
    }
}