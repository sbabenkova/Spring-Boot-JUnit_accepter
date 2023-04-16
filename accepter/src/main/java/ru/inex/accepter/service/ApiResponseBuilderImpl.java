package ru.inex.accepter.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.inex.accepter.entity.ApiResponse;
import ru.inex.accepter.entity.Result;

import java.time.LocalDateTime;

/**
 * Формирует ответы для контроллера и хэндлера
 */
@Component
@Data
@Slf4j
public class ApiResponseBuilderImpl implements ApiResponseBuilder {
    /**
     * Формат, в котором выводить корректный ответ сервиса
     * @param message ответ сервиса
     * @return ответ, содержащий json с корректными данными
     */
    public ResponseEntity<ApiResponse<String>> getOkResponse(String message) {
        ApiResponse<String> apiResponse = new ApiResponse<>(Result.OK, message, "", LocalDateTime.now());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    /**
     * Формат, в котором выводить некорректный ответ сервиса
     *
     * @param message сообщение, возвращаемое в отрицательном ответе
     * @param httpStatus статускод исключения, выбрасываемого сервисом
     * @return ответ, содержащий json с некорректными данными
     */
    public ResponseEntity<ApiResponse<String>> getErrorResponse(String message, HttpStatus httpStatus) {
        ApiResponse<String> apiResponse = new ApiResponse<>(Result.FAIL, "", message, LocalDateTime.now());
        return new ResponseEntity<>(apiResponse, httpStatus);
    }
}
