package com.flickfinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * TODO: Implement this class
 * 
 */

class PersonTest {

	private Person person;

	@BeforeEach
	public void setUp() {
		person = new Person(1, "Fred Astaire", 1899);
	}

	@Test
	public void testPersonCreated() {
		assertEquals(1, person.getId());
		assertEquals("Fred Astaire", person.getName());
		assertEquals(1899, person.getBirthday());
	}

	@Test
	public void testPersonSetters() {
		person.setName("Johnny Bravo");
		person.setId(3);
		person.setBirthday(1997);
		assertEquals("Johnny Bravo", person.getName());
		assertEquals(3, person.getId());
		assertEquals(1997, person.getBirthday());
	}

}