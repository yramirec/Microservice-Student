package com.everis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.domain.Student;
import com.everis.service.IStudentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController//--
@RequestMapping("/students")
public class StudentController {
	
	@Autowired//--
	private IStudentService service;

	@GetMapping
	public Flux<Student> list() {
		Flux<Student> students = service.findAll();
		return students;
	}

	@GetMapping("/{id}")
	public Mono<Student> listForId(@PathVariable String id) {
		Mono<Student> student = service.findById(id);
		return student;
	}
	
	@PostMapping
	public void create(@RequestBody Student s) {
        service.create(s);
    }
	
	@PutMapping
	public Mono<Student> update(@RequestBody Student s) {
        return service.update(s);
    }
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
        service.delete(id).subscribe();
    }

}
