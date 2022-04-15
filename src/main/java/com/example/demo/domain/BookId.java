package com.example.demo.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BookId implements Serializable {

	private static final long serialVersionUID = 9011564494628810201L;

	@NotNull
	@Column(nullable = false)
	private Integer originalId;

	@NotNull
	@Column(nullable = false)
	private ZonedDateTime received;
}
