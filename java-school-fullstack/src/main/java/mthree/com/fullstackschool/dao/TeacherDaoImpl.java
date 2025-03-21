package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.StudentMapper;
import mthree.com.fullstackschool.dao.mappers.TeacherMapper;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    public TeacherDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Teacher createNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE
        final String CREATE_TEACHER = "INSERT INTO teacher (tFName, tLName) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {PreparedStatement ps = con.prepareStatement(CREATE_TEACHER, new String[]{"tid"});
            ps.setString(1, teacher.getTeacherFName());
            ps.setString(2, teacher.getTeacherLName());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        teacher.setTeacherId(key.intValue());

        return teacher;
        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE
        final String GET_ALL_TEACHERS = "SELECT * FROM teacher";
        return jdbcTemplate.query(GET_ALL_TEACHERS, new TeacherMapper());
        //YOUR CODE ENDS HERE
    }

    @Override
    public Teacher findTeacherById(int id) {
        //YOUR CODE STARTS HERE
        try {
            final String GET_TEACHER_BY_ID = "SELECT * FROM teacher WHERE tid = ?";
            return jdbcTemplate.queryForObject(GET_TEACHER_BY_ID, new TeacherMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateTeacher(Teacher t) {
        //YOUR CODE STARTS HERE
        final String UPDATE_TEACHER = "UPDATE teacher " +
                "SET tFName = ?, tLName = ? " +
                "WHERE tid = ?";
        jdbcTemplate.update(UPDATE_TEACHER,
                t.getTeacherFName(),
                t.getTeacherLName(),
                t.getTeacherId());
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteTeacher(int id) {
        //YOUR CODE STARTS HERE
        final String DELETE_TEACHER = "DELETE FROM teacher WHERE tid = ?";
        jdbcTemplate.update(DELETE_TEACHER, id);

        final String DELETE_TEACHER_FROM_COURSE = "UPDATE course " +
                "SET teacherId = NULL WHERE teacherId = ?";
        jdbcTemplate.update(DELETE_TEACHER_FROM_COURSE, id);
        //YOUR CODE ENDS HERE
    }
}
