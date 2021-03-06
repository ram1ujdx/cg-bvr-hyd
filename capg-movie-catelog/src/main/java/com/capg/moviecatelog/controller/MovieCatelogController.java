package com.capg.moviecatelog.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.moviecatelog.model.CatelogList;
import com.capg.moviecatelog.model.MovieCatelog;
import com.capg.moviecatelog.service.MovieCatelogService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
@RequestMapping("/catelog")
public class MovieCatelogController {

	@Autowired
	private MovieCatelogService service;
	@Autowired
	private Environment env;
	

	
	
	
	@GetMapping("/id/{id}")
	@HystrixCommand(fallbackMethod = "getMovieCatelogFallback")
	public MovieCatelog getMovieCatelog(@PathVariable long id) {
		MovieCatelog movie= service.getMovieCatelog(id);
		int port=Integer.parseInt(env.getProperty("local.server.port"));
		movie.setPort(port);
		return movie;
	}
	
	
	public MovieCatelog getMovieCatelogFallback(@PathVariable long id) {
		MovieCatelog movie=new MovieCatelog(id,"Lion King");
		return movie;
	}
	
	@GetMapping("/all")
	public CatelogList getAllMovieCatelog(){
		return new CatelogList(service.getAllMovieCatelog());
		
	}
	@PostMapping("/add")
	public MovieCatelog addMovieCatelog(@RequestBody MovieCatelog movie) {
		return service.addMovieCatelog(movie);
	}
	
}
