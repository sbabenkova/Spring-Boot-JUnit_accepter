package ru.inex.accepter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.inex.accepter.entity.ApiResponse;
import ru.inex.accepter.entity.TaskPathList;
import ru.inex.accepter.exception.TestListBuilderFailException;

import java.net.URI;

/**
 * Обращается к приложению test-list-builder-cypress за списком параметров для запуска тестов.
 */
@Component
@Slf4j
public class TestListBuilderCypressClient implements TestListBuilderClient {
    private final RestTemplate restTemplate;
    @Value("${test-list-builder-cypress.protocol}")
    private String testListBuilderCypressProtocol;
    @Value("${test-list-builder-cypress.base_url}")
    private String testListBuilderCypressHost;
    @Value("${test-list-builder-cypress.context}")
    private String testListBuilderCypressContext;
    @Value("${test-list-builder-cypress.port}")
    private int testListBuilderCypressPort;

    public TestListBuilderCypressClient(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public TaskPathList getTaskPathList(long projectIdGitlab, String gitRepositoryRef) {
        URI testListBuilderCypressUri = UriComponentsBuilder.newInstance()
                .scheme(testListBuilderCypressProtocol)
                .host(testListBuilderCypressHost)
                .path("tests/")
                .path("getTestPathListFromGitlab")
                .port(testListBuilderCypressPort)
                .queryParam("projectIdGitlab", projectIdGitlab)
                .queryParam("ref", gitRepositoryRef)
                .build()
                .toUri();
        log.info(testListBuilderCypressUri + "");
        RequestEntity<Object> requestEntity = new RequestEntity<>(HttpMethod.GET, testListBuilderCypressUri);
        log.info("requestEntity: {}", requestEntity);
        try {
            ResponseEntity<ApiResponse<TaskPathList>> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<>() {
            });
            log.info("response: {}", response);
            ApiResponse<TaskPathList> responseBody = response.getBody();
            if (responseBody != null) {
                return responseBody.getData();
            } else {
                log.error("ResponseBody is null in " + this.getClass()
                        + " while send a request to URI " + testListBuilderCypressUri);
                throw new TestListBuilderFailException("ResponseBody is null in " + this.getClass()
                        + " while send a request to URI " + testListBuilderCypressUri);
            }
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            log.error(e.getClass() + " caught while send a request in" + this.getClass() + " to URI " + testListBuilderCypressUri);
            throw new TestListBuilderFailException(e.getMessage() + " in " + this.getClass());
        }
    }
}
