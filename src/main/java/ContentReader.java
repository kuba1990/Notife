import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ContentReader {
    public String getChanges(String pathFile) throws IOException {
        String content;
        StringBuilder sb = new StringBuilder();

        Files.lines(Paths.get(pathFile)).forEachOrdered(s -> {
            sb.append(s);
            sb.append(System.lineSeparator());
        });
        return content = sb.toString();
    }
}
