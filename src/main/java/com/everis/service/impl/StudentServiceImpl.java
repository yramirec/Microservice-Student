package com.everis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.dao.IStudentDao;
import com.everis.domain.Student;
import com.everis.service.IStudentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceImpl implements IStudentService {
	
	@Autowired
	private IStudentDao dao;
	
	@Override
	public void create(Student s) {
		dao.save(s).subscribe();
	}

	@Override
	public Mono<Student> findById(String id) {
		return dao.findById(id);
	}

	@Override
	public Flux<Student> findAll() {
		return dao.findAll();
	}

	@Override
	public Mono<Student> update(Student s) {
		return dao.save(s);
	}

	@Override
	public Mono<Void> delete(String id) {
		return dao.deleteById(id);
	}
	
	

}
