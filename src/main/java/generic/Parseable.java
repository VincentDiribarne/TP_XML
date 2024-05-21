package generic;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface Parseable<O> {
    void start() throws SAXException, IOException, ParserConfigurationException;

    void createDom(O object);
}
