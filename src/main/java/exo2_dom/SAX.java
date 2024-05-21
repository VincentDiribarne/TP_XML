package exo2_dom;

import exo2_dom.filter.PostalCodeStructureFilter;
import exo2_dom.filter.StructureFilter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAX {
    private final List<Element> postalStructureFilterList = new ArrayList<>();
    private List<Element> childElements = new ArrayList<>();

    public void start() {
        SAXBuilder sxb = new SAXBuilder();
        Document document = null;

        try {
            document = sxb.build(new File("src/main/java/exercice2/xml/CroixRouge.xml"));
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        if (document != null) {
            Element root = document.getRootElement();

            StructureFilter structureFilter = new StructureFilter();
            List<Element> result = root.getContent(structureFilter);

            PostalCodeStructureFilter postalCodeStructureFilter = new PostalCodeStructureFilter();
            for (Element element : result) {
                postalStructureFilterList.addAll(element.getContent(postalCodeStructureFilter));
            }

            createDom();
        }
    }

    private void createDom() {
        Element root = new Element("Structures");

        postalStructureFilterList.forEach(element -> {
            childElements = element.getContent().stream()
                    .filter(childElement -> childElement instanceof Element)
                    .map(childElement -> (Element) childElement)
                    .toList();

            Element child = new Element("Structure");

            Element name = new Element("Libellé");
            name.setText(getProperties("Libelle_Structure"));
            child.addContent(name);

            Element cp = new Element("CP");
            cp.setText(getProperties("CP_Structure"));
            child.addContent(cp);

            Element webStructure = new Element("Web_Structure");
            webStructure.setText(getProperties("Web_Structure"));
            child.addContent(webStructure);

            root.addContent(child);
        });

        save(new Document(root), "src/main/java/exercice2/xml/CroixRougeFiltré.xml");
    }

    private String getProperties(String properties) {
        return childElements.stream().filter(el -> el.getName().equals(properties)).map(Element::getTextTrim).findFirst().orElse(null);
    }

    public void save(Document document, String fichier) {
        try {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(document, new FileOutputStream(fichier));
        } catch (IOException ignored) {
        }
    }
}