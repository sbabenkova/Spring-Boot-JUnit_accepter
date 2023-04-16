package ru.inex.accepter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Обертка для данных в ответе от TestListBuilderClient
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskPathList {
    private List<TaskPath> taskPathList;
}
