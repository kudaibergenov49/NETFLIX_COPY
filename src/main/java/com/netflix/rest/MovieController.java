package com.netflix.rest;

import com.netflix.model.Movie;
import com.netflix.repository.MovieDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController extends AbstractRestController<Movie, MovieDao> {

	public MovieController(MovieDao repository) {
		super(repository);
	}
}
