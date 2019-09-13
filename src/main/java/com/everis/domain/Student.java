package com.everis.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection="students")
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@Id
	private String id;	
	private String fullName;
	private String gender;	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateOfBirth;
	private String typeDocument;
	private int numberDocument;

}
