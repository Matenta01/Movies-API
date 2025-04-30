package com.flickfinder.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;

/**
 * TODO: Implement this class
 */
class PersonDAOTest {

	/**
	 * The movie data access object.
	 */

	private PersonDAO personDAO;

	/**
	 * Seeder
	 */

	Seeder seeder;

	/**
	 * Sets up the database connection and creates the tables. We are using an
	 * in-memory database for testing purposes. This gets passed to the Database
	 * class to get a connection to the database. As it's a singleton class, the
	 * entire application will use the same connection.
	 */
	@BeforeEach
	void setUp() {
		var url = "jdbc:sqlite::memory:";
		seeder = new Seeder(url);
		Database.getInstance(seeder.getConnection());
		personDAO = new PersonDAO();

	}

	/**
	 * Tests the getAllMovies method. We expect to get a list of all movies in the
	 * database. We have seeded the database with 5 movies, so we expect to get 5
	 * movies back. At this point, we avoid checking the actual content of the list.
	 */
	@Test
	void testGetAllPeople() {
		try {
			int limit = 50;
			List<Person> people = personDAO.getAllPeople(limit);
			assertEquals(5, people.size());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}

	/**
	 * Tests the getMovieById method. We expect to get the movie with the specified
	 * id.
	 */
	@Test
	void testGetPersonById() {
		Person person;
		try {
			person = personDAO.getPersonById(2);
			assertEquals("Morgan Freeman", person.getName());
			assertEquals(1937, person.getBirthday());

		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}

	/**
	 * Tests the getMovieById method with an invalid id. Null should be returned.
	 */
	@Test
	void testGetPersonByIdInvalidId() {
		// write an assertThrows for a SQLException

		try {
			Person person = personDAO.getPersonById(500);
			assertEquals(null, person);
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}

	}

	/* tests whether a person's movies can be listed */
	@Test
	void testGetCastings() {
		try {
			List<Movie> movies = personDAO.getCastings(4);
			assertEquals(2, movies.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterEach
	void tearDown() {
		seeder.closeConnection();
	}

}