package ru.inex.accepter.service;

import ru.inex.accepter.entity.TaskPathList;
/**
 * интерфейс для клиентов, взаимодействующих с приложениями TestListBuilder
 */
public interface TestListBuilderClient {
    TaskPathList getTaskPathList(long projectIdGitlab, String gitRepositoryRef);
}
