package exercice.sax.parser;

import generic.Adapter;
import generic.Parseable;
import generic.classes.Action;
import exercice.sax.handler.SortContentHandler;
import lombok.RequiredArgsConstructor;
import org.jdom2.Document;
import org.jdom2.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class SortActionSaxParser implements Parseable<HashMap<String, List<Action>>> {
    private final String uri;
    private final SortContentHandler handler = new SortContentHandler();

    private final Adapter adapter = new Adapter();

    @Override
    public void start() throws SAXException, IOException, ParserConfigurationException {
        adapter.start(uri, handler);
        createDom(handler.getObject());
    }

    @Override
    public void createDom(HashMap<String, List<Action>> hashMap) {
        System.out.println("Construction du fichier XML");
        Element actions = new Element("Actions");

        hashMap.forEach((key, actionList) -> {
            Element actionGroup = new Element(key);

            actionList.stream().sorted(Comparator.comparingInt(Action::getCount).reversed()).forEach(action -> {
                Element actionElement = adapter.createElement(actionGroup, "Action");

                adapter.createElement(actionElement, "Nom", action, Action::getName);
                adapter.createElement(actionElement, "Nombre", action, (value) -> String.valueOf(value.getCount()));
            });


            adapter.addElement(actions, actionGroup);
        });

        adapter.save(new Document(actions), "src/main/java/xml/CroixRougeSaxExercice2.xml");
    }
}
