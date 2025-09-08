package ru.hogwarts.school;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import javax.net.ssl.SSLEngineResult;
import javax.sound.sampled.Port;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {
    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoSpyBean
    private StudentService studentService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextNotNull() {
        Assertions.assertNotNull(studentController);
    }

    @Test
    public void postStudentTest() throws Exception {
        String name = "danil";
        long id = 1;
        int age = 21;
        Student student = new Student(name, age);
        JSONObject studentJson = new JSONObject();
        studentJson.put("id", id);
        studentJson.put("name", name);
        studentJson.put("age", age);
        student.setId(id);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findByAgeAndName(any(int.class), any(String.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/student").content(studentJson.toString())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name)).andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void deleteMapTest() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}", id))
                .andExpect(status().isOk());
        verify(studentRepository).deleteById(any(Long.class));
    }

    @Test
    public void putMapTest() throws Exception {
        String name = "danil";
        long id = 1;
        int age = 21;
        Student student = new Student(name, age);
        JSONObject studentJson = new JSONObject();
        studentJson.put("id", id);
        studentJson.put("name", name);
        studentJson.put("age", age);
        student.setId(id);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/student").content(studentJson.toString())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name)).andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getMapFindTest() throws Exception {
        String name = "danil";
        long id = 1;
        int age = 21;
        Student student = new Student(name, age);
        JSONObject studentJson = new JSONObject();
        studentJson.put("id", id);
        studentJson.put("name", name);
        studentJson.put("age", age);
        student.setId(id);

        when(studentRepository.getReferenceById(any(Long.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/find?id=1").content(studentJson.toString())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name)).andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getAllStudByAgeBetweenTest() throws Exception {
        String name1 = "danil";
        Long id1 = 1L;
        int age1 = 21;
        String name2 = "dima";
        Long id2 = 2L;
        int age2 = 30;
        Student student1 = new Student(name1, age1);
        student1.setId(id1);
        Student student2 = new Student(name2, age2);
        student2.setId(id2);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepository.findByAgeBetween(any(int.class), any(int.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student?min=20&&max=40").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", contains(Math.toIntExact(id1), Math.toIntExact(id2))))
                .andExpect(jsonPath("$[*].name", contains(name1, name2)))
                .andExpect(jsonPath("$[*].age", contains(age1, age2)));
    }

    @Test
    public void getStudInAgeTest() throws Exception {
        String name1 = "danil";
        Long id1 = 1L;
        int age1 = 21;
        String name2 = "dima";
        Long id2 = 2L;
        int age2 = 21;
        Student student1 = new Student(name1, age1);
        student1.setId(id1);
        Student student2 = new Student(name2, age2);
        student2.setId(id2);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepository.findByAge(any(int.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{age}", 21).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", contains(Math.toIntExact(id1), Math.toIntExact(id2))))
                .andExpect(jsonPath("$[*].name", contains(name1, name2)))
                .andExpect(jsonPath("$[*].age", contains(age1, age2)));
    }

    @Test
    public void getFacultyOfStudTest() throws Exception {
        long id = 1L;
        String name1 = "danil";
        int age1 = 21;
        Faculty faculty = new Faculty("ravenclaw", "blue");
        faculty.setId(id);
        Student student = new Student(name1, age1);
        student.setId(id);
        student.setFaculty(faculty);

        when(studentRepository.getReferenceById(any(Long.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getFacultyOfStudent?id=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("ravenclaw"))
                .andExpect(jsonPath("$.color").value("blue"));
    }
}
