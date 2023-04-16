package ru.inex.accepter.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.inex.accepter.entity.ApiResponse;
import ru.inex.accepter.entity.RequestParams;
import ru.inex.accepter.service.AccepterService;
import ru.inex.accepter.service.ApiResponseBuilder;

@RestController
@Data
@Slf4j
@RequestMapping("/tests")
public class AccepterController {

    private AccepterService accepterService;
    private ApiResponseBuilder apiResponseBuilder;

    @Autowired
    public AccepterController(AccepterService accepterService, ApiResponseBuilder apiResponseBuilder) {
        this.accepterService = accepterService;
        this.apiResponseBuilder = apiResponseBuilder;
    }

    /**
     * Эндпойнт, получающий сообщение от ControlCenter с параметрами для создания задач
     *
     * @param ptkName          имя ПТК для тестов
     * @param groupNameGitlab  имя группы в гитлаб
     * @param gitRepositoryRef ветка
     * @param clusterName      имя кластера, в котором запускать тесты
     * @return сообщение об успехе.
     * В случае ошибок в приложении бросаются Exception и сообщение формируется в GlobalExceptionHandler
     */
    @GetMapping("/initAccepter")
    public ResponseEntity<ApiResponse<String>> processRequest(@RequestParam(name = "ptkName") String ptkName,
                                                      @RequestParam(name = "groupNameGitlab") String groupNameGitlab,
                                                      @RequestParam(name = "gitRepositoryRef") String gitRepositoryRef,
                                                      @RequestParam(name = "clusterName") String clusterName) {
        accepterService.processRequest(new RequestParams(ptkName, groupNameGitlab, gitRepositoryRef, clusterName));
        return apiResponseBuilder.getOkResponse("Tests started");
    }

    @GetMapping("/getStart")
    public String getStart(@RequestParam(name = "id") Integer id) {
        String str = "getStart/" + id + "/Test";
        log.info(str);
        return "getStart" + str;
    }

}
