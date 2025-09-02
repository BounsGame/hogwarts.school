package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;

    public StudentController (StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> postStudent(@RequestBody Long id , @RequestBody Student student){
        studentService.post(student);
        return ResponseEntity.ok(studentService.get(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id){
        studentService.remove(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping
    public ResponseEntity<Student> putStudent(@RequestBody Student student){
        studentService.post(student);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Student> getStudent(@RequestParam Long id){
        return ResponseEntity.ok(studentService.get(id));
    }

    @GetMapping("{age}")
    public ResponseEntity<List<Student>> getStudentInAge (@PathVariable int age){
        return ResponseEntity.ok(studentService.getOnAge(age));
    }
}
