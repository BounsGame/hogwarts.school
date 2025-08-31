package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private Map<Long, Student> studentMap = new HashMap<>();
    private static Long id = 0L;

    public void add(Student student){
        studentMap.put(id,student);
        id++;
    }

    public Student get(Long id1){
        return studentMap.get(id1);
    }

    public void post(Student student, Long id1){
        studentMap.put(id1,student);
    }

    public void remove(Long id1){
        studentMap.remove(id1);
    }

    public List<Student> getOnAge(int age){
        List<Student> studentList = new ArrayList<>();
        studentMap.values().stream().mapToInt(e ->
        {if (e.getAge() == age) {
            studentList.add(e);
        }
        return 0;});
        return studentList;
    }
}
