package com.flickfinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieRatingTest {
	// TODO Auto-generated constructor stub

	private MovieRating movie;

	@BeforeEach
	void setUp() {
		movie = new MovieRating(4, "The Dark Knight", 8.8, 2000000, 2008);
	}

	@Test
	public void testMovieRatingCreated() {
		assertEquals(4, movie.getId());
		assertEquals("The Dark Knight", movie.getTitle());
		assertEquals(2008, movie.getYear());
		assertEquals(8.8, movie.getRating());
		assertEquals(2000000, movie.getVotes());
	}

	@Test
	public void testMovieRatingSetters() {
		movie.setId(2);
		movie.setTitle("The Matrix Reloaded");
		movie.setYear(2003);
		movie.setRating(9.5);
		movie.setVotes(30000000);
		assertEquals(2, movie.getId());
		assertEquals("The Matrix Reloaded", movie.getTitle());
		assertEquals(2003, movie.getYear());
		assertEquals(9.5, movie.getRating());
		assertEquals(30000000, movie.getVotes());
	}

}
