package exercice.sax.parser;

import generic.Adapter;
import generic.Parseable;
import generic.classes.Structure;
import exercice.sax.handler.FilterContentHandler;
import lombok.RequiredArgsConstructor;
import org.jdom2.Document;
import org.jdom2.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class FilterSaxParser implements Parseable<List<Structure>> {
    private final String uri;
    private final FilterContentHandler handler = new FilterContentHandler();

    private final Adapter adapter = new Adapter();

    public void start() throws ParserConfigurationException, IOException, SAXException {
        adapter.start(uri, handler);
        createDom(handler.getObject());
    }

    public void createDom(List<Structure> list) {
        System.out.println("Construction du fichier XML");
        Element root = new Element("Structures");

        for (Structure structure : list) {
            Element structureElement = new Element("Structure");

            adapter.createElement(structureElement, "Libellé", structure, Structure::getName);
            adapter.createElement(structureElement, "CP", structure, Structure::getCP);
            adapter.createElement(structureElement, "Site_Web", structure, Structure::getWeb);

            adapter.addElement(root, structureElement);
        }

        adapter.save(new Document(root), "src/main/java/xml/CroixRougeSaxExercice1.xml");
    }
}
