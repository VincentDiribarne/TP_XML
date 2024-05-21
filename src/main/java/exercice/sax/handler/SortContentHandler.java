package exercice.sax.handler;

import exercice.sax.handler.global.BasicHandler;
import exercice.sax.handler.global.Handleable;
import generic.Adapter;
import generic.classes.Action;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class SortContentHandler extends BasicHandler implements Handleable<HashMap<String, List<Action>>> {
    @Getter(AccessLevel.NONE)
    private final HashMap<String, List<Action>> hashMap = new HashMap<>();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Action> actions = new ArrayList<>();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<String> titleStringList = null;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<String> actionStringList = null;


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final Adapter adapter = new Adapter();

    public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) {
        if (rawName.equals("Groupe_Actions")) {
            titleStringList = new ArrayList<>();
        }

        if (rawName.equals("Action")) {
            actionStringList = new ArrayList<>();
        }
    }

    public void characters(char[] ch, int start, int end) {
        String text = new String(ch, start, end);

        if (titleStringList != null && actionStringList == null) {
            if (!text.isBlank()) {
                adapter.add(titleStringList, text);
            }
        }

        if (actionStringList != null) {
            if (!text.isBlank()) {
                adapter.add(actionStringList, text);
            }
        }
    }

    public void endElement(String nameSpaceURI, String localName, String rawName) {
        if (rawName.equals("Groupe_Actions")) {
            String key = adapter.getFormattedText(String.join("", titleStringList)).replace(" ", "_");

            if (!hashMap.containsKey(key)) {
                adapter.put(hashMap, key, actions);
            } else {
                List<Action> valuesAction = hashMap.get(key);

                actions.forEach(action -> {
                    Action foundAction = valuesAction.stream()
                            .filter(valueAction -> valueAction.getName().equals(action.getName()))
                            .findFirst()
                            .orElse(null);

                    if (foundAction == null) {
                        adapter.add(valuesAction, action);
                    } else {
                        foundAction.setCount(foundAction.getCount() + 1);
                    }
                });
            }

            titleStringList = null;
            actions = new ArrayList<>();
        }

        if (rawName.equals("Action")) {
            adapter.add(actions, new Action(adapter.getFormattedText(String.join("", actionStringList))));

            actionStringList = null;
        }
    }

    @Override
    public HashMap<String, List<Action>> getObject() {
        return hashMap;
    }
}
