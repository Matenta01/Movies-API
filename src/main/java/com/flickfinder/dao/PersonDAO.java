package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;

/**
 * TODO: Implement this class
 * 
 */
public class PersonDAO {

	// for the must have requirements, you will need to implement the following
	// methods:
	// - getAllPeople()
	// - getPersonById(int id)
	// you will add further methods for the more advanced tasks; however, ensure
	// your have completed
	// the must have requirements before you start these.

	private Connection conn;

	public PersonDAO() {
		Database db = Database.getInstance();
		conn = db.getConnection();
	}

	/* Obtains all people in database */
	public List<Person> getAllPeople(int limit) throws SQLException {
		List<Person> people = new ArrayList<>();
		String statement = "SELECT * FROM PEOPLE LIMIT ?";
		PreparedStatement stmt = conn.prepareStatement(statement);
		stmt.setInt(1, limit);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			people.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth")));

		}
		return people;
	}

	/* Returns person and their information */
	public Person getPersonById(int id) throws SQLException {
		String statement = "SELECT * FROM PEOPLE WHERE 	id = ?";
		PreparedStatement stmt = conn.prepareStatement(statement);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			return new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth"));
		}
		return null;
	}

	/* Returns list of movies a person has participated in */
	public List<Movie> getCastings(int id) throws SQLException {
		List<Movie> movies = new ArrayList<>();
		String statement = "SELECT * FROM MOVIES INNER JOIN STARS on STARS.movie_id = MOVIES.id  WHERE person_id = ?";
		PreparedStatement stmt = conn.prepareStatement(statement);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}
		return movies;
	}

}
