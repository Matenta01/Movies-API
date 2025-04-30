package com.flickfinder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;

import io.javalin.Javalin;

/**
 * These are our integration tests. We are testing the application as a whole,
 * including the database.
 */
class IntegrationTests {

	/**
	 * The Javalin app.*
	 */
	Javalin app;

	/**
	 * The seeder object.
	 */
	Seeder seeder;

	/**
	 * The port number. Try and use a different port number from your main
	 * application.
	 */
	int port = 6000;

	/**
	 * The base URL for our test application.
	 */
	String baseURL = "http://localhost:" + port;

	/**
	 * Bootstraps the application before each test.
	 */
	@BeforeEach
	void setUp() {
		var url = "jdbc:sqlite::memory:";
		seeder = new Seeder(url);
		Database.getInstance(seeder.getConnection());
		app = AppConfig.startServer(port);
	}

	/**
	 * Test that the application retrieves a list of all movies. Notice how we are
	 * checking the actual content of the list. At this higher level, we are not
	 * concerned with the implementation details.
	 */

	@Test
	void retrieves_a_list_of_all_movies() {
		given().when().get(baseURL + "/movies").then().assertThat().statusCode(200). // Assuming a successful
		// response returns HTTP
		// 200
				body("id", hasItems(1, 2, 3, 4, 5))
				.body("title",
						hasItems("The Shawshank Redemption", "The Godfather", "The Godfather: Part II",
								"The Dark Knight", "12 Angry Men"))
				.body("year", hasItems(1994, 1972, 1974, 2008, 1957));
	}

	@Test
	void retrieves_a_single_movie_by_id() {

		given().when().get(baseURL + "/movies/1").then().assertThat().statusCode(200). // Assuming a successful
		// response returns HTTP
		// 200
				body("id", equalTo(1)).body("title", equalTo("The Shawshank Redemption")).body("year", equalTo(1994));
	}

	/**
	 * Tears down the application after each test. We want to make sure that each
	 * test runs in isolation.
	 */
	@AfterEach
	void tearDown() {
		seeder.closeConnection();
		app.stop();
	}

	/* tests that the application can retrieve a list of all people */
	@Test
	void retrieves_a_list_of_all_people() {
		given().when().get(baseURL + "/people").then().assertThat().statusCode(200).body("id", hasItems(1, 2, 3, 4, 5))
				.body("name",
						hasItems("Tim Robbins", "Morgan Freeman", "Christopher Nolan", "Al Pacino", "Henry Fonda"))
				.body("birthday", hasItems(1958, 1937, 1970, 1940, 1905));
	}

	/* tests that a single persons information can be retrieved */
	@Test
	void retrieves_a_single_person_by_id() {
		given().when().get(baseURL + "/people/3").then().assertThat().statusCode(200).body("id", equalTo(3))
				.body("name", equalTo("Christopher Nolan")).body("birthday", equalTo(1970));
	}

	/* tests that the application can retrieve the cast of a movie */
	@Test
	void retrieves_a_movies_cast() {
		given().when().get(baseURL + "/movies/1/stars").then().assertThat().statusCode(200).body("id", hasItems(1, 2))
				.body("name", hasItems("Tim Robbins", "Morgan Freeman")).body("birthday", hasItems(1958, 1937));
	}

	/*
	 * tests that the application can retrieve a list of movies a person has taken
	 * part in
	 */
	@Test
	void retrieves_a_persons_movies() {
		given().when().get(baseURL + "/people/4/movies").then().assertThat().statusCode(200).body("id", hasItems(2, 3))
				.body("title", hasItems("The Godfather", "The Godfather: Part II")).body("year", hasItems(1972, 1974));
	}

	/*
	 * tests that the application can retrieve a list of movies from a specific year
	 */
	@Test
	void retrieves_movies_and_ratings_by_specific_year() {
		given().when().get(baseURL + "/movies/ratings/2008").then().assertThat().statusCode(200).body("id", hasItems(4))
				.body("title", hasItems("The Dark Knight")).body("ratings", hasItems(8.8))
				.body("votes", hasItems(2000000)).body("year", hasItems(2008));
	}
}
