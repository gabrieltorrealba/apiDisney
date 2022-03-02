package com.proyect.disneyApi.controller;


import com.proyect.disneyApi.dto.CharacterDto;
import com.proyect.disneyApi.dto.MovieDetailsDto;
import com.proyect.disneyApi.dto.MovieDto;
import com.proyect.disneyApi.model.Character;
import com.proyect.disneyApi.model.Gender;
import com.proyect.disneyApi.model.Movie;
import com.proyect.disneyApi.model.Role;
import com.proyect.disneyApi.model.User;
import com.proyect.disneyApi.service.CharacterService;
import com.proyect.disneyApi.service.GenderService;
import com.proyect.disneyApi.service.MovieService;
import com.proyect.disneyApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class MovieController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private GenderService genderService;

    //get all movie by 10 per page
    @GetMapping("/movies")
    public ResponseEntity<?> getAllMovies(@RequestParam MultiValueMap<String, String> queryMap,
                                            @PageableDefault(size = 10, sort = "year", direction = Sort.Direction.ASC) Pageable pageable){

        try {

            MovieDetailsDto filter = MovieDetailsDto.builder()
                    .title(queryMap.getFirst("title"))
                    .gender(queryMap.getFirst("gender"))
                    .year(queryMap.getFirst("year"))
                    .build();

            Page<Movie> page = movieService.getMoviesByFilter(filter, pageable);

            Map<String, Object> movieMap = new HashMap<>();
            movieMap.put("total", page.getTotalElements());
            movieMap.put("pages", page.getTotalPages());
            movieMap.put("page", page.getNumber());
            movieMap.put("size", page.getSize());
            movieMap.put("sort", page.getSort());
            movieMap.put("content", page.getContent().stream().map(movie ->
                    MovieDto.builder()
                            .id(movie.getId())
                            .image(movie.getImage().toString())
                            .title(movie.getTitle())
                            .year(movie.getYear().toString())
                            .build()));

            return ResponseEntity.ok().body(movieMap);

        }  catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    //get a movie by id
    @GetMapping("/movies/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id){


        Movie movie = movieService.getMovieById(id).orElse(null);

        try {

            if (movie != null){
                MovieDetailsDto movieDetailsDto = MovieDetailsDto.builder()
                        .id(movie.getId())
                        .image(movie.getImage().toString())
                        .title(movie.getTitle())
                        .gender(movie.getGender().getName())
                        .year(movie.getYear().toString())
                        .rating(movie.getRating().toString())
                        .characters(movie.getCharacters().stream().map(character ->
                                CharacterDto.builder()
                                        .id(character.getId())
                                        .image(character.getImage().toString())
                                        .name(character.getName())
                                        .build()).collect(Collectors.toSet()))
                        .build();

                return ResponseEntity.ok().body(movieDetailsDto);

            }

            return new ResponseEntity<>("Movie doesn't exists",HttpStatus.NOT_FOUND);

        }  catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }

    }

    //add new movie
    @PostMapping("/movies")
    public ResponseEntity<?> createMovie(Authentication authentication, @RequestBody MovieDetailsDto movieDetailsDto) throws MalformedURLException {
        User user = userService.getUser(authentication.getName());
        Role role = userService.getRole("ADMIN");

        try {

            if (!user.getRoles().contains(role)){
                return new ResponseEntity<>("Not allowed", HttpStatus.METHOD_NOT_ALLOWED);
            }

            if (movieDetailsDto.getTitle().isEmpty() || movieDetailsDto.getImage().isEmpty() || movieDetailsDto.getGender().isEmpty() ||
            movieDetailsDto.getYear().isEmpty() || movieDetailsDto.getYear().isEmpty()){

                return new ResponseEntity<>("Empty fields",HttpStatus.BAD_REQUEST);
            }

            float rating = Float.parseFloat(movieDetailsDto.getRating());

            if (rating < 1 || rating > 5){

                return new ResponseEntity<>("Invalid rate", HttpStatus.BAD_REQUEST);
            }

            Movie movie = movieService.getMovieByTitle(movieDetailsDto.getTitle().toUpperCase());

            if (movie != null){

                return new ResponseEntity<>("Movie already exists",HttpStatus.CONFLICT);
            }

            Gender gender = genderService.getGenderByName(movieDetailsDto.getGender().toUpperCase());

            if (gender == null){

                return new ResponseEntity<>("Gender doesn't exists",HttpStatus.CONFLICT);
            }

            URL image = new URL(movieDetailsDto.getImage());
            LocalDate year = LocalDate.parse(movieDetailsDto.getYear());

            Movie newMovie = new Movie(null, image, movieDetailsDto.getTitle(), year, rating, gender, null);

            movieService.saveMovie(newMovie);
            return new ResponseEntity<>("Movie created",HttpStatus.CREATED);

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    //edit a movie by id
    @PutMapping("/movies/{id}")
    public ResponseEntity<?> editMovie(Authentication authentication, @PathVariable("id") Long id,
                                       @RequestBody MovieDetailsDto movieDetailsDto) throws MalformedURLException {
        User user = userService.getUser(authentication.getName());
        Role role = userService.getRole("ADMIN");

        try {

        if (!user.getRoles().contains(role)) {
            return new ResponseEntity<>("Not allowed", HttpStatus.METHOD_NOT_ALLOWED);
        }

        if (movieDetailsDto.getTitle().isEmpty() || movieDetailsDto.getImage().isEmpty() || movieDetailsDto.getGender().isEmpty() ||
                movieDetailsDto.getYear().isEmpty() || movieDetailsDto.getYear().isEmpty()){

            return new ResponseEntity<>("Empty fields",HttpStatus.BAD_REQUEST);
        }

        float rating = Float.parseFloat(movieDetailsDto.getRating());
        Movie currentMovie = movieService.getMovieById(id).orElse(null);
        Gender gender = genderService.getGenderByName(movieDetailsDto.getGender().toUpperCase());
        URL image = new URL(movieDetailsDto.getImage());
        LocalDate year = LocalDate.parse(movieDetailsDto.getYear());

        if (currentMovie == null){
            return new ResponseEntity<>("Movie doesn't exists", HttpStatus.NOT_FOUND);
        }

        if (rating < 1 || rating > 5){

            return new ResponseEntity<>("Invalid rate", HttpStatus.BAD_REQUEST);
        }

        if (gender == null){

            return new ResponseEntity<>("Gender doesn't exists",HttpStatus.CONFLICT);
        }

        currentMovie.setImage(image);
        currentMovie.setTitle(movieDetailsDto.getTitle());
        currentMovie.setYear(year);
        currentMovie.setRating(rating);
        currentMovie.setGender(gender);

        movieService.saveMovie(currentMovie);

        return new ResponseEntity<>("Movie edited",HttpStatus.OK);

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    //delete a movie by id
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteMovie(Authentication authentication, @PathVariable("id") Long id) {

        User user = userService.getUser(authentication.getName());
        Role role = userService.getRole("ADMIN");

        try {

            if (!user.getRoles().contains(role)) {
                return new ResponseEntity<>("Not allowed", HttpStatus.METHOD_NOT_ALLOWED);
            }

            Movie movie = movieService.getMovieById(id).orElse(null);

            if(movie == null){
                return new ResponseEntity<>("Movie doesn't exists", HttpStatus.BAD_REQUEST);
            }

            Set<Character> characters = movie.getCharacters();

            characters.forEach(character -> {
                List<Movie> movies = character.getMovies().stream().filter(movie1 -> movie1.getId().equals(id)).collect(Collectors.toList());
                movies.remove(movies.get(0));
                character.setMovies(movies);
                characterService.saveCharacter(character);
            });

            movie.setCharacters(null);
            movie.setGender(null);

            movieService.saveMovie(movie);
            movieService.deleteMovie(movie.getId());

            return new ResponseEntity<>("Movie deleted",HttpStatus.OK);

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }

    }

    //add character to movie
    @PostMapping("/movies/characters")
    public ResponseEntity<Object> addMovieToActor(@RequestParam String character, @RequestParam String title){

        movieService.addMovie(character, title);
        return ResponseEntity.ok().build();
    }

}
