package ru.inex.accepter.repository;

import ru.inex.accepter.entity.TestTypeForGroup;

import java.util.List;

public interface TestTypeRepository {
    Integer getIdByName(String name);
    String getNameById(long id);
    List<TestTypeForGroup> getTestTypeList(String groupNameGitlab);
    Integer getGroupIdByName(String name);
}
