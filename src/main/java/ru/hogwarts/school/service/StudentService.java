package ru.hogwarts.school.service;

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

    @Autowired
    public StudentService (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public void post(Student student){
        studentRepository.save(student);
    }

    public Student get(Long id1){
        return studentRepository.getReferenceById(id1);
    }

    public void remove(Long id1){
        studentRepository.deleteById(id1);
    }

    public List<Student> getOnAge(int age){
        return studentRepository.findByAge(age);
    }

    public List<Student> getAgeBetween(int min,int max){
        return studentRepository.findByAgeBetween(min,max);
    }

    public Faculty getFacultyOfStudent(Long id){
        return studentRepository.getReferenceById(id).getFaculty();
    }
}
