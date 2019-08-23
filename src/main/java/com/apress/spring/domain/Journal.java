package com.apress.spring.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Journal {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	private Date created;
	private String summary;
	public Journal(String title, Date created, String summary) {
		super();
		this.title = title;
		this.created = created;
		this.summary = summary;
	}
	public Journal() {
		super();
	}
	

}
