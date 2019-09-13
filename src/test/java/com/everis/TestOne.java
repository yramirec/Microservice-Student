package com.everis;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.everis.domain.Student;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class TestOne {

	private final Student students = new Student("1", "Juan", "Masculino", null, "dni", 9347823);


	@Test
	public void testShouldStepVerifyTeams() throws Exception {
		StepVerifier.create(Flux.just(students.getId(), students.getFullName())).expectNext("1", "Juan")
				.expectComplete().verify();
	}
	

}
