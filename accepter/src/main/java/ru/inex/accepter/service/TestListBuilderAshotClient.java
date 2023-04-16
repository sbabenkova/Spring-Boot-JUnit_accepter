package ru.inex.accepter.service;

import org.springframework.stereotype.Component;
import ru.inex.accepter.entity.TaskPathList;
import ru.inex.accepter.exception.NotImplementedException;

@Component
public class TestListBuilderAshotClient implements TestListBuilderClient{
    @Override
    public TaskPathList getTaskPathList(long projectIdGitlab, String gitRepositoryRef) {
        throw new NotImplementedException(this.getClass().getName() + " is not implemented yet");
    }
}
