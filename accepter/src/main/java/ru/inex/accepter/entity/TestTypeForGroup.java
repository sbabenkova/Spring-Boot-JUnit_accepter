package ru.inex.accepter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Соответствие id gitlab и типа тестов для группы (rzd,mcc...)
 */
@Data
@AllArgsConstructor
public class TestTypeForGroup {
    private TestType testType;
    private long projectIdGitlab;
}