package com.everis.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.model.Student;

import reactor.core.publisher.Flux;

public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
	
	Flux<Student> findByFullName(String fullName);	
	Flux<Student> findByNumberDocument(int numberDocument);

}
