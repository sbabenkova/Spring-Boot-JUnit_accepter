package ru.inex.accepter.exception;

/**
 * Предусмотрено, если Repository должен будет бросать исключение,
 * для идентификации, у какого компонента что-то пошло не так
 */
public class DbFailException extends RuntimeException {
    public DbFailException(String message) {
        super(message);
    }
}
