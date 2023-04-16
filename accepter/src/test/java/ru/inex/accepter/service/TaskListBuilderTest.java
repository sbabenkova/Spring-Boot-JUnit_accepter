package ru.inex.accepter.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.inex.accepter.config.AppTestConfig;
import ru.inex.accepter.entity.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AppTestConfig.class)
@ActiveProfiles("local")
@Slf4j
class TaskListBuilderTest {

    @Autowired
    private TaskListBuilderImpl tackListBuilderImpl;

     @Test
    void createTaskList_DataIsValid_ReturnsValidResponse() {
        //given
        List<TaskPath> expectedInputTaskPathList = List.of(new TaskPath("cypress/e2e/_common"),new TaskPath("cypress/e2e/_noifr"),new TaskPath("cypress/e2e/_themes"),new TaskPath("cypress/e2e/account"));
        TaskPathList expectedTaskPathList = new TaskPathList(expectedInputTaskPathList);

        List<Task> expectedInputTaskList = List.of(
                new Task("ptk", TestType.cypress, expectedTaskPathList.getTaskPathList().get(0), "positive", TaskStatus.New),
                new Task("ptk", TestType.cypress, expectedTaskPathList.getTaskPathList().get(1), "positive", TaskStatus.New),
                new Task("ptk", TestType.cypress, expectedTaskPathList.getTaskPathList().get(2), "positive", TaskStatus.New),
                new Task("ptk", TestType.cypress, expectedTaskPathList.getTaskPathList().get(3), "positive", TaskStatus.New));
        TaskList expectedTaskList = new TaskList(expectedInputTaskList);

        //when
        TaskList actualTackList = tackListBuilderImpl.createTaskList("ptk", "clusterName", expectedTaskPathList,"positive", TestType.cypress);

        //then
        assertNotNull(actualTackList);
//        assertInstanceOf(TaskList.class, actualTackList.getClass());
        assertEquals(actualTackList.getTaskList(),expectedTaskList.getTaskList());
    }
}