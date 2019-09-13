package com.everis.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.domain.Student;

public interface IStudentDao extends ReactiveMongoRepository<Student, String> {

}
