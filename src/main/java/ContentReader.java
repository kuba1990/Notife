import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContentReader {

    public String getChanges(String pathFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(pathFile));
        try {
            StringBuilder content = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                content.append(line);
                content.append("\n");
                line = br.readLine();
            }
            return content.toString();

        } finally {
            br.close();
        }
    }
}