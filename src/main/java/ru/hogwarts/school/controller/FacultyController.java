package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Set;

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

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id){
        facultyService.remove(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping
    public ResponseEntity<Faculty> putFaculty(@RequestBody Faculty faculty){
        facultyService.post(faculty);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id){
        return ResponseEntity.ok(facultyService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getFacultyByColor(@RequestParam(required = false) String color,
                                                           @RequestParam(required = false) String name){
        return ResponseEntity.ok(facultyService.getByColorOrName(name,color));
    }

    @PutMapping("/link")
    public ResponseEntity<Faculty> linkStudentWithFaculty(@RequestParam Long facultyId, @RequestParam Long studentId){
        facultyService.linkFacultyWithStudent(facultyId,studentId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/getFacultyStudents")
    public ResponseEntity<Set<Student>> getFacultyStudents(@RequestParam Long id){
        return ResponseEntity.ok(facultyService.getStudentListByFacultyId(id));
    }

}
