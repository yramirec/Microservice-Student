package com.everis.controller;

import static org.mockito.Mockito.when;

import com.everis.model.Student;
import com.everis.service.StudentService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
class StudentControllerTest {

  @Mock
  private StudentService studentService;
  private WebTestClient client;
  private List<Student> expectedStudents;

  @BeforeEach
   void setUp() {
    client = WebTestClient.bindToController(new StudentController(studentService)).configureClient()
    .baseUrl("/students").build();

    expectedStudents = Arrays.asList(
     Student.builder().id("1").fullName("Juan").gender("Masculino").dateOfBirth(new Date())
     .typeDocument("dni").numberDocument(109999).idFamily("Suarez").build(),
    Student.builder().id("2").fullName("Mariana").gender("Femenino").dateOfBirth(new Date())
    .typeDocument("dni").numberDocument(259999).idFamily("Suarez").build(),
    Student.builder().id("3").fullName("Adrian").gender("Masculino").dateOfBirth(new Date())
    .typeDocument("Carnet").numberDocument(79999).idFamily("Suarez").build());
  }

  @Test
   void getAllStudents() {
    when(studentService.findAll()).thenReturn(Flux.fromIterable(expectedStudents));

    client.get().uri("/").exchange().expectStatus().isOk().expectBodyList(Student.class)
    .isEqualTo(expectedStudents);
  }

  @Test
   void getStudentById_whenStudentExists_returnCorrectStudent() {
    Student expectedStudent = expectedStudents.get(0);

    when(studentService.findById(expectedStudent.getId())).thenReturn(Mono.just(expectedStudent));

    client.get().uri("/{id}", expectedStudent.getId())
    .exchange().expectStatus().isOk().expectBody(Student.class)
    .isEqualTo(expectedStudent);
  }

  @Test
   void getStudentById_whenStudentNotExist_returnNotFound() {
    String id = "NOT_EXIST_ID";
    when(studentService.findById(id)).thenReturn(Mono.empty());

    client.get().uri("/{id}", id).exchange()
    .expectStatus().isNotFound();
  }

  @Test
   void addStudent() {
    Student expectedStudent = expectedStudents.get(0);
    when(studentService.create(expectedStudent)).thenReturn(Mono.just(expectedStudent));

    client.post().uri("/").body(Mono.just(expectedStudent), Student.class).exchange()
    .expectStatus().isCreated()
    .expectBody(Student.class).isEqualTo(expectedStudent);
  }

  @Test
   void updateStudent_whenStudentExists_performUpdate() {
    Student expectedStudent = expectedStudents.get(0);
    when(studentService.update(expectedStudent.getId(), expectedStudent))
      .thenReturn(Mono.just(expectedStudent));

    client.put().uri("/{id}", expectedStudent.getId())
    .body(Mono.just(expectedStudent), Student.class).exchange()
    .expectStatus().isOk()
    .expectBody(Student.class).isEqualTo(expectedStudent);
  }

  @Test
   void updateStudent_whenStudentNotExist_returnNotFound() {
    String id = "NOT_EXIST_ID";
    Student expectedStudent = expectedStudents.get(0);
    when(studentService.update(id, expectedStudent))
      .thenReturn(Mono.empty());

    client.put().uri("/{id}", id).body(Mono.just(expectedStudent), Student.class)
    .exchange()
    .expectStatus().isNotFound();
  }

  @Test
   void deleteStudent_whenStudentExists_performDeletion() {
    Student studentToDelete = expectedStudents.get(0);
    when(studentService.deleteById(studentToDelete.getId()))
      .thenReturn(Mono.just(studentToDelete));

    client.delete().uri("/{id}", studentToDelete.getId()).exchange()
    .expectStatus().isOk();
  }
  
  @Test
   void deleteStudent_whenIdNotExist_returnNotFound() {
    Student studentToDelete = expectedStudents.get(0);
    when(studentService.deleteById(studentToDelete.getId()))
      .thenReturn(Mono.empty());

    client.delete().uri("/{id}", studentToDelete.getId()).exchange()
    .expectStatus().isNotFound();
  }

  @Test
   void searchByName() {
    String fullName = "Mariana";
    List<Student> expectedFilteredStudents = Arrays.asList(expectedStudents.get(0), 
        expectedStudents.get(1));
    when(studentService.findByFullName(fullName))
      .thenReturn(Flux.fromIterable(expectedFilteredStudents));
    
    client.get().uri("/searchByName/{fullName}", fullName).exchange()
    .expectStatus().isOk()
    .expectBodyList(Student.class).isEqualTo(expectedFilteredStudents);
  }

  @Test
   void searchByDocument() {
    int numberDocument = 109999;
    List<Student> expectedFilteredStudents = Arrays.asList(expectedStudents.get(0), 
        expectedStudents.get(1));
    when(studentService.findByNumberDocument(numberDocument))
      .thenReturn(Flux.fromIterable(expectedFilteredStudents));
    
    client.get().uri("/searchByDocument/{numberDocument}", numberDocument).exchange()
    .expectStatus().isOk()
    .expectBodyList(Student.class).isEqualTo(expectedFilteredStudents);
  }


}
