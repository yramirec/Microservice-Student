package com.everis.service;

import com.everis.model.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

	Mono<Student> create(Student student);

	Mono<Student> findById(String id);

	Flux<Student> findAll();

	Mono<Student> update(String id, Student updateStudent);

	Mono<Student> deleteById(String id);

	Flux<Student> findByFullName(String fullName);

	Flux<Student> findByNumberDocument(int numberDocument);

}
