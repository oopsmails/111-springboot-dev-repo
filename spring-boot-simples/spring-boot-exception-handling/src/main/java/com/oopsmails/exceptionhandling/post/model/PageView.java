package com.oopsmails.exceptionhandling.post.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
public class PageView implements Serializable {

	private static final long serialVersionUID = 65981149772133526L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String slug;

	private LocalDate viewedOn;

	private String ipAddress;
}