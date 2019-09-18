package com.everis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection="students")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Student {
	@Id
	private String id;	
	private String fullName;
	private String gender;	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateOfBirth;
	private String typeDocument;
	private int numberDocument;

}
