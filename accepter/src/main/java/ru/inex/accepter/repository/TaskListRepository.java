package ru.inex.accepter.repository;

import ru.inex.accepter.entity.TaskList;

public interface TaskListRepository {
    void saveTaskList(TaskList taskList);
}
