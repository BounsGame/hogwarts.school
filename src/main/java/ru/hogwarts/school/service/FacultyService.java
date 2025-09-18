package ru.hogwarts.school.service;

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

    @Autowired
    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public void post(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    public Faculty get(Long id1) {
        return facultyRepository.getReferenceById(id1);
    }

    public void remove(Long id1) {
        facultyRepository.deleteById(id1);
    }

    public List<Faculty> getByColorOrName(String name, String color) {
        if (name != null) {
            return facultyRepository.findByNameOrColorIgnoreCase(name, color);
        }
        return facultyRepository.findByColor(color);
    }

    public void linkFacultyWithStudent(Long facultyId, Long studentId) {
        Faculty faculty = facultyRepository.getReferenceById(facultyId);
        Student student = studentRepository.getReferenceById(studentId);
        faculty.addStudentList(student);
        student.setFaculty(faculty);
        facultyRepository.save(faculty);
        studentRepository.save(student);
    }

    public Set<Student> getStudentListByFacultyId(Long id) {
        return facultyRepository.getReferenceById(id).getStudentList();
    }

    public Faculty getIdByFaculty(Faculty faculty) {
        return facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor());
    }
}
