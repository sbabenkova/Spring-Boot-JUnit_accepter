package ru.inex.accepter.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.inex.accepter.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Формирует список задач
 */
@Component
@Data
@AllArgsConstructor
public class TaskListBuilderImpl implements TaskListBuilder {
    @Override
    public TaskList createTaskList(String ptkName, String clusterName, TaskPathList taskPathList, String gitRepositoryRef, TestType testType) {
        List<Task> taskList = new ArrayList<>();
        taskPathList.getTaskPathList().forEach(taskPath -> taskList.add(new Task(ptkName, testType, taskPath, gitRepositoryRef, TaskStatus.New)));
        return new TaskList(taskList);
    }

}
