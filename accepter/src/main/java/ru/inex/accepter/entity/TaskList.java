package ru.inex.accepter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Обертка для списка заданий. Возможно, стоит отказаться и использовать List<Task> напрямую
 */
@Data
@AllArgsConstructor
public class TaskList {
    private final List<Task> taskList;
}
