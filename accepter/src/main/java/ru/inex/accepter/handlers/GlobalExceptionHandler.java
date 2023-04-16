package ru.inex.accepter.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.inex.accepter.entity.ApiResponse;
import ru.inex.accepter.exception.DbFailException;
import ru.inex.accepter.exception.NoProjectException;
import ru.inex.accepter.exception.TestListBuilderFailException;
import ru.inex.accepter.service.ApiResponseBuilder;

import static ru.inex.accepter.Constants.INTERNAL_SERVER_ERROR;

/**
 * Перехватывает всех исключения, возникающие в сервисе, формирует ответ для ControlCenter при их возникновении
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final ApiResponseBuilder apiResponseBuilder;

    public GlobalExceptionHandler(@Autowired ApiResponseBuilder apiResponseBuilder) {
        this.apiResponseBuilder = apiResponseBuilder;
    }

    @ExceptionHandler(NoProjectException.class)
    public ResponseEntity<ApiResponse<String>> projectNotFound(NoProjectException noProjectException) {
        log.error(noProjectException.getMessage());
        return apiResponseBuilder.getErrorResponse(noProjectException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TestListBuilderFailException.class)
    public ResponseEntity<ApiResponse<String>> testListBuilderFailed(TestListBuilderFailException testListBuilderFailException) {
        log.error(testListBuilderFailException.getMessage());
        return apiResponseBuilder.getErrorResponse(testListBuilderFailException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DbFailException.class)
    public ResponseEntity<ApiResponse<String>> dbRequestFailed(DbFailException dbFailException) {
        log.error(dbFailException.getMessage());
        return apiResponseBuilder.getErrorResponse(dbFailException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiResponse<String>> internalServerError(Throwable throwable) {
        log.error(throwable.getMessage());
        return apiResponseBuilder.getErrorResponse(INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
