package exo2_sax.generic;

import org.jdom2.Element;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter {
    public <T> void add(List<T> list, T object) {
        list.add(object);
    }

    public void createElement(Element addElement, String key, Structure structure, StructureGetter<String> getter) {
        Element newElement = new Element(key);

        setText(newElement, getStructureValue(structure, getter));
        addElement(addElement, newElement);
    }

    public void setText(Element element, String text) {
        element.setText(text);
    }

    public void addElement(@NotNull Element firstElement, Element secondElement) {
        firstElement.addContent(secondElement);
    }


    public <T> T getStructureValue(Structure structure, StructureGetter<T> getter) {
        return getter.get(structure);
    }

    public <T> void setStructureValue(Structure structure, T value, StructureSetter<T> setter) {
        setter.set(structure, value);
    }

    @FunctionalInterface
    public interface StructureGetter<T> {
        T get(Structure structure);
    }

    @FunctionalInterface
    public interface StructureSetter<T> {
        void set(Structure structure, T value);
    }
}
