package ru.inex.accepter.repository;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.inex.accepter.entity.TaskList;
import ru.inex.accepter.entity.TaskStatus;

@Repository
@Data
public class TaskPtkRepositoryImpl implements TaskPtkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer saveTaskPtk(TaskList taskList) {

        Integer TEST_TASK_PTK_ID = jdbcTemplate.queryForObject(
                "INSERT INTO mcc.test_task_ptk(ptk_name, task_status_id) VALUES (?,?) returning id;",
                Integer.class,
                taskList.getTaskList().get(0).getPtkName(), TaskStatus.New.getStatusId());
        return TEST_TASK_PTK_ID;
    }
}
