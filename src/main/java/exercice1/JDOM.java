package exercice1;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileOutputStream;
import java.io.IOException;

public class JDOM {
    private final Element root = new Element("personnes");
    private final Document document = new Document(root);

    public void start() {
        Element student = new Element("etudiant");
        root.addContent(student);

        Attribute schoolClass = new Attribute("classe", "P2");
        student.setAttribute(schoolClass);

        Element name = new Element("nom");
        name.setText("CynO");
        student.addContent(name);

        affiche();
        enregistre("Exercice1.xml");
    }

    public void affiche() {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(document, System.out);
        } catch (IOException ignored) {
        }
    }

    public void enregistre(String fichier) {
        try {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(document, new FileOutputStream(fichier));
        } catch (IOException ignored) {
        }
    }
}
