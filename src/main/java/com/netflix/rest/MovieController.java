package com.netflix.rest;

import com.netflix.model.Movie;
import com.netflix.repository.MovieRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController extends AbstractRestController<Movie, MovieRepository> {

	public MovieController(MovieRepository repository) {
		super(repository);
	}
}
