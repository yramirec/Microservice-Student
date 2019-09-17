package com.everis.controller;

import org.junit.Test;

import com.everis.model.Student;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class StudentControllerTest {

	
	private final Student students = new Student("1", "Juan", "Masculino", null, "dni", 9347823);
	
	@Test
	public void testShouldStepVerifyTeams() throws Exception {
		StepVerifier.create(Flux.just(students.getId(), students.getFullName())).expectNext("1", "Juan")
				.expectComplete().verify();
	}
	



}
