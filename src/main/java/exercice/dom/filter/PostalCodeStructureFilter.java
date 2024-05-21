package exercice.dom.filter;

import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;

public class PostalCodeStructureFilter extends ElementFilter {
    @Override
    public Element filter(Object content) {
        Element returnElement = null;

        if (content instanceof Element element) {
            if (element.getName() != null && element.getName().equals("CP_Structure") && element.getTextTrim().startsWith("35")) {
                returnElement = element.getParentElement();
            }
        }

        return returnElement;
    }
}