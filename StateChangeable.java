/**
 * File: StateChangeable.java
 * Author: Jared Buehner
 * Date: May 1, 2020
 * Purpose: This generic interface declares enumerated types as a bounded type parameter and contains an abstract method
 * to change state.
 */
public interface StateChangeable <T extends Enum<T>> {
    void changeState(T t);
}
