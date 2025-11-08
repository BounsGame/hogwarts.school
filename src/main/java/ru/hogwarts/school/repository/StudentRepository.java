package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    Student findByAgeAndName(int age, String name);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Long countAllStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Long averageAgeOfStudents ();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5",nativeQuery = true)
    List<Student> last5Stud();

    @Query(value = "SELECT * FROM student LIMIT 6", nativeQuery = true)
    List<Student> sixStudents();
}
