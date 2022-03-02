package com.proyect.disneyApi.service.implementations;

import com.proyect.disneyApi.dto.MovieDetailsDto;
import com.proyect.disneyApi.model.Character;
import com.proyect.disneyApi.model.Movie;
import com.proyect.disneyApi.repository.MovieRepository;
import com.proyect.disneyApi.service.CharacterService;
import com.proyect.disneyApi.service.MovieService;
import com.proyect.disneyApi.service.specifications.MovieSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterService characterService;

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Page<Movie> getMoviesByFilter(MovieDetailsDto filter, Pageable pageable) {
        return movieRepository.findAll(MovieSpecification.movieFilter(filter), pageable);
    }

    @Override
    public Movie getMovieByTitle(String title) {
        return movieRepository.findMovieByTitle(title);
    }

    @Override
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public void addMovie(String characterName, String movieTitle) {
        Character character = characterService.getCharacterByName(characterName);
        Movie movie = movieRepository.findMovieByTitle(movieTitle);

        character.getMovies().add(movie);

    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
