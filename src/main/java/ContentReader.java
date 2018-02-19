import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContentReader {
    public String readContent(String pathFile) throws IOException {
        String content;
        StringBuilder sb = new StringBuilder();

        Files.lines(Paths.get(pathFile)).forEachOrdered(s -> {
            sb.append(s);
            sb.append(System.lineSeparator());
        });
        return content = sb.toString();
    }
}
