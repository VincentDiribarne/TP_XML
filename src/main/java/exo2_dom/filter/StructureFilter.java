package exo2_dom.filter;

import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;

public class StructureFilter extends ElementFilter {
    @Override
    public Element filter(Object content) {
        Element returnElement = null;

        if (content instanceof Element element && element.getName() != null && element.getName().equals("Structure")) {
            returnElement = element;
        }

        return returnElement;
    }
}
