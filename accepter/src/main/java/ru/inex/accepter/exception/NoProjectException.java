package ru.inex.accepter.exception;
/**
 * Предусмотрено, если от ControlCenter пришел запрос с незнакомой группой
 */
public class NoProjectException extends RuntimeException {
    public NoProjectException(String message) {
        super(message);
    }
}
