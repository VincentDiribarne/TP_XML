package exercice.sax;

import exercice.sax.parser.FilterSaxParser;
import exercice.sax.parser.SortActionSaxParser;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainSax {
    public static void main(String[] args) {
        try {
            String filePath = "/src/main/java/xml/CroixRouge.xml";
            String encodedFilePath = URLEncoder.encode(filePath, StandardCharsets.UTF_8);

            FilterSaxParser filterSaxParser = new FilterSaxParser(encodedFilePath);
            filterSaxParser.start();

            SortActionSaxParser sortActionSaxParser = new SortActionSaxParser(encodedFilePath);
            sortActionSaxParser.start();
        } catch (Exception ignored) {
            System.out.println("Erreur lors de execution du programme");
        }
    }
}
