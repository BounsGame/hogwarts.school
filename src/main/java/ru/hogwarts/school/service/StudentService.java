package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void post(Student student) {
        logger.info("метод studentPost запустился");
        studentRepository.save(student);
    }

    public Student get(Long id1) {
        logger.info("метод studentGet запустился");
        return studentRepository.getReferenceById(id1);
    }

    public void remove(Long id1) {
        logger.info("метод studentRemove запустился");
        studentRepository.deleteById(id1);
    }

    public List<Student> getOnAge(int age) {
        logger.info("метод получения студентов по возрасту запустился");
        return studentRepository.findByAge(age);
    }

    public List<Student> getAgeBetween(int min, int max) {
        logger.info("метод получения студентов с возрастом в пределах диапазона запустился");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyOfStudent(Long id) {
        logger.info("метод получения факультета студента запустился");
        return studentRepository.getReferenceById(id).getFaculty();
    }

    public Student findIdByStud(Student student) {
        logger.info("метод получения id студента запустился");
        return studentRepository.findByAgeAndName(student.getAge(), student.getName());
    }

    public Long countStudents() {
        logger.info("метод подсчёта студентов запустился");
        return studentRepository.countAllStudents();
    }

    public Long averageAgeOfStudents() {
        logger.info("метод подсчёта среднего возраста студентов запустился");
        return studentRepository.averageAgeOfStudents();
    }

    public List<Student> last5Students() {
        logger.info("метод получения последних 5 зачисленых студентов запустился");
        return studentRepository.last5Stud();
    }

    public List<String> allNameOfStudentsWhoStartsOnA() {
        List<Student> stud = studentRepository.findAll();
        return stud.stream().map(Student::getName).parallel().filter(w -> w.startsWith("A")).sorted()
                .collect(Collectors.toList());
    }

    public OptionalDouble averageAgeThroughStream() {
        List<Student> stud = studentRepository.findAll();
        return stud.stream().map(Student::getAge).parallel().mapToInt(Integer::intValue).average();
    }

    public List<Student> sixStud() {
        return studentRepository.sixStudents();
    }

    public void printParallel() {

        System.out.println(sixStud().get(0).getName());
        System.out.println(sixStud().get(1).getName());

        new Thread(() -> printParallel2()).start();
        new Thread(() -> printParallel3()).start();
    }

    public void printParallel2() {
        System.out.println(sixStud().get(2).getName());
        System.out.println(sixStud().get(3).getName());
    }

    public void printParallel3() {
        System.out.println(sixStud().get(4).getName());
        System.out.println(sixStud().get(5).getName());
    }

    public synchronized void printName(String name){
        System.out.println(name);
    }

    public void printSynchronized(){
        List<Student> students = studentRepository.sixStudents();

        printName(students.get(0).getName());
        printName(students.get(1).getName());

        Thread thread1 = new Thread(()-> {
            printName(students.get(2).getName());
            printName(students.get(3).getName());
        });
        thread1.start();

        Thread thread2 = new Thread(()-> {
            printName(students.get(4).getName());
            printName(students.get(5).getName());
        });
        thread2.start();
    }
}
