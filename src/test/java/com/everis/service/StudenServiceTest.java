package com.everis.service;

import static org.mockito.Mockito.when;

import com.everis.model.Student;
import com.everis.repository.StudentRepository;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class StudenServiceTest {

  private final Student student1 = Student.builder().fullName("Juan").gender("Masculino")
      .dateOfBirth(new Date()).typeDocument("dni").numberDocument(109999).idFamily("Suarez")
      .build();
  private final Student student2 = Student.builder().fullName("Mariana").gender("Femenino")
      .dateOfBirth(new Date()).typeDocument("dni").numberDocument(259999).idFamily("Suarez")
      .build();
  private final Student student3 = Student.builder().fullName("Adrian").gender("Masculino")
      .dateOfBirth(new Date()).typeDocument("Carnet").numberDocument(79999).idFamily("Suarez")
      .build();

  @Mock
   private StudentRepository studentRepository;
  private StudentService studentService;

  @BeforeEach
   void setUp() {
    studentService = new StudentService(studentRepository);
  }

  @Test
   void getAll() {
    when(studentRepository.findAll()).thenReturn(Flux.just(student1, student2, student3));

    Flux<Student> actual = studentService.findAll();

    assertResults(actual, student1, student2, student3);
  }

  @Test
   void getById_whenIdExists_returnCorrectStudent() {
    when(studentRepository.findById(student1.getId())).thenReturn(Mono.just(student1));

    Mono<Student> actual = studentService.findById(student1.getId());

    assertResults(actual, student1);
  }

  @Test
   void getById_whenIdNotExist_returnEmptyMono() {
    when(studentRepository.findById(student1.getId())).thenReturn(Mono.empty());

    Mono<Student> actual = studentService.findById(student1.getId());

    assertResults(actual);
  }

  @Test
   void create() {
    when(studentRepository.save(student1)).thenReturn(Mono.just(student1));

    Mono<Student> actual = studentService.create(student1);

    assertResults(actual, student1);
  }

  @Test
   void update_whenIdExists_returnUpdatedStudent() {
    when(studentRepository.findById(student1.getId())).thenReturn(Mono.just(student1));
    when(studentRepository.save(student1)).thenReturn(Mono.just(student1));

    Mono<Student> actual = studentService.update(student1.getId(), student1);

    assertResults(actual, student1);
  }

  @Test
   void update_whenIdNotExist_returnEmptyMono() {
    when(studentRepository.findById(student1.getId())).thenReturn(Mono.empty());

    Mono<Student> actual = studentService.update(student1.getId(), student1);

    assertResults(actual);
  }

  @Test
   void delete_whenStudentExists_performDeletion() {
    when(studentRepository.findById(student1.getId())).thenReturn(Mono.just(student1));
    when(studentRepository.delete(student1)).thenReturn(Mono.empty());

    Mono<Student> actual = studentService.deleteById(student1.getId());

    assertResults(actual, student1);
  }

  @Test
   void delete_whenIdNotExist_returnEmptyMono() {
    when(studentRepository.findById(student1.getId())).thenReturn(Mono.empty());

    Mono<Student> actual = studentService.deleteById(student1.getId());

    assertResults(actual);
  }

  private void assertResults(Publisher<Student> publisher, Student... expectedStudents) {
    StepVerifier
    .create(publisher)
    .expectNext(expectedStudents)
      .verifyComplete();
  }

}
