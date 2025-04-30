package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.flickfinder.model.Movie;
import com.flickfinder.model.MovieRating;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;

/**
 * The Data Access Object for the Movie table.
 * 
 * This class is responsible for getting data from the Movies table in the
 * database.
 * 
 */
public class MovieDAO {

	/**
	 * The connection to the database.
	 */
	private final Connection connection;

	/**
	 * Constructs a SQLiteMovieDAO object and gets the database connection.
	 * 
	 */
	public MovieDAO() {
		Database database = Database.getInstance();
		connection = database.getConnection();
	}

	/**
	 * Returns a list of all movies in the database.
	 * 
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */

	public List<Movie> getAllMovies(int limitInfo) throws SQLException {
		List<Movie> movies = new ArrayList<>();
		String statement = "SELECT * FROM MOVIES LIMIT ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, limitInfo);

		// I've set the limit to 10 for development purposes - you should do the same.
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}

		return movies;
	}

	public List<MovieRating> getAllMoviesInSpecificYear(int year, int votes, int limit) throws SQLException {
		List<MovieRating> movies = new ArrayList<>();
		String statement = "SELECT MOVIES.id, MOVIES.title, MOVIES.year, RATINGS.rating, RATINGS.votes FROM MOVIES JOIN RATINGS ON MOVIES.id = RATINGS.movie_id WHERE MOVIES.year = ? AND RATINGS.votes > ? ORDER BY RATINGS.rating DESC LIMIT ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, year);
		ps.setInt(2, votes);
		ps.setInt(3, limit);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			movies.add(new MovieRating(rs.getInt("id"), rs.getString("title"), rs.getFloat("rating"),
					rs.getInt("votes"), rs.getInt("year")));
		}
		return movies;
	}

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param id the id of the movie
	 * @return the movie with the specified id
	 * @throws SQLException if a database error occurs
	 */
	public Movie getMovieById(int id) throws SQLException {

		String statement = "select * from movies where id = ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			return new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"));
		}

		// return null if the id does not return a movie.

		return null;

	}

	/* Returns casting list */
	public List<Person> getCast(int id) throws SQLException {
		List<Person> people = new ArrayList<>();
		String statement = "SELECT * FROM STARS INNER JOIN PEOPLE ON STARS.person_id = People.id  WHERE movie_id = ?";
		PreparedStatement stmt = connection.prepareStatement(statement);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			people.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth")));
		}
		return people;
	}

}
