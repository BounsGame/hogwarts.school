package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private FacultyRepository facultyRepository;

    @Autowired
    public FacultyService (FacultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }

    public void post(Faculty faculty){
        facultyRepository.save(faculty);
    }

    public Faculty get(Long id1){
        return facultyRepository.getReferenceById(id1);
    }

    public void remove(Long id1){
        facultyRepository.deleteById(id1);
    }

    public List<Faculty> getByColor(String color){
        return facultyRepository.findByColor(color);
    }
}
