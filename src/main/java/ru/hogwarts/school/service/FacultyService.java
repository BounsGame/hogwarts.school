package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private FacultyRepository facultyRepository;
    private StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public void post(Faculty faculty) {
        logger.info("метод facultyPost запустился");
        facultyRepository.save(faculty);
    }

    public Faculty get(Long id1) {
        logger.info("метод facultyGet запустился");
        return facultyRepository.getReferenceById(id1);
    }

    public void remove(Long id1) {
        logger.info("метод facultyRemove запустился");
        facultyRepository.deleteById(id1);
    }

    public List<Faculty> getByColorOrName(String name, String color) {
        logger.info("метод получения факультета по цвету или имени запустился");
        if (name != null) {
            return facultyRepository.findByNameOrColorIgnoreCase(name, color);
        }
        return facultyRepository.findByColor(color);
    }

    public void linkFacultyWithStudent(Long facultyId, Long studentId) {
        logger.info("метод связывания студента с факультетом запустился");
        Faculty faculty = facultyRepository.getReferenceById(facultyId);
        Student student = studentRepository.getReferenceById(studentId);
        faculty.addStudentList(student);
        student.setFaculty(faculty);
        facultyRepository.save(faculty);
        studentRepository.save(student);
    }

    public Set<Student> getStudentListByFacultyId(Long id) {
        logger.info("метод получения студентов факультета запустился");
        return facultyRepository.getReferenceById(id).getStudentList();
    }

    public Faculty getIdByFaculty(Faculty faculty) {
        logger.info("метод получения id факультета запустился");
        return facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor());
    }

    public Optional<String> longestName (){
        List<Faculty> facultyList = facultyRepository.findAll();
        return facultyList.stream().map(Faculty::getName).parallel().max(Comparator.comparing(String::length));
    }
}
