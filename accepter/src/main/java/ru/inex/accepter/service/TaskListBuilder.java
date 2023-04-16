package ru.inex.accepter.service;

import ru.inex.accepter.entity.TaskList;
import ru.inex.accepter.entity.TaskPathList;
import ru.inex.accepter.entity.TestType;

/**
 * интерфейс для компонента, составляющего таски
 */
public interface TaskListBuilder {
    TaskList createTaskList (String ptkName, String clusterName, TaskPathList taskPathList, String gitRepositoryRef, TestType testType);
}
