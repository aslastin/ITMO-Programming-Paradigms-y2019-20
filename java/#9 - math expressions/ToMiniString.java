package HW.HW11.expression;

public interface ToMiniString {
    default String toMiniString() {
        return toString();
    }
}
