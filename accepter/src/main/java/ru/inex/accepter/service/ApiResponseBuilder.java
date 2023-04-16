package ru.inex.accepter.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.inex.accepter.entity.ApiResponse;

/**
 * интерфейс для построения ответа контроллера/хэндлера
 */
public interface ApiResponseBuilder {
    ResponseEntity<ApiResponse<String>> getOkResponse(String message);
    ResponseEntity<ApiResponse<String>> getErrorResponse(String message, HttpStatus httpStatus);
}
