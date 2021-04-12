package spring.util;

public interface Validatable<T> {
    boolean validate(T object);
}
