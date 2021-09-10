package expression.generic;

public interface ToMiniString {
    default String toMiniString() {
        return toString();
    }
}
