package ru.inex.accepter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.inex.accepter.config.AppTestConfig;
import ru.inex.accepter.entity.RequestParams;
import ru.inex.accepter.exception.NoProjectException;
import ru.inex.accepter.exception.TestListBuilderFailException;
import ru.inex.accepter.service.AccepterService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AppTestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccepterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccepterService accepterService;

    @BeforeEach
    public void setup() {
        RequestParams requestParams400 = new RequestParams("400", "400", "400", "400");
        RequestParams requestParams500 = new RequestParams("500", "500", "500", "500");
        Mockito.doThrow(new TestListBuilderFailException("TestListBuilder failed")).when(accepterService)
                .processRequest(requestParams500);
        Mockito.doThrow(new NoProjectException("No project found")).when(accepterService)
                .processRequest(requestParams400);
    }

    @Test
    void processRequest_requestParamsIsValid_Returns200() throws Exception {
        mockMvc.perform(get("/tests/initAccepter").param("ptkName", "positive")
                        .param("groupNameGitlab", "positive")
                        .param("gitRepositoryRef", "positive")
                        .param("clusterName", "positive"))
                .andExpect(status().isOk());
    }

    @Test
    void processRequest_requestParamsIsInvalid_Returns500() throws Exception {
        mockMvc.perform(get("/tests/initAccepter").param("ptkName", "500")
                        .param("groupNameGitlab", "500")
                        .param("gitRepositoryRef", "500")
                        .param("clusterName", "500"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void processRequest_noProjectInGitLab_Returns400() throws Exception {
        mockMvc.perform(get("/tests/initAccepter").param("ptkName", "400")
                        .param("groupNameGitlab", "400")
                        .param("gitRepositoryRef", "400")
                        .param("clusterName", "400"))
                .andExpect(status().is4xxClientError());
    }
}