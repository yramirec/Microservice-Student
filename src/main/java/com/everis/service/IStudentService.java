package com.everis.service;

import com.everis.domain.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStudentService {
	
	void create(Student s);
    
    Mono<Student> findById(String id);
 
    Flux<Student> findAll();
 
    Mono<Student> update(Student s);
 
    Mono<Void> delete(String id);

}
