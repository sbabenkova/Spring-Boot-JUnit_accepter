package ru.inex.accepter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskPtk {
    //private long ptkNameId;
    private String ptkName;
    private TaskStatus taskStatus;
}
