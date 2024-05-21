package generic.classes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Action {
    private final String name;
    private int count = 1;

    @Override
    public String toString() {
        return name;
    }
}
