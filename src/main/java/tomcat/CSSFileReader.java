package tomcat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSSFileReader {
    public static String read(String path) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
        }

        return builder.toString();
    }
}