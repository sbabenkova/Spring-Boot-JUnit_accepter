package ru.inex.accepter.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.inex.accepter.config.AppTestConfig;
import ru.inex.accepter.entity.*;
import ru.inex.accepter.exception.TestListBuilderFailException;
import ru.inex.accepter.repository.TaskListRepository;
import ru.inex.accepter.repository.TestTypeRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(classes = AppTestConfig.class)
@ActiveProfiles("test")
@Slf4j
class AccepterServiceTest {

    @MockBean
    private TestTypeRepositoryImpl testTypeRepositoryImpl;

    @MockBean
    private TaskListRepository taskListRepository;
//    @MockBean
//    private GitlabProjectToTestRepositoryImpl gitlabProjectToTestRepositoryImpl;
    //private Repository repository;
    @MockBean
    private TaskListBuilder taskListBuilder;
    @MockBean
    private Function<TestType, TestListBuilderClient> testListBuilderClientSupplier;
    @MockBean (name = "testListBuilderCypressClient")
    private TestListBuilderClient testListBuilderClient;
    @Autowired
    private AccepterService accepterService;

    @BeforeEach
    void setUp() {
        List<TaskPath> mockingList = new ArrayList<>();
        mockingList.add(new TaskPath("cypress/e2e/_common"));
        mockingList.add(new TaskPath("cypress/e2e/_noifr"));
        mockingList.add(new TaskPath("cypress/e2e/_themes"));
        mockingList.add(new TaskPath("cypress/e2e/account"));
        TaskPathList mockingTaskPathList = new TaskPathList(mockingList);
        List<TestTypeForGroup> testTypeForGroups = new ArrayList<>();
        TestType testType = TestType.cypress;
        testTypeForGroups.add(new TestTypeForGroup(testType, 0));
        Mockito.doReturn(testTypeForGroups).when(testTypeRepositoryImpl).getTestTypeList("positive");
        Mockito.doReturn(testTypeForGroups).when(testTypeRepositoryImpl).getTestTypeList("negative");
        Mockito.doReturn(testListBuilderClient).when(testListBuilderClientSupplier).apply(testType);
        Mockito.doReturn(mockingTaskPathList).when(testListBuilderClient).getTaskPathList(0, "positive");
        //Mockito.doReturn(0).when(gitlabProjectToTestRepositoryImpl).getGitlabProjectToTest("positive", testType);
        Mockito.doThrow(new TestListBuilderFailException("")).when(testListBuilderClient).getTaskPathList(0, "negative");
        List<Task> list = List.of(new Task("ptk", testType, new TaskPath("cypress/e2e/_common"), "clusterName", TaskStatus.New),
                new Task("ptk", testType, new TaskPath("cypress/e2e/_noifr"), "clusterName", TaskStatus.New),
                new Task("ptk", testType, new TaskPath("cypress/e2e/_themes"), "clusterName", TaskStatus.New),
                new Task("ptk", testType, new TaskPath("cypress/e2e/account"), "clusterName", TaskStatus.New));

        TaskList mockingTaskList = new TaskList(list);
        Mockito.doReturn(mockingTaskList).when(taskListBuilder).createTaskList(anyString(), anyString(), any(), anyString(), any(TestType.class));
    }

    @Test
    void processRequest_requestParamsIsValid_ReturnsValidResponse() {
        RequestParams requestParams = new RequestParams("ptk", "positive", "positive", "clusterName");
        accepterService.processRequest(requestParams);
        Mockito.verify(taskListBuilder, Mockito.times(1)).createTaskList(anyString(), anyString(), any(), anyString(), any(TestType.class));
        Mockito.verify(taskListRepository, Mockito.times(1)).saveTaskList(ArgumentMatchers.any());
    }

    @Test
    void processRequest_testListBuilderClientThrowsException_ReturnsError() throws TestListBuilderFailException{
        RequestParams requestParams = new RequestParams("ptk", "negative", "negative", "clusterName");
        TestListBuilderFailException testListBuilderFailException = assertThrows(TestListBuilderFailException.class, () ->
                accepterService.processRequest(requestParams));
        assertNotNull(testListBuilderFailException.getMessage());
    }
}