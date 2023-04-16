package ru.inex.accepter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class GitlabProjectToTest {
    @Id
    private Integer Id;
    private Integer testTypeId;
    private Integer groupId;
    private Integer gitlabProjectId;
}
