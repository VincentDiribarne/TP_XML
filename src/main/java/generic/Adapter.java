package generic;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Adapter {
    public <E> void add(List<E> list, E object) {
        list.add(object);
    }

    public <K, V> void put(HashMap<K, V> hashMap, K key, V value) {
        hashMap.put(key, value);
    }

    public String getFormattedText(String text) {
        return text.trim().replace("\n", "");
    }

    public Element createElement(Element addElement, String key) {
        return createElement(addElement, key, null, null);
    }

    public <C> Element createElement(Element addElement, String key, @Nullable C className, @Nullable ObjectGetter<C, String> getter) {
        Element newElement = new Element(key);

        if (getter != null) {
            setText(newElement, getStructureValue(className, getter));
        }

        addElement(addElement, newElement);

        return newElement;
    }

    public void setText(Element element, String text) {
        element.setText(text);
    }

    public void addElement(@NotNull Element firstElement, Element secondElement) {
        firstElement.addContent(secondElement);
    }

    public <C, V> V getStructureValue(C className, ObjectGetter<C, V> getter) {
        return getter.get(className);
    }

    public <C, V> void setStructureValue(C className, V value, ObjectSetter<C, V> setter) {
        setter.set(className, value);
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

    public void start(String uri, ContentHandler handler) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(handler);
        xmlReader.parse(uri);
    }

    @FunctionalInterface
    public interface ObjectGetter<C, V> {
        V get(C className);
    }

    @FunctionalInterface
    public interface ObjectSetter<C, V> {
        void set(C className, V value);
    }
}
