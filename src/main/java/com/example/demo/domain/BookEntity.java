package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
public class BookEntity {

//	@Id
//	@SequenceGenerator(name = "BOOK_ID_GENERATOR", sequenceName = "BOOK_ID_SEQ", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_GENERATOR")
//	private Integer id;

	@Id
	@NotNull
	private BookId id;

	@NotNull
	private String name;

	@NotNull
	private String author;

//	@Column(nullable = false)
//	private ZonedDateTime received = ZonedDateTime.now();

	@NotNull
	private String isbn;
}
