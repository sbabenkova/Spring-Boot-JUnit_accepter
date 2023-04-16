package ru.inex.accepter.service;

import ru.inex.accepter.entity.RequestParams;

/**
 * интерфейс для основного сервиса приложения
 */
public interface AccepterService {
    void processRequest(RequestParams requestParams);
}
