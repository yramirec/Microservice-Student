package com.everis.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.everis.model.Student;
import com.everis.service.StudentService;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class StudentControllerTest {

	@Autowired
	private WebTestClient client;

	@Autowired
	private StudentService studentService;

	@Test
	public void addStudent() {

		Student student = new Student("1", "Juan", "Masculino", new Date(), "dni", 9347823);


		client.post().uri("/students").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(student), Student.class)
				.exchange()
				.expectStatus()
				.isCreated()
				.expectHeader()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(Student.class);

	}
	
	@Test
    public void getAllStudents() {

        client.get()
                .uri("/students")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Student.class)
                .consumeWith(response -> {
                    List<Student> students = response.getResponseBody();
                    students.forEach(p -> {
                        System.out.println(p.getFullName());
                    });

                    Assertions.assertThat(students.size()>0).isTrue();
                });

    }
	
	@Test
    public void getStudentById() {

        Student student = studentService.findById("5d8126d2d44ed721fc0097df").block();
        client.get()
                .uri("/students/{id}", Collections.singletonMap("id", student.getId()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Student.class)
                .consumeWith(response -> {
                    Student s = response.getResponseBody();
                    Assertions.assertThat(s.getId()).isNotEmpty();
                    Assertions.assertThat(s.getId().length()>0).isTrue();

                });

    }
	
    

}
