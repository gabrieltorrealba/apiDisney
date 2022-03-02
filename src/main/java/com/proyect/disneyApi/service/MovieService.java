package com.proyect.disneyApi.service;

import com.proyect.disneyApi.dto.MovieDetailsDto;
import com.proyect.disneyApi.dto.MovieDto;
import com.proyect.disneyApi.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    public Movie saveMovie(Movie movie);
    public List<Movie> getAllMovies();
    public Page<Movie> getMoviesByFilter(MovieDetailsDto filter, Pageable pageable);
    public Movie getMovieByTitle(String title);
    public Optional<Movie> getMovieById(Long id);
    public void addMovie(String characterName, String movieTitle);
    public void deleteMovie(Long id);
}
