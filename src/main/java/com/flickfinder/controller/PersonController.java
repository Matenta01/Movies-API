package com.flickfinder.controller;

import java.sql.SQLException;
import java.util.List;

import com.flickfinder.dao.PersonDAO;
import com.flickfinder.model.Person;

import io.javalin.http.Context;

public class PersonController {

	// to complete the must-have requirements you need to add the following methods:
	// getAllPeople
	// getPersonById
	// you will add further methods for the more advanced tasks; however, ensure
	// your have completed
	// the must have requirements before you start these.

	private final PersonDAO personDAO;

	public PersonController(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	/* returns list of a people in database */
	public void getAllPeople(Context ctx) {
		try {
			List<Person> people;
			String limitInfo = ctx.queryParam("limit");
			int limit = 0;
			if (limitInfo == null) {
				limit = 50;
				people = personDAO.getAllPeople(limit);
				ctx.json(people);
			} else if (Integer.parseInt(limitInfo) <= 0) {
				ctx.result("Not a valid Limit");
				ctx.status(404);
			} else {
				limit = Integer.parseInt(limitInfo);
				people = personDAO.getAllPeople(limit);
				ctx.json(people);
			}
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Returns a person's info */
	public void getPersonById(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			Person person = personDAO.getPersonById(id);
			if (person == null) {
				ctx.status(404);
				ctx.result("Person not found");
				return;
			}
			ctx.json(personDAO.getPersonById(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	/* Returns list of movies a person has taken part in */
	public void getMoviesStarringPerson(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			ctx.json(personDAO.getCastings(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}
}