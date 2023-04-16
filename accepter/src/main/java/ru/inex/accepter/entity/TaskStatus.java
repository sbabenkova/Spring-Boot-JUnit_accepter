package ru.inex.accepter.entity;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum TaskStatus {
    New(1),
    Done(2),
    InProgress(3),
    Failed(4);

    private Integer statusId;

    public Integer getStatusId() {
        return statusId;
    }
}
