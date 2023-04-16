package ru.inex.accepter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс, использующийся для обмена сообщениями
 * @param <D> передаваемые в сообщении данные. В данном приложении это String
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApiResponse<D> {
    private Result result;
    private D data;
    private String error;
    private LocalDateTime timestamp;
}
