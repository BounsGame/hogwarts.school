package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import javax.net.ssl.SSLEngineResult;
import javax.sound.sampled.Port;

import java.util.*;

import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FacultyControllerTest {
    @MockitoBean
    private FacultyRepository facultyRepository;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoSpyBean
    private FacultyService facultyService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextTest() {
        Assertions.assertNotNull(facultyController);
    }

    //MockMvc
    @Test
    public void postFaculty() throws Exception {
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("color", color);

        when(facultyRepository.save(faculty)).thenReturn(faculty);
        when(facultyRepository.findByNameAndColor(any(String.class), any(String.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty").content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/{id}", id)).andExpect(status().isOk());
        verify(facultyRepository).deleteById(any(Long.class));
    }

    @Test
    public void putFacultyTest() throws Exception {
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("color", color);

        when(facultyRepository.save(faculty)).thenReturn(faculty);
        when(facultyRepository.findByNameAndColor(any(String.class), any(String.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty").content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void getFacultyByIdTest() throws Exception {
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);

        when(facultyRepository.getReferenceById(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void getFacultyByNameOrColor() throws Exception {
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);
        Long id2 = 2L;
        String name2 = "gryffindor";
        String color2 = "red";
        Faculty faculty2 = new Faculty(name2, color2);
        faculty2.setId(id2);
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(faculty);
        facultyList.add(faculty2);

        when(facultyRepository.findByNameOrColorIgnoreCase(any(String.class), any(String.class))).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty?name=ravenclaw&&color=red").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].id", contains(Math.toIntExact(id), Math.toIntExact(id2))))
                .andExpect(jsonPath("$[*].name", contains(name, name2)))
                .andExpect(jsonPath("$[*].color", contains(color, color2)));
    }

    @Test
    public void getStudentListTest() throws Exception {
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);
        Set<Student> studentList = new HashSet<>();
        String studentName1 = "danil";
        String studentName2 = "ilya";
        int age1 = 21;
        int age2 = 22;
        Long studId1 = 1L;
        Long studId2 = 2L;
        Student student1 = new Student(studentName1, age1);
        student1.setId(studId1);
        Student student2 = new Student(studentName2, age2);
        student2.setId(studId2);
        studentList.add(student1);
        studentList.add(student2);
        faculty.setStudentList(studentList);

        when(facultyRepository.getReferenceById(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/getFacultyStudents?id=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].id", contains(Math.toIntExact(studId1), Math.toIntExact(studId2))))
                .andExpect(jsonPath("$[*].name", contains(studentName1, studentName2)))
                .andExpect(jsonPath("$[*].age", contains(age1, age2)));
    }

    @Test
    public void linkTest() throws Exception {
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);
        Set<Student> studentSet = new HashSet<>();
        faculty.setStudentList(studentSet);
        String studentName1 = "danil";
        int age1 = 21;
        Long studId1 = 1L;
        Student student1 = new Student(studentName1, age1);
        student1.setId(studId1);

        when(facultyRepository.getReferenceById(any(Long.class))).thenReturn(faculty);
        when(studentRepository.getReferenceById(any(Long.class))).thenReturn(student1);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(studentRepository.save(any(Student.class))).thenReturn(student1);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/link?facultyId=1&&studentId=1"))
                .andExpect(status().isOk());

        verify(facultyRepository).save(any(Faculty.class));
        verify(studentRepository).save(any(Student.class));
    }

    //TestRestTemplate

    @Test
    public void postFacultyTest(){
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        HttpEntity<Faculty> request = new HttpEntity<>(faculty);
        faculty.setId(id);

        when(facultyRepository.save(faculty)).thenReturn(faculty);
        when(facultyRepository.findByNameAndColor(any(String.class), any(String.class))).thenReturn(faculty);

        ResponseEntity<Faculty> response = testRestTemplate
                .exchange("http://localhost:" + port + "/faculty"
                ,HttpMethod.POST
                ,request
                ,Faculty.class);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(faculty,response.getBody());
    }

    @Test
    public void deleteFaculty(){
        ResponseEntity<Faculty> response = testRestTemplate
                .exchange("http://localhost:" + port + "/faculty/{id}"
                        ,HttpMethod.DELETE
                        ,null
                        ,Faculty.class
                        ,1);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());

        verify(facultyService).remove(any(Long.class));
    }

    @Test
    public void putFaculty(){
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);
        HttpEntity<Faculty> request = new HttpEntity<>(faculty);

        when(facultyRepository.save(faculty)).thenReturn(faculty);
        when(facultyRepository.findByNameAndColor(any(String.class), any(String.class))).thenReturn(faculty);

        ResponseEntity<Faculty> response = testRestTemplate
                .exchange("http://localhost:" + port + "/faculty"
                        ,HttpMethod.PUT
                        ,request
                        ,Faculty.class);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(faculty,response.getBody());
    }

    @Test
    public void getFacultyTest(){
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);

        when(facultyRepository.getReferenceById(any(Long.class))).thenReturn(faculty);

        ResponseEntity<Faculty> response = testRestTemplate
                .exchange("http://localhost:" + port + "/faculty/{id}"
                        ,HttpMethod.GET
                        ,null
                        ,Faculty.class
                        ,id);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(faculty,response.getBody());
    }

    @Test
    public void getFacultyByColorTest(){
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);
        Long id2 = 2L;
        String name2 = "gryffindor";
        String color2 = "red";
        Faculty faculty2 = new Faculty(name2, color2);
        faculty2.setId(id2);
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(faculty);
        facultyList.add(faculty2);

        when(facultyRepository.findByNameOrColorIgnoreCase(any(String.class), any(String.class))).thenReturn(facultyList);

        ResponseEntity<List<Faculty>> response = testRestTemplate
                .exchange("http://localhost:" + port + "/faculty?name=ravenclaw&&color=red"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<List<Faculty>>() {
                        });

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(facultyList,response.getBody());
    }

    @Test
    public void linkStudentWithFacultyTest(){
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);
        Set<Student> studentSet = new HashSet<>();
        faculty.setStudentList(studentSet);
        String studentName1 = "danil";
        int age1 = 21;
        Long studId1 = 1L;
        Student student1 = new Student(studentName1, age1);
        student1.setId(studId1);

        when(facultyRepository.getReferenceById(any(Long.class))).thenReturn(faculty);
        when(studentRepository.getReferenceById(any(Long.class))).thenReturn(student1);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(studentRepository.save(any(Student.class))).thenReturn(student1);

        ResponseEntity<Faculty> response = testRestTemplate
                .exchange("http://localhost:" + port + "/faculty/link?facultyId=1&&studentId=1"
                        , HttpMethod.PUT
                        , null
                        , Faculty.class);

        verify(facultyRepository).save(any(Faculty.class));
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    public void getFacultyStudentsTest(){
        Long id = 1L;
        String name = "ravenclaw";
        String color = "blue";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);
        Set<Student> studentList = new HashSet<>();
        String studentName1 = "danil";
        String studentName2 = "ilya";
        int age1 = 21;
        int age2 = 22;
        Long studId1 = 1L;
        Long studId2 = 2L;
        Student student1 = new Student(studentName1, age1);
        student1.setId(studId1);
        Student student2 = new Student(studentName2, age2);
        student2.setId(studId2);
        studentList.add(student1);
        studentList.add(student2);
        faculty.setStudentList(studentList);

        when(facultyRepository.getReferenceById(any(Long.class))).thenReturn(faculty);

        ResponseEntity<Set<Student>> response = testRestTemplate
                .exchange("http://localhost:" + port + "/faculty/getFacultyStudents?id=1"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<Set<Student>>() {
                        });

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(studentList,response.getBody());
    }
}
