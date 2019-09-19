package com.everis.service;

import com.everis.model.Student;

import com.everis.repository.StudentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public Mono<Student> create(Student student) {
    return studentRepository.save(student);
  }

  public Mono<Student> findById(String id) {
    return studentRepository.findById(id);
  }

  public Flux<Student> findAll() {
    return studentRepository.findAll();
  }

  /**
   * Method for Update Student By Id.
   */
  public Mono<Student> update(String id, Student updateStudent) {
    return studentRepository.findById(id)
        .map(existingStudent -> existingStudent.toBuilder()
        .fullName(updateStudent.getFullName())
        .gender(updateStudent.getGender())
        .dateOfBirth(updateStudent.getDateOfBirth())
        .typeDocument(updateStudent.getTypeDocument())
        .numberDocument(updateStudent.getNumberDocument())
        .build())
        .flatMap(studentRepository::save);
  }


  public Mono<Student> deleteById(String id) {
    return studentRepository.findById(id)
        .flatMap(student -> studentRepository.delete(student).then(Mono.just(student)));
  }

  public Flux<Student> findByFullName(String fullName) {
    return studentRepository.findByFullName(fullName);
  }

  public Flux<Student> findByNumberDocument(int numberDocument) {
    return studentRepository.findByNumberDocument(numberDocument);
  }

}
