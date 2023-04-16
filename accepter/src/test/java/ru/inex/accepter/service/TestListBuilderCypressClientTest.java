package ru.inex.accepter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.inex.accepter.config.AppTestConfig;
import ru.inex.accepter.entity.ApiResponse;
import ru.inex.accepter.entity.Result;
import ru.inex.accepter.entity.TaskPath;
import ru.inex.accepter.entity.TaskPathList;
import ru.inex.accepter.exception.TestListBuilderFailException;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = AppTestConfig.class)
@ActiveProfiles("local")
class TestListBuilderCypressClientTest {
    @Autowired
    private TestListBuilderCypressClient testListBuilderCypressClient;
    @MockBean
    private RestTemplate restTemplate;
    @Value("${test-list-builder-cypress.protocol}")
    private String testListBuilderCypressProtocol;
    @Value("${test-list-builder-cypress.base_url}")
    private String testListBuilderCypressHost;
    @Value("${test-list-builder-cypress.context}")
    private String testListBuilderCypressContext;
    @Value("${test-list-builder-cypress.port}")
    private int testListBuilderCypressPort;

    @BeforeEach
    void setUp() {
        URI positiveUri = UriComponentsBuilder.newInstance()
                .scheme(testListBuilderCypressProtocol)
                .host(testListBuilderCypressHost)
                .path(testListBuilderCypressContext)
                .port(testListBuilderCypressPort)
                .queryParam("projectIdGitlab", 0)
                .queryParam("ref", "positive")
                .build()
                .toUri();
        List<TaskPath> taskPaths = new ArrayList<>();
        taskPaths.add(new TaskPath("cypress/e2e/_common"));
        taskPaths.add(new TaskPath("cypress/e2e/_noifr"));
        taskPaths.add(new TaskPath("cypress/e2e/_themes"));
        taskPaths.add(new TaskPath("cypress/e2e/account"));
        TaskPathList taskPathList = new TaskPathList(taskPaths);
        ApiResponse<TaskPathList> positiveApiResponse = new ApiResponse<>(Result.OK, taskPathList, "", LocalDateTime.now());
        RequestEntity<Object> positiveRequestEntity = new RequestEntity<>(HttpMethod.GET, positiveUri);
        ResponseEntity<ApiResponse<TaskPathList>> positiveResponseEntity = new ResponseEntity<>(positiveApiResponse, HttpStatus.OK);
        Mockito.doReturn(positiveResponseEntity).when(restTemplate).exchange(positiveRequestEntity, new ParameterizedTypeReference<ApiResponse<TaskPathList>>() {
        });

        URI internalServerUri = UriComponentsBuilder.newInstance()
                .scheme(testListBuilderCypressProtocol)
                .host(testListBuilderCypressHost)
                .path("tests/")
                .path("getTestPathListFromGitlab")
                .port(testListBuilderCypressPort)
                .queryParam("projectIdGitlab", 0)
                .queryParam("ref", "500")
                .build()
                .toUri();
        RequestEntity<Object> internalServerRequestEntity = new RequestEntity<>(HttpMethod.GET, internalServerUri);
        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(restTemplate).exchange(internalServerRequestEntity, new ParameterizedTypeReference<ApiResponse<TaskPathList>>() {
        });

        URI notFoundServerUri = UriComponentsBuilder.newInstance()
                .scheme(testListBuilderCypressProtocol)
                .host(testListBuilderCypressHost)
                .path("tests/")
                .path("getTestPathListFromGitlab")
                .port(testListBuilderCypressPort)
                .queryParam("projectIdGitlab", 0)
                .queryParam("ref", "404")
                .build()
                .toUri();
        RequestEntity<Object> notFoundRequestEntity = new RequestEntity<>(HttpMethod.GET, notFoundServerUri);
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(restTemplate).exchange(notFoundRequestEntity, new ParameterizedTypeReference<ApiResponse<TaskPathList>>() {
        });
    }

    @Test
    void getParameterList_DataIsValid_ReturnsValidResponse() {
        TaskPathList taskPathList = testListBuilderCypressClient.getTaskPathList(0, "positive");
        List<TaskPath> expectedList = new ArrayList<>();
        expectedList.add(new TaskPath("cypress/e2e/_common"));
        assertNotNull(taskPathList);
        assertTrue(taskPathList.getTaskPathList().containsAll(expectedList));
    }

    @Test
    void getParameterList_testListBuilderReturnsError500_ThrowsException() {
        TestListBuilderFailException testListBuilderFailException = assertThrows(TestListBuilderFailException.class, () ->
                testListBuilderCypressClient.getTaskPathList(0, "500"));
        assertNotNull(testListBuilderFailException.getMessage());
    }

    @Test
    void getParameterList_testListBuilderReturnsError404_ThrowsException() {
        TestListBuilderFailException testListBuilderFailException = assertThrows(TestListBuilderFailException.class, () ->
                testListBuilderCypressClient.getTaskPathList(0, "404"));
        assertNotNull(testListBuilderFailException.getMessage());
    }
}
