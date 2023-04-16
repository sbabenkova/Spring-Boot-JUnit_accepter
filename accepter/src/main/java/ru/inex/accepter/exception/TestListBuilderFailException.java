package ru.inex.accepter.exception;
/**
 * Предусмотрено, если TestListBuilder должен будет бросать исключение,
 * для идентификации, у какого компонента что-то пошло не так
 */
public class TestListBuilderFailException extends RuntimeException{
    public TestListBuilderFailException(String message) {
        super(message);
    }
}
