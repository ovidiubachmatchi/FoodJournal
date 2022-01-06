package hashmap;

import java.util.Objects;

public class Value {
    private int value;
    public Value(int v) {
        value=v;
    }

    public int getValue() {
        return value;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Value value1 = (Value) o;
        return Objects.equals(value, value1.value);
    }
}
