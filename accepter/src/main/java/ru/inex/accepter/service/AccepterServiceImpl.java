package ru.inex.accepter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inex.accepter.entity.*;
import ru.inex.accepter.exception.TestListBuilderFailException;
import ru.inex.accepter.repository.TaskListRepository;
import ru.inex.accepter.repository.TestTypeRepositoryImpl;

import java.util.List;
import java.util.function.Function;

/**
 * Основной сервис приложения
 */
@Service
@Slf4j
public class AccepterServiceImpl implements AccepterService {
    private final TestTypeRepositoryImpl testTypeRepositoryImpl;
    private final TaskListRepository taskListRepository;
    private final TaskListBuilder taskListBuilder;
    private final Function<TestType, TestListBuilderClient> testListBuilderClientSupplier;

    @Autowired
    public AccepterServiceImpl(TestTypeRepositoryImpl testTypeRepositoryImpl, TaskListRepository taskListRepository, TaskListBuilder taskListBuilder,
                               Function<TestType, TestListBuilderClient> testListBuilderClientSupplier) {
        this.testTypeRepositoryImpl = testTypeRepositoryImpl;
        this.taskListRepository = taskListRepository;
        this.taskListBuilder = taskListBuilder;
        this.testListBuilderClientSupplier = testListBuilderClientSupplier;
    }

    @Override
    public void processRequest(RequestParams requestParams) {
        List<TestTypeForGroup> testTypeForGroupList = testTypeRepositoryImpl.getTestTypeList(requestParams.getGroupNameGitlab());
        if(testTypeForGroupList.size()!=0) {
            testTypeForGroupList.forEach(testTypeForGroup -> {
                TaskPathList taskPathList = testListBuilderClientSupplier.apply(testTypeForGroup.getTestType())
                        .getTaskPathList(testTypeForGroup.getProjectIdGitlab(), requestParams.getGitRepositoryRef());
                TaskList taskList = taskListBuilder
                        .createTaskList(requestParams.getPtkName(), requestParams.getClusterName(), taskPathList, requestParams.getGitRepositoryRef(), testTypeForGroup.getTestType());
                taskListRepository.saveTaskList(taskList);
            });
        } else {throw new TestListBuilderFailException("No tests for GroupNameGitlab " + requestParams.getGroupNameGitlab() + " and GitRepositoryRef " + requestParams.getGitRepositoryRef() + " in class " + this.getClass());
        }
    }
}
