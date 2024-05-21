package exo2_sax.handler;

import exo2_sax.generic.Adapter;
import exo2_sax.generic.Structure;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SimpleContentHandler extends BasicHandler {
    private final List<Structure> structures = new ArrayList<>();
    private Adapter adapter = new Adapter();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Structure structure;

    private boolean cpStructure = false;
    private boolean libelleStructure = false;
    private boolean webStructure = false;

    private boolean postalCodeValid = false;


    public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) {
        if (rawName.equals("Structure")) {
            structure = new Structure();
        }

        if (rawName.equals("Libelle_Structure")) {
            setLibelleStructure(true);
        }

        if (rawName.equals("CP_Structure")) {
            setCpStructure(true);
        }

        if (rawName.equals("Web_Structure")) {
            setWebStructure(true);
        }
    }

    public void characters(char[] ch, int start, int end) {
        String text = new String(ch, start, end);

        if (isLibelleStructure()) {
            adapter.setStructureValue(structure, text, Structure::setName);
        }

        if (isCpStructure()) {
            if (text.startsWith("35")) {
                setPostalCodeValid(true);
            }

            adapter.setStructureValue(structure, text, Structure::setCP);
        }

        if (isWebStructure()) {
            adapter.setStructureValue(structure, text, Structure::setWeb);
        }
    }

    public void endElement(String nameSpaceURI, String localName, String rawName) {
        if (rawName.equals("Structure")) {
            if (isPostalCodeValid()) {
                adapter.add(structures, structure);
                setPostalCodeValid(false);
            }

            structure = null;
        }

        if (rawName.equals("Libelle_Structure")) {
            setLibelleStructure(false);
        }

        if (rawName.equals("CP_Structure")) {
            setCpStructure(false);
        }

        if (rawName.equals("Web_Structure")) {
            setWebStructure(false);
        }
    }
}