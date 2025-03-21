package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Course;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    //YOUR CODE STARTS HERE
    private StudentDao studentDao;

    @Autowired
    CourseServiceInterface courseService;

    StudentServiceImpl (StudentDao studentDao) {
        this.studentDao = studentDao;
    }
    //YOUR CODE ENDS HERE

    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE
        return studentDao.getAllStudents();
        //YOUR CODE ENDS HERE
    }

    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE
        Student student = studentDao.findStudentById(id);

        if (student == null) {
            student = new Student();
            student.setStudentFirstName("Student Not Found");
            student.setStudentLastName("Student Not Found");
        }

        return student;
        //YOUR CODE ENDS HERE
    }

    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE
        if (student.getStudentFirstName().isEmpty()) {
            student.setStudentFirstName("First Name blank, student NOT added");
        }

        if (student.getStudentLastName().isEmpty()) {
            student.setStudentLastName("Last Name blank, student NOT added");
        }

        return studentDao.createNewStudent(student);
        //YOUR CODE ENDS HERE
    }

    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE
        if (id != student.getStudentId()) {
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
        } else {
            studentDao.updateStudent(student);
        }

        return student;
        //YOUR CODE ENDS HERE
    }

    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE
        studentDao.deleteStudent(id);
        //YOUR CODE ENDS HERE
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        if (getStudentById(studentId).getStudentFirstName().equals("Student Not Found")) {
            System.out.println("Student not found");
        }
        else if (courseService.getCourseById(courseId).getCourseName().equals("Course Not Found")) {
            System.out.println("Course not found");
        }
        else {
            studentDao.deleteStudentFromCourse(studentId, courseId);
            System.out.println("Student: " + studentId + " deleted from course: " + courseId);
        }
        //YOUR CODE ENDS HERE
    }

    public void addStudentToCourse(int studentId, int courseId) throws DataAccessException {
        //YOUR CODE STARTS HERE
        if (getStudentById(studentId).getStudentFirstName().equals("Student Not Found")) {
            System.out.println("Student not found");
        }
        else if (courseService.getCourseById(courseId).getCourseName().equals("Course Not Found")) {
            System.out.println("Course not found");
        }
        else {
            try{
                studentDao.addStudentToCourse(studentId, courseId);
                System.out.println("Student: " + studentId + " added to course: " + courseId);
            } catch (DataAccessException e) {
                System.out.println("Student: " + studentId + " already enrolled in course " + courseId);
            }
        }
        //YOUR CODE ENDS HERE
    }
}
