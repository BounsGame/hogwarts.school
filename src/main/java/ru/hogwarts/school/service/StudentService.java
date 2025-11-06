package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void post(Student student) {
        logger.info("метод studentPost запустился");
        studentRepository.save(student);
    }

    public Student get(Long id1) {
        logger.info("метод studentGet запустился");
        return studentRepository.getReferenceById(id1);
    }

    public void remove(Long id1) {
        logger.info("метод studentRemove запустился");
        studentRepository.deleteById(id1);
    }

    public List<Student> getOnAge(int age) {
        logger.info("метод получения студентов по возрасту запустился");
        return studentRepository.findByAge(age);
    }

    public List<Student> getAgeBetween(int min, int max) {
        logger.info("метод получения студентов с возрастом в пределах диапазона запустился");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyOfStudent(Long id) {
        logger.info("метод получения факультета студента запустился");
        return studentRepository.getReferenceById(id).getFaculty();
    }

    public Student findIdByStud(Student student) {
        logger.info("метод получения id студента запустился");
        return studentRepository.findByAgeAndName(student.getAge(), student.getName());
    }

    public Long countStudents(){
        logger.info("метод подсчёта студентов запустился");
        return studentRepository.countAllStudents();
    }

    public Long averageAgeOfStudents(){
        logger.info("метод подсчёта среднего возраста студентов запустился");
        return studentRepository.averageAgeOfStudents();
    }

    public List<Student> last5Students (){
        logger.info("метод получения последних 5 зачисленых студентов запустился");
        return studentRepository.last5Stud();
    }
}
