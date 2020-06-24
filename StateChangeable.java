public interface StateChangeable <T extends Enum<T>> {
    void changeState(T t);
}
