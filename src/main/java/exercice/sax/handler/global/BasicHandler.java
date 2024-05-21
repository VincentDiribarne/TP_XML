package exercice.sax.handler.global;

import lombok.Getter;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.LocatorImpl;

@Getter
public abstract class BasicHandler implements ContentHandler {
    private Locator locator;

    protected BasicHandler() {
        super();
        locator = new LocatorImpl();
    }

    @Override
    public void setDocumentLocator(Locator value) {
        locator = value;
    }

    @Override
    public void startDocument() {
        System.out.println("Debut de l'analyse du document");
    }

    @Override
    public void endDocument() {
        System.out.println("Fin de l'analyse du document");
    }

    @Override
    public void startPrefixMapping(String prefix, String URI) {
        System.out.println("Traitement de l'espace de nommage : " + URI + ", préfix choisi : " + prefix);
    }

    @Override
    public void endPrefixMapping(String prefix) {
        System.out.println("Fin de traitement de l'espace de nommage : " + prefix);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int end) {
        System.out.println("espaces inutiles rencontres : ..." + new String(ch, start, end) + "...");
    }

    @Override
    public void processingInstruction(String target, String data) {
        System.out.println("Instruction de fonctionnement : " + target);
        System.out.println("  dont les arguments sont : " + data);
    }

    @Override
    public void skippedEntity(String args) {
        //No-op
    }
}
