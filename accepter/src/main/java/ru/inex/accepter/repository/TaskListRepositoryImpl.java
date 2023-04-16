package ru.inex.accepter.repository;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.inex.accepter.entity.TaskList;
import ru.inex.accepter.entity.TaskStatus;

@Repository
@Data
public class TaskListRepositoryImpl implements TaskListRepository {
    private final JdbcTemplate jdbcTemplate;
private final TaskPtkRepository taskPtkRepository;
    @Override
    public void saveTaskList(TaskList taskList) {

         Integer TEST_TASK_PTK_ID = taskPtkRepository.saveTaskPtk(taskList);

        taskList.getTaskList().forEach(task -> {
            jdbcTemplate.update(
                    "INSERT INTO mcc.test_task(test_task_ptk_id, test_type_id, task_path, git_repository_ref, task_status_id) VALUES (?,?,?,?,?)",
                    TEST_TASK_PTK_ID, task.getTestType().getTestTypeId(), task.getTaskPath().getTaskPath(), task.getGitRepositoryRef(), TaskStatus.New.getStatusId());
        });
    }
}
