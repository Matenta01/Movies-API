package com.flickfinder.controller;

import java.sql.SQLException;
import java.util.List;

import com.flickfinder.dao.MovieDAO;
import com.flickfinder.model.Movie;
import com.flickfinder.model.MovieRating;

import io.javalin.http.Context;

/**
 * The controller for the movie endpoints.
 * 
 * The controller acts as an intermediary between the HTTP routes and the DAO.
 * 
 * As you can see each method in the controller class is responsible for
 * handling a specific HTTP request.
 * 
 * Methods a Javalin Context object as a parameter and uses it to send a
 * response back to the client. We also handle business logic in the controller,
 * such as validating input and handling errors.
 *
 * Notice that the methods don't return anything. Instead, they use the Javalin
 * Context object to send a response back to the client.
 */

public class MovieController {

	/**
	 * The movie data access object.
	 */

	private final MovieDAO movieDAO;

	/**
	 * Constructs a MovieController object and initializes the movieDAO.
	 */
	public MovieController(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	/**
	 * Returns a list of all movies in the database.
	 * 
	 * @param ctx the Javalin context
	 */
	public void getAllMovies(Context ctx) {
		try {
			List<Movie> movies;
			String limitInfo = ctx.queryParam("limit");
			int limit = 0;

			if (limitInfo == null) {
				limit = 50;
				movies = movieDAO.getAllMovies(limit);
				ctx.json(movies);
			} else if (Integer.parseInt(limitInfo) <= 0) {
				ctx.result("Not valid limit");
				ctx.status(400);
			} else {
				limit = Integer.parseInt(limitInfo);
				movies = movieDAO.getAllMovies(limit);
				ctx.json(movies);
			}
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param ctx the Javalin context
	 */
	public void getMovieById(Context ctx) {

		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			Movie movie = movieDAO.getMovieById(id);
			if (movie == null) {
				ctx.status(404);
				ctx.result("Movie not found");
				return;
			}
			ctx.json(movieDAO.getMovieById(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	/* Returns list of movie cast */
	public void getPeopleByMovieId(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			ctx.json(movieDAO.getCast(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	/* returns list of movies by year */
	public void getRatingsByYear(Context ctx) {
		List<MovieRating> movies;
		String yearInfo, limitInfo, votesInfo;
		yearInfo = ctx.pathParam("year");
		votesInfo = ctx.queryParam("votes");
		limitInfo = ctx.queryParam("limit");
		int year, votes = 0, limit = 0;
		try {
			if (votesInfo == null) {
				votes = 1000;
			} else if (Integer.parseInt(votesInfo) <= 0) {
				ctx.status(404);
				ctx.result("Invalid votes");
			} else {
				votes = Integer.parseInt(votesInfo);
			}

			if (limitInfo == null) {
				limit = 50;
			} else if (Integer.parseInt(limitInfo) <= 0) {
				ctx.result("Not valid limit");
				ctx.status(400);
			} else {
				limit = Integer.parseInt(limitInfo);
			}
			year = Integer.parseInt(yearInfo);
			movies = movieDAO.getAllMoviesInSpecificYear(year, votes, limit);
			ctx.json(movies);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ctx.status(400);
			ctx.result("input error");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ctx.status(500);
			ctx.result("Database error");
		}
	}

}