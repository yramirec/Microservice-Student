package com.everis.controller;

import com.everis.model.Student;
import com.everis.service.StudentService;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping
   public Flux<Student> list() {
    return studentService.findAll();
  }

  /**
   * Method for Find By Id.
   */
  @GetMapping("{id}")
    public Mono<ResponseEntity<Student>> findById(@PathVariable String id) {
    return studentService.findById(id)
       .map(ResponseEntity::ok)
       .defaultIfEmpty(ResponseEntity.notFound()
       .build());
  }

  /**
   * Method for Create Student.
   */
  @PostMapping
   public Mono<ResponseEntity<Student>> createStudent(@RequestBody @Valid Student student) {
    Student studentToCrete = student.toBuilder().id(null).build();
    return studentService.create(studentToCrete).map(
        newStudent -> ResponseEntity.created(URI.create("/students/" + newStudent.getId()))
        .body(newStudent));
  }

  @PutMapping("{id}")
   public Mono<ResponseEntity<Student>> updateStudent(@PathVariable String id, 
      @RequestBody @Valid Student student) {
    return studentService.update(id, student).map(ResponseEntity::ok)
       .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("{id}")
   public Mono<ResponseEntity<Void>> deleteStudent(@PathVariable String id) {
    return studentService.deleteById(id).map(r -> ResponseEntity.ok().<Void>build())
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/searchByName/{fullName}")
   public Flux<Student> findByFullName(@PathVariable String fullName) {
    return studentService.findByFullName(fullName);
  }

  @GetMapping("/searchByDocument/{numberDocument}")
   public Flux<Student> findByNumberDocument(@PathVariable int numberDocument) {
    return studentService.findByNumberDocument(numberDocument);
  }

}
