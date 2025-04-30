package com.flickfinder.model;

import java.sql.Date;
import java.time.LocalDate;

/**
 * A person in the movie database.
 * 
 * @TODO: Implement this class
 */
public class Person {

	// - Add your code here: use the MovieDAO.java as an example
	// - Check the ERD and database schema in the docs folder
	// (./docs/database_schema.md) to ensure each column in the People table
	// has an attribute in the model. (DELETE THIS COMMENT WHEN DONE)
	private int id;
	private String name;
	private int birthday;

	public Person(int id, String name, int birthday) {
		this.id = id;
		this.name = name;
		this.birthday = birthday;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirthday() {
		return birthday;
	}

	public void setBirthday(int birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "Person [id=" + this.id + ", name=" + this.name + ", birthday=" + this.birthday + "]";
	}
}
