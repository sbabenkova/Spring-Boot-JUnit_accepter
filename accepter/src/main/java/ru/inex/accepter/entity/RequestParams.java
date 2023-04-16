package ru.inex.accepter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Обертка для передачи параметров контроллером в сервис
 */
@Data
@AllArgsConstructor
public class RequestParams {
    private String ptkName;
    private String groupNameGitlab;
    private String gitRepositoryRef;
    private String clusterName;
}
