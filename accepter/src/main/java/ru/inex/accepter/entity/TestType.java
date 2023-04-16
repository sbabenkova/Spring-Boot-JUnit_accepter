package ru.inex.accepter.entity;

import lombok.AllArgsConstructor;

/**
 * Возможные типы тестов, с которыми работает приложение
 */
@AllArgsConstructor
public enum TestType {
    cypress(1),
    karate(2),
    ashot(3);

    private Integer testTypeId;

    public Integer getTestTypeId() {
        return testTypeId;
    }
}