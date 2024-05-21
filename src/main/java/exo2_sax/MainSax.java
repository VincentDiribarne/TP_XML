package exo2_sax;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainSax {
    public static void main(String[] args) {
        try {
            String filePath = "/src/main/java/exo2_sax/xml/CroixRouge.xml";
            String encodedFilePath = URLEncoder.encode(filePath, StandardCharsets.UTF_8);

            SimpleSaxParser parser = new SimpleSaxParser(encodedFilePath);
            parser.start();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
