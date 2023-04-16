package ru.inex.accepter.exception;

/**
 * Затычка для нереализованных сейчас имплементаций, например, TestListBuilderKarateClient или TestListBuilderAshotClient
 */
public class NotImplementedException extends RuntimeException{
    public NotImplementedException(String message) {
        super(message);
    }
}
