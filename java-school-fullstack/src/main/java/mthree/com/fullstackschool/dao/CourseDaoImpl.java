package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.CourseMapper;
import mthree.com.fullstackschool.model.Course;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {

    private final JdbcTemplate jdbcTemplate;

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course createNewCourse(Course course) {
        //YOUR CODE STARTS HERE
        final String INSERT_NEW_COURSE = "INSERT INTO course (courseCode, courseDesc) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {PreparedStatement ps = con.prepareStatement(INSERT_NEW_COURSE, new String[]{"tid"});
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getCourseDesc());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        course.setCourseId(key.intValue());

        return course;
        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE
        final String SELECT_ALL_COURSES = "SELECT * FROM course";

        return jdbcTemplate.query(SELECT_ALL_COURSES, new CourseMapper());

        //YOUR CODE ENDS HERE
    }

    @Override
    public Course findCourseById(int id) throws DataAccessException {
        //YOUR CODE STARTS HERE
        try {
            final String SELECT_COURSE_BY_ID = "SELECT * FROM course WHERE cid = ?";
            return jdbcTemplate.queryForObject(SELECT_COURSE_BY_ID, new CourseMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateCourse(Course course) {
        //YOUR CODE STARTS HERE
        final String UPDATE_COURSE = "UPDATE course " +
                "SET courseCode = ?, courseDesc = ?, teacherId = ?" +
                " WHERE cid = ?";

        jdbcTemplate.update(UPDATE_COURSE,
                        course.getCourseName(),
                        course.getCourseDesc(),
                        course.getTeacherId(),
                        course.getCourseId());
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteCourse(int id) {
        //YOUR CODE STARTS HERE
        final String DELETE_COURSE = "DELETE FROM course WHERE cid = ?";
        jdbcTemplate.update(DELETE_COURSE, id);
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteAllStudentsFromCourse(int courseId) {
        //YOUR CODE STARTS HERE
        final String DELETE_STUDENTS_FROM_COURSE = "DELETE FROM course_student WHERE course_id = ?";
        jdbcTemplate.update(DELETE_STUDENTS_FROM_COURSE, courseId);
        //YOUR CODE ENDS HERE
    }
}
