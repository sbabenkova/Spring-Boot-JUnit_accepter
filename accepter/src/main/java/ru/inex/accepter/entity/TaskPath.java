package ru.inex.accepter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Обертка для поля ответа с данными от TestListBuilderClient
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPath {
    private String taskPath;
}
