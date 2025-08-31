package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private static Long id = 0L;

    public void add(Faculty faculty){
        facultyMap.put(id,faculty);
        id++;
    }

    public Faculty get(Long id1){
        return facultyMap.get(id1);
    }

    public void post(Faculty faculty, Long id1){
        facultyMap.put(id1,faculty);
    }

    public void remove(Long id1){
        facultyMap.remove(id1);
    }

    public List<Faculty> getByColor(String color){
        return facultyMap.values().stream().filter(faculty -> faculty.getColor().contains(color)).collect(Collectors.toList());
    }
}
