package ru.inex.accepter.repository;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.inex.accepter.entity.TestType;
import ru.inex.accepter.entity.TestTypeForGroup;

import java.util.List;

@Repository
@Data
public class TestTypeRepositoryImpl implements TestTypeRepository{

    private final JdbcTemplate jdbcTemplate;
    public Integer getIdByName(String name) {
        Integer Id;
        String sql = "SELECT id FROM mcc.test_type WHERE name=?";

        Id = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return Id;
    }

    public String getNameById(long id) {
        String name;
        String sql = "SELECT name FROM mcc.test_type WHERE id=?";

        name = jdbcTemplate.queryForObject(sql, String.class, id);
        return name;
    }
    public List<TestTypeForGroup> getTestTypeList(String groupNameGitlab) {
        Integer groupId = getGroupIdByName(groupNameGitlab);
        //String sql = "SELECT test_type_id, gitlab_project_id FROM mcc.gitlab_project_to_test WHERE group_id=?";
        String sql = "SELECT tt.name as test_type_name, gpt.gitlab_project_id as test_type_project_id FROM mcc.gitlab_project_to_test gpt, mcc.test_type tt WHERE gpt.group_id=? and gpt.gitlab_project_id is not null and gpt.test_type_id=tt.id";
        //List<Long> testTypeForGroupList_long = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Long.class), groupId);
        List<TestTypeForGroup> testTypeForGroupList = jdbcTemplate.query(sql,
                (rs, rowNum) -> new TestTypeForGroup(
                        TestType.valueOf(rs.getString("test_type_name")),
                        rs.getLong("test_type_project_id")),
                groupId);
        return testTypeForGroupList;
    }

    public Integer getGroupIdByName(String name) {
        Integer Id;
        String sql = "SELECT id FROM mcc.project WHERE name=?";
        //TODO перенести в отдельный GroupIdRepository
        Id = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return Id;
    }
}
