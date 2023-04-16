package ru.inex.accepter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для заданий
 */
@Data
@AllArgsConstructor
public class Task {
    //private TaskPTK taskPtkNameId;
    private String ptkName;
    private TestType testType;
    private TaskPath taskPath;
    private String gitRepositoryRef;
    private TaskStatus taskStatus;
}
