package com.everis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.model.Student;
import com.everis.repository.StudentRepository;
import com.everis.service.StudentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public Mono<Student> create(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Mono<Student> findById(String id) {
		return studentRepository.findById(id);
	}

	@Override
	public Flux<Student> findAll() {
		return studentRepository.findAll();
	}
	
	@Override
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
	

	@Override
	public Mono<Student> deleteById(String id) {
		return studentRepository.findById(id)
		        .flatMap(student -> studentRepository.delete(student).then(Mono.just(student)));
	}

	@Override
	public Flux<Student> findByFullName(String fullName) {
		return studentRepository.findByFullName(fullName);
	}

	@Override
	public Flux<Student> findByNumberDocument(int numberDocument) {
		return studentRepository.findByNumberDocument(numberDocument);
	}	

}
