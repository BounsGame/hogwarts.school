package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;

    public StudentController (StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> postStudent(@RequestBody Student student){
        studentService.post(student);
        return ResponseEntity.ok(studentService.findIdByStud(student));
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

    @GetMapping("/find")
    public ResponseEntity<Student> findStudent(@RequestParam Long id){
            return ResponseEntity.ok(studentService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<Student>> findStudents(@RequestParam(required = false) int min,
                                                      @RequestParam(required = false) int max){
        return ResponseEntity.ok(studentService.getAgeBetween(min,max));
    }

    @GetMapping("{age}")
    public ResponseEntity<List<Student>> getStudentInAge (@PathVariable int age){
        return ResponseEntity.ok(studentService.getOnAge(age));
    }

    @GetMapping("/getFacultyOfStudent")
    public ResponseEntity<Faculty> facultyOfStudent (@RequestParam Long id){
        return ResponseEntity.ok(studentService.getFacultyOfStudent(id));
    }

    @GetMapping("/countStudents")
    public ResponseEntity<Optional> countStudents(){
        return ResponseEntity.ok(Optional.of(studentService.countStudents()));
    }

    @GetMapping("/AverageAge")
    public ResponseEntity<Optional> AverageAgeOfStudents(){
        return ResponseEntity.ok(Optional.of(studentService.averageAgeOfStudents()));
    }

    @GetMapping("/last5")
    public ResponseEntity<List<Student>> last5Students (){
        return ResponseEntity.ok(studentService.last5Students());
    }

    @GetMapping("/studentsWithNameOnA")
    public ResponseEntity<List<String>> studentsWithNameOnA (){
        return ResponseEntity.ok(studentService.allNameOfStudentsWhoStartsOnA());
    }

    @GetMapping("/avarageAgeThroughStream")
    public ResponseEntity<OptionalDouble> avarageAgeThroughStream (){
        return ResponseEntity.ok(studentService.averageAgeThroughStream());
    }

    @GetMapping("/print-parallel")
    public void printParallel (){
        studentService.printParallel();
    }

    @GetMapping("/print-synchronized")
    public void printSynchronized (){
        studentService.printSynchronized();
    }
}
