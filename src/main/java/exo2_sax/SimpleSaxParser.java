package exo2_sax;

import exo2_sax.generic.Adapter;
import exo2_sax.generic.Structure;
import exo2_sax.handler.SimpleContentHandler;
import lombok.RequiredArgsConstructor;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class SimpleSaxParser {
    private final String uri;
    private final Adapter adapter = new Adapter();

    public void start() throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        SimpleContentHandler handler = new SimpleContentHandler();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(handler);
        xmlReader.parse(uri);

        createDom(handler.getStructures());
    }

    private void createDom(List<Structure> list) {
        System.out.println("Construction du fichier XML");
        Element root = new Element("Structures");

        for (Structure structure : list) {
            Element structureElement = new Element("Structure");

            adapter.createElement(structureElement, "Libellé", structure, Structure::getName);
            adapter.createElement(structureElement, "CP", structure, Structure::getCP);
            adapter.createElement(structureElement, "Site_Web", structure, Structure::getWeb);

            adapter.addElement(root, structureElement);
        }

        save(new Document(root), "src/main/java/exo2_sax/xml/CroixRougeFiltré.xml");
    }

    public void save(Document document, String file) {
        try {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(document, new FileOutputStream(file));

            String[] splitFile = file.split("/");
            String fileName = splitFile[splitFile.length - 1];

            System.out.println("Document créé avec le nom : " + fileName);
        } catch (IOException ignored) {
        }
    }
}
