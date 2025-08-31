package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private FacultyService facultyService;

    FacultyController (FacultyService facultyService){
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> postFaculty(@RequestBody Long id , @RequestBody Faculty faculty){
        facultyService.post(faculty);
        return ResponseEntity.ok(facultyService.get(id));
    }

    @DeleteMapping
    public ResponseEntity<Faculty> deleteFaculty(@RequestBody Long id){
        facultyService.remove(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping
    public ResponseEntity<Faculty> putFaculty(@RequestBody Faculty faculty){
        facultyService.post(faculty);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<Faculty> getFaculty(@RequestBody Long id){
        return ResponseEntity.ok(facultyService.get(id));
    }

    @GetMapping("{color}")
    public ResponseEntity<List<Faculty>> getFacultyByColor(@PathVariable String color){
        return ResponseEntity.ok(facultyService.getByColor(color));
    }

}
